package com.github.julesrb.fixme.common;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseUnsignedInt;

// support only FIX 4.4
public class FixMessage {

    private String msg;

    private Map<Integer, String> fields = new HashMap<>();

    public static final int BEGIN_STRING = 8;
    public static final int MSG_TYPE = 35;
    public static final int SENDER_COMP_ID = 49; // ID Broker
    public static final int TARGET_COMP_ID = 56; // Market
    public static final int INSTRUMENT = 55;
    public static final int SIDE = 54;
    public static final int QUANTITY = 38;
    public static final int PRICE = 44;
    public static final int CHECKSUM = 10;

    private final int[] expectedTags = {8, 35, 49, 56, 55, 54, 38, 44};


    public FixMessage(String message) {
        this.msg = message;
    }

    public void parseMessage() throws FixParseException {
        if (msg == null || msg.isBlank())
            throw new FixParseException("Message cannot be null or blank");
        String[] tokens = msg.split("\u0001");
        for (String token : tokens) {
            if (token.isEmpty()) continue;
            String[] part = token.split("=");
            if (part.length != 2)
                throw new FixParseException("Malformed token: " + token);
            try {
                int tag = parseInt(part[0]);
                fields.put(tag, part[1]);
            } catch (NumberFormatException e) {
                throw new FixParseException("Invalid tag number: " + part[0]);
            }
            System.out.println("added " + part[0] + " = " + part[1]);
        }
    }

    private Boolean isUnsignedInt(String str) {
        try {
            int id = parseUnsignedInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //Check if the fields are correct, not empty and that the minimum fields are present
    public Boolean validateMessage() {
        if (!validateChecksum())
            throw new RuntimeException("Checksum incorrect");
        for (int key : expectedTags) {
            if (!fields.containsKey(key)) throw new RuntimeException("Missing tag = " + key);
        }
        for (Map.Entry<Integer,String> field : fields.entrySet()) {
            int key = field.getKey();
            String value = field.getValue();
//            if (value.isBlank()) throw new RuntimeException("Value missing for key " + key);
            switch (key) {
                case BEGIN_STRING:
                    if (!value.equals("FIX.4.4")) throw new RuntimeException("Expect FIX version 4.4");
                    break;
                case MSG_TYPE:
                    if (value.length() > 2) throw new RuntimeException("Message type unknown");
                    break;
                case SENDER_COMP_ID:
                    if (!isUnsignedInt(value)) throw new RuntimeException("Broker ID type error");
                    break;
                case TARGET_COMP_ID:
                    if (!isUnsignedInt(value)) throw new RuntimeException("Market ID type error");
                    break;
                case INSTRUMENT:
                    //In a simplified project, just define a list of strings that your Market supports, e.g.:
                    //If a Broker sends an order for a symbol not in the list, the Market should reject it.//
                    // List<String> tradableInstruments = List.of("AAPL", "GOOG", "TSLA");
                    //207 = SecurityExchange → exchange where the symbol trades (NASDAQ, NYSE)
	                //48 = SecurityID → unique identifier (ISIN, CUSIP)
	                //22 =SecurityIDSource → source of SecurityID (e.g., ISIN)
                    break;
                case SIDE:
                    if (!(value.equals("1") || value.equals("2"))) throw new RuntimeException("Wrong side argument");
                    break;
                case QUANTITY:
                    if (!isUnsignedInt(value)) throw new RuntimeException("Quantity type error");
                    break;
                case PRICE:
                    if (!isUnsignedInt(value)) throw new RuntimeException("Price ID type error");
                    break;
            }
        }
        return true;
    }

    public String calculateChecksum(String messageWithoutChecksum) {
        byte[] bytes = messageWithoutChecksum.getBytes(StandardCharsets.US_ASCII);
        int sum = 0;
        for (byte b : bytes) {
            sum += (b & 0xFF); // Makes it unsigned
        }
        int checksum = sum % 256;
        return String.format("%03d", checksum); // always 3 digits
    }

    public Boolean validateChecksum() {
        int lastTagIndex = msg.lastIndexOf("10=");
        if (lastTagIndex == -1) return false;

        String messageWithoutChecksum = msg.substring(0, lastTagIndex);
        String provided = fields.get(CHECKSUM);
        String expected = calculateChecksum(messageWithoutChecksum);

        return expected.equals(provided);
    }

}


package com.github.julesrb.fixme.market;

import com.github.julesrb.fixme.common.FixMessage;

import java.math.BigDecimal;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome from market!");
        try {
//            FixMessage msg = new FixMessage("8=FIX.4.4|35=D|49=BROKER01|56=MARKET01|34=1|11=ORDER123|55=AAPL|54=1|38=100|44=150.50|10=123|");
//            msg.validateMessage();
//            FixMessage msg2 = new FixMessage("8=FIX.4.4\u00019=65\u000135=D\u000134=1\u000149=BROKER01\u000156=MARKET01\u000111=ORDER123\u000155=AAPL\u000154=1\u000138=100\u000144=150.50\u000110=072\u0001");
//            msg2.validateMessage();
            FixMessage msg3 = new FixMessage("8=FIX.4.4\u00019=65\u000135=\u000134=1\u000149=BROKER01\u000156=MARKET01\u000111=ORDER123\u000155=AAPL\u000154=1\u000138=100\u000144=150.50\u000110=072\u0001");
            msg3.validateMessage();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
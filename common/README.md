# FIX-message

### Step 1 — Logon message (from initiator, e.g., Broker)

The initiator (the one opening the TCP connection) sends a message like:

8=FIX.4.2|35=A|49=BROKER1|56=ROUTER|34=1|52=20251007-12:00:00|98=0|108=30|10=072|

### Step 2 — Logon response (from acceptor, e.g., Router)

The Router replies with its own Logon message:

8=FIX.4.2|35=A|49=ROUTER|56=BROKER1|34=1|52=20251007-12:00:00|98=0|108=30|10=073|


Once that’s done, the session is established.
From then on, both sides exchange application messages (Buy, Sell, etc.),
always including the assigned 49 (SenderCompID) and 56 (TargetCompID).

### Step 3 — Optional heartbeats and test requests

During idle periods, both sides send:

8=FIX.4.2|35=0|49=BROKER1|56=ROUTER|34=2|52=...|10=...|

(35=0 means Heartbeat)

This keeps the session alive and detects disconnects.

### Step 4 — Logout

When closing the session cleanly:

8=FIX.4.2|35=5|49=BROKER1|56=ROUTER|34=10|52=...|10=...|

(35=5 = Logout)

## test

test many message on one destination to see if the program append and quee them and then them all at once. 
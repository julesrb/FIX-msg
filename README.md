# FIX-msg (in progress)

This project is a Java-based trading simulator that implements a simplified version of the FIX protocol. It consists of three independent modules—a Router, a Broker, and a Market, that communicate asynchronously over TCP using non-blocking sockets and the Java executor framework. The Router acts as a central hub, assigning IDs to participants and forwarding validated FIX messages, while Brokers send buy and sell orders and Markets execute or reject them based on available instruments. The design follows a client–server architecture with a clear separation of concerns, and can be extended with features such as transaction persistence in a database and failover mechanisms for robustness.

Based on the FIX-me project from school 42

## To build and execute the core modules 

mvn -pl router exec:java
mvn -pl router exec:java
mvn -pl market exec:java

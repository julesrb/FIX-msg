# FIXME – Hard Network Programming (Java) - in Progress

## Overview
**FIXME** is a Java-based simulation of an electronic trading system inspired by the FIX
(Financial Information eXchange) protocol. The project focuses on building a robust,
high-performance messaging platform using non-blocking network communication and
multi-threaded execution.

The system is composed of three independent components that communicate over TCP:
- **Router** – central message dispatcher
- **Broker** – sends buy and sell orders
- **Market** – executes or rejects orders

This project is the final Java assignment from the 42 curriculum and emphasizes clean
architecture, concurrency, network programming, and test-driven development.

## Tech Stack
- **Language:** Java (latest LTS)
- **Networking:** TCP, non-blocking sockets (Java NIO)
- **Concurrency:** Java Executor Framework
- **Build Tool:** Maven (multi-module)
- **Frameworks:** Spring Boot (planned, for persistence layer)
- **Testing:** JUnit (unit and integration testing)
- **Architecture Patterns:** Chain of Responsibility

## Architecture
### Router
- Central communication hub
- Accepts multiple Broker and Market connections
- Assigns unique 6-digit identifiers
- Validates, routes, and forwards FIX messages

### Broker
- Sends Buy and Sell orders
- Receives Executed or Rejected responses from markets

### Market
- Manages tradable instruments
- Executes or rejects orders based on availability and rules

## Message Format
All communications use a simplified **FIX message format**:
- Starts with the component ID assigned by the Router
- Ends with a checksum
- Order messages include:
  - Instrument
  - Quantity
  - Market
  - Price

## Testing & Development Approach
The project includes test coverage across core components to ensure correctness and
stability of the messaging system. Testing was an integral part of the development
process, encouraging a **test-driven approach** when designing network logic,
message validation, and routing behavior.

## Learning Objectives
- Asynchronous socket programming
- Multi-threaded message processing
- Network protocol design
- Test-driven development and testable architecture
- Clean, modular, and extensible system design

---
*Educational project – trading algorithms are out of scope.*

## To build and execute the core modules 

mvn clean package
mvn -pl router exec:java
mvn -pl router exec:java
mvn -pl market exec:java

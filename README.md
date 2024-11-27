# ChatApp
Develop a Client-Server Chat application, in UDP and TCP


# Features of the code:
### UDP Communication:

* Connectionless, lightweight communication using datagrams.
* Server logs messages received from clients with their respective IP addresses.
* Client sends UTF-8 encoded messages to the server.

### TCP Communication:

* Reliable, connection-oriented communication between a client and server.
* Server echoes each received message prefixed by the client's IP address.
* Client can continuously send and receive messages until it exits.

### Multiserver UDP:

* We send a packet that will be forwarded directly to the other customers

### Multiserver TCP:

*
*

# What is UDP and TCP?

## UDP:

UDP stands for User Datagram Protocol and is a connectionless communication protocol. The User Datagram Protocol, abbreviated UDP, is a protocol for the connectionless transmission of datagrams in IP-based networks. To reach the desired services on the destination hosts, the protocol uses ports, which are an essential part of the UDP header. Like many other network protocols, UDP is part of the Internet Protocol suite. It operates at the transport layer, acting as an intermediary between the network layer and the application layer. By using the User Datagram Protocol, an application can send information very quickly because no connection to the recipient is established and no response is expected. On the other hand, there is no guarantee that packets will arrive in their entirety and in the same order as they were sent. Furthermore, the protocol does not offer any protection against manipulation or access by third parties.

### UDP Properties

1) *UDP is connectionless*: UDP transport is characterised by the fact that there is no connection between the sender and the recipient. The packets in question are then sent to the preferred IP address, which specifies the destination port, without the computer to which this address is assigned having to send a response. If packets are to be returned to the sender, the UDP header may also contain the source port.
2) *Ports used by UDP*: Like TCP, UDP uses ports to route packets to the correct downstream protocols or applications on the destination system.
3) *UDP allows fast communication without delay*: this transport protocol is suitable for fast data transmission because it does not establish a connection. The loss of individual packets only affects the quality of the transmission.
4) *UDP does not guarantee the security or authenticity of data*: by dispensing with mutual authentication of the sender and receiver, the UDP protocol ensures exceptional transmission speed. However, the protocol cannot guarantee the integrity and security of data packets. 

## TCP

TCP stands for Transmission Control Protocol and is a connection-oriented communication protocol. TCP ensures reliable data transmission between devices in IP-based networks. Unlike connectionless protocols such as UDP, TCP establishes a connection between the sender and recipient using a three-way handshake before any data is exchanged. This process ensures that both devices are synchronized and ready for communication.

To facilitate communication, TCP uses ports, which are integral to its header structure, to route data to specific services or applications on the destination host. As a core protocol of the Internet Protocol suite, TCP operates at the transport layer, bridging the gap between the network layer and the application layer.

TCP guarantees that all packets sent will be delivered, in the correct order, and without errors. If packets are lost or corrupted during transmission, TCP detects the issue and retransmits the affected packets. These features make it highly reliable and suitable for applications like web browsing, email, and file transfers. Additionally, TCP incorporates checksums and mutual acknowledgment between sender and receiver to ensure data integrity and protection against unauthorized access, enhancing the security and reliability of the communication process.

### TCP Properties
1) *TCP is connection-oriented*: TCP requires a connection to be established between the sender and recipient before data transmission begins. This involves a three-way handshake (SYN, SYN-ACK, ACK) to ensure that both devices are ready for communication. Once the connection is established, data packets are exchanged reliably.
2) *Ports used by TCP*: TCP uses ports to direct data packets to the appropriate application or service on the destination system. These ports allow the operating system to differentiate between multiple simultaneous connections.
3) *TCP ensures reliable communication*: TCP provides mechanisms to guarantee the delivery of data packets. It ensures that packets are received in order and retransmits any packets that are lost or corrupted. This makes TCP suitable for applications requiring high accuracy, such as file transfers or web browsing.
4) *TCP guarantees data integrity and security*: TCP verifies the integrity of transmitted data using checksums and ensures that data packets arrive unaltered. Additionally, the connection-oriented nature of TCP enables mutual authentication of the sender and receiver, enhancing the security of the communication.


Usage:

Comment on utilise ? java ....
Schéma diag
Quels sont les commandes ?
Les erreurs/exceptions ?


TODO:

- Test codes
- MTU taille
- Dernière question
- Java Doc
- ReadMe usage ?
- Git potabke

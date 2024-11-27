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

### TCP Properties

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

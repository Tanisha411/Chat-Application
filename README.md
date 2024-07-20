# Chat-Application

This project implements a simple chat application using Java. The `ChatServer` class listens for client connections on a specified port and manages multiple clients using separate threads (`ConversationHandler`). Each client, managed by the `ChatClient` class, connects to the server, prompts the user for a unique username, and facilitates message exchange with other connected clients. The server logs all chat messages. The application features a graphical user interface (GUI) for the client, built with Swing, which allows users to send and receive messages in real time.

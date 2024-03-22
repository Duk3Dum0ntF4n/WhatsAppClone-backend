## For PC

The server part of WhatsApp clone. Listens for connections at startup. After successful connection, WS implements the ability to receive all the client's chats and messages in them from the database. The data is transmitted in unencrypted form in Json format. After the user sends the message, and its subsequent successful reception by the server, it is saved in the database and immediately sent to the connected recipient
[The frontend server repository](https://github.com/Duk3Dum0ntF4n/WhatsAppClone-frontend))

## Modules:
- data - gaining data from backend and classes for their transport
- di - dependencies injection using dagger hilt
- domain - data models to be shown in UI
- presentation - screens and their ViewModels
- UI - default Jetpack Compose
- util - some useful utils

Feel free to push some changes to this project and use it as simple example of websocket messenger backend ☺️

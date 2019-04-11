package inf112.roborally.game.server;

import java.io.*;
import java.net.*;
class GameClient {

    public static void main(String argv[]) throws Exception {

        String sentence;
        int id = 1;

//            BufferedReader inFromUser =
//                    new BufferedReader(new InputStreamReader(System.in));
        ClientInput inFromUser = new ClientInput(new BufferedReader(new InputStreamReader((System.in))));

        Socket clientSocket = new Socket("10.111.35.178", 6789); // replace "hostname" by your favourite hostname
        // ...or "127.0.0.1", for example
        OutputStream outToServer = clientSocket.getOutputStream();
        InputStream inFromServer = clientSocket.getInputStream();

        ServerConnection clientToServer = new ServerConnection(outToServer, inFromServer);

        MessagePacket handshake = new MessagePacket(id, "JOIN");

        clientToServer.send(handshake);

        while(true) {


            sentence = inFromUser.readLine();

            MessagePacket packet = new MessagePacket(id, sentence);

            clientToServer.send(packet);

            MessagePacket incomingPacket = clientToServer.receive();

            System.out.println("FROM SERVER: "  + incomingPacket.getId() + " " + incomingPacket.getWord());


        }

    }
}


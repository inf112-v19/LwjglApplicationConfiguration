package inf112.roborally.game.server;

import java.io.*;
import java.net.*;
class GameClient {

    public static void main(String argv[]) throws Exception {

        String sentence;

//            BufferedReader inFromUser =
//                    new BufferedReader(new InputStreamReader(System.in));
        ClientInput inFromUser = new ClientInput(new BufferedReader(new InputStreamReader((System.in))));

        Socket clientSocket = new Socket("127.0.0.1", 6789); // replace "hostname" by your favourite hostname
        // ...or "127.0.0.1", for example
        OutputStream outToServer = clientSocket.getOutputStream();
        InputStream inFromServer = clientSocket.getInputStream();

        ServerConnection clientToServer = new ServerConnection(outToServer, inFromServer);

        while(true) {


            sentence = inFromUser.readLine();

            String[] message = sentence.split(" ");
            MessagePacket packet = new MessagePacket(Integer.parseInt(message[0]), message[1]);

            clientToServer.send(packet);

            MessagePacket incomingPacket = clientToServer.receive();

            System.out.println("FROM SERVER: "  + incomingPacket.getNumber() + " " + incomingPacket.getWord());


        }

    }
}


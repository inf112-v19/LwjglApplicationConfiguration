package inf112.roborally.game.server;

import java.io.*;
import java.net.*;
class GameClient {

    public static void main(String argv[]) throws Exception {

        while(true) {
            String sentence;

            BufferedReader inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));

            Socket clientSocket = new Socket("192.168.56.1", 6789); // replace "hostname" by your favourite hostname
            // ...or "127.0.0.1", for example

            OutputStream outToServer = clientSocket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outToServer);


            sentence = inFromUser.readLine();

            String[] message = sentence.split(" ");
            MessagePacket packet = new MessagePacket(Integer.parseInt(message[0]), message[1]);

            objectOutputStream.writeObject(packet);

            InputStream inFromClient = clientSocket.getInputStream();

            ObjectInputStream objectInputStream = new ObjectInputStream(inFromClient);

            MessagePacket incomingPacket = (MessagePacket) objectInputStream.readObject();


            System.out.println("FROM SERVER: \n"  + incomingPacket.getNumber() + "\n" + incomingPacket.getWord());


        }

    }
}


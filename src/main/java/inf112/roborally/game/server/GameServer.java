package inf112.roborally.game.server;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

// NOT in use, only for demonstration
public class GameServer {

    public static void main(String argv[]) throws Exception
    {

        ServerSocket serverSocket = new ServerSocket(6789);
        while(true) {

            Socket connectionSocket = serverSocket.accept();

            System.out.println( "Connection received from: " +
                    connectionSocket.getInetAddress().getHostName() );

            InputStream inFromClient = connectionSocket.getInputStream();

            ObjectInputStream objectInputStream = new ObjectInputStream(inFromClient);

            MessagePacket packet = (MessagePacket) objectInputStream.readObject();

            System.out.println(packet.getId());
            System.out.println(packet.getWord());

            OutputStream outToServer = connectionSocket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outToServer);

            objectOutputStream.writeObject(packet);

        }
    }
}


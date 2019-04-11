package inf112.roborally.game.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class MultiThreadedServer {

    static final int PORT = 6789;


    public static void main(String args[]) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        int connections = 0;
        ArrayList<String> list = new ArrayList<String>();

        try {
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        try {
            serverSocket = new ServerSocket(PORT);

        } catch (IOException e) {
            e.printStackTrace();

        }
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            new EchoThread(socket).start();
            connections ++;
            System.out.println(connections);
        }
    }
}
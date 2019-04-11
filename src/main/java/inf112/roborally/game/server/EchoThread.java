package inf112.roborally.game.server;

import sun.net.ConnectionResetException;

import java.io.*;
import java.net.Socket;

public class EchoThread extends Thread {
    protected Socket socket;
    private int id;

    public EchoThread(Socket clientSocket, int connections) {
        this.socket = clientSocket;
        this.id = connections;
    }

    public void run() {
        InputStream inp = null;
        ObjectInputStream brinp = null;
        ObjectOutputStream out = null;

        try {
            inp = socket.getInputStream();
            brinp = new ObjectInputStream(inp);
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }

        while (true) {
            try {
                MessagePacket packet = (MessagePacket) brinp.readObject();
                int packetInt = packet.getId();
                String packetWord = packet.getWord();
                if ((packetInt == 0) || packetWord.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    return;
                } else if (packetWord.equalsIgnoreCase("JOIN")){
                    System.out.println(socket.getInetAddress() + " connected");
                }
                else {
                    System.out.println(packetInt + " " + packetWord);
                    out.writeObject(packet);

                }
            } catch (IOException e) {
                System.out.println("Lost connection to " + socket.getInetAddress());
                return;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
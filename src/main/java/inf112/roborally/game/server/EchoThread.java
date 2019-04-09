package inf112.roborally.game.server;

import sun.net.ConnectionResetException;

import java.io.*;
import java.net.Socket;

public class EchoThread extends Thread {
    protected Socket socket;

    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
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
                int packetInt = packet.getNumber();
                String packetWord = packet.getWord();
                if ((packetInt == 0) || packetWord.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    return;
                } else {
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
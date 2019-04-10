package inf112.roborally.game.server;

import java.io.*;

public class ServerConnection {

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream inputStream;

    public ServerConnection(OutputStream os, InputStream is){
        try {
            this.objectOutputStream = new ObjectOutputStream(os);
            this.inputStream = new ObjectInputStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(MessagePacket packet){
        try {
            objectOutputStream.writeObject(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public MessagePacket receive(){
        try {
            return (MessagePacket) inputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }
}

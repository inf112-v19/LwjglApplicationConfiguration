package inf112.roborally.game.server;

import java.io.Serializable;

public class MessagePacket implements Serializable {
    private int number;
    private String word;

    public MessagePacket(int num , String s){
        this.number = num;
        this.word = s;
    }

    public int getNumber() {
        return number;
    }

    public String getWord() {
        return word;
    }
}

package inf112.roborally.game.server;

import inf112.roborally.game.board.ProgramCard;

import java.io.Serializable;
import java.util.Stack;

public class MessagePacket implements Serializable {
    private int id;
    private String word;
    Stack<ProgramCard> cards;

    public MessagePacket(int num , String s){
        this.id = num;
        this.word = s;

    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }
}

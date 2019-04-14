package inf112.roborally.game.server;

import java.io.Serializable;

public class MessagePacket implements  Serializable{
    public String tag;
    public Payload payload;

    public MessagePacket (String tag, Payload value){
        this.tag = tag;
        this.payload = value;
    }

}

package inf112.roborally.game.server;

public class MessagePacket {
    public String tag;
    public Payload payload;

    public MessagePacket (String tag, Payload value){
        this.tag = tag;
        this.payload = value;
    }

}

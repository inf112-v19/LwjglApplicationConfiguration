package inf112.roborally.game.server;

public class MessagePacket {
    public String tag;
    public Payload payload;
    public int id;

    public MessagePacket (String tag, Payload value, int id){
        this.tag = tag;
        this.payload = value;
        this.id = id;
    }

}

package inf112.roborally.game.server;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.enums.Rotate;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private final RoboRallyGame game;

    public ClientHandler(RoboRallyGame game) {
        this.game = game;
    }

    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        String packet = o.toString();
        String[] split = packet.split(" ");
        String header = split[0];

        if (header.equals("START")) {
            game.playerNames.add(split[1]);
            System.out.println("123");
        }
        if(header.equals("CARD")){

        }
        else {
            System.out.println(packet);
        }


    }
    public ProgramCard generateCard(String rotate, String move, String prio){
            Rotate rotation = toRotate(rotate);
            int moveDist = Integer.parseInt(move);
            int priority = Integer.parseInt(prio);

            return new ProgramCard(rotation,moveDist,priority);

    }
    public Rotate toRotate(String rotate){
        if(rotate.equals("NONE")){
            return Rotate.NONE;
        }
        else if(rotate.equals("LEFT")){
            return Rotate.LEFT;
        }
        else if(rotate.equals("RIGHT")){
            return Rotate.RIGHT;
        }
        else if(rotate.equals("UTURN")){
            return Rotate.UTURN;
        }
        else{
            return null;
        }
    }
}

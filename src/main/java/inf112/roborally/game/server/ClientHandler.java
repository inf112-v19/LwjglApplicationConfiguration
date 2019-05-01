package inf112.roborally.game.server;

import com.badlogic.gdx.Gdx;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.objects.Flag;
import inf112.roborally.game.player.ProgramCard;
import inf112.roborally.game.enums.Rotate;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private final RoboRallyGame game;

    public ClientHandler(RoboRallyGame game) {
        this.game = game;
    }

/*
The format for sending messages is:
First word = Header

HEADER = Start
Second word = NAME of player to add to list

HEADER = CARD
Second word = NAME of player that played the card
THIRD WORD = ROTATE of card
FOURTH WORD = MOVE DISTANCE of card
FIFTH WORD = PRIORITY of card


 */

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        String packet = o.toString();
        String[] split = packet.split(" ");
        String header = split[0];

        if(header.equals("LIST")){
            int size = Integer.parseInt(split[1]);
            while(game.playerNames.size() < size){
                game.playerNames.add("temp");
            }
        }

       else if (header.equals("START")) {
            String name = split[1];
            int id = Integer.parseInt(split[2]);
            if(!game.playerNames.contains(name)){
                game.playerNames.set(id, name);
                System.out.println(game.playerNames);

            }

        }
        else if(header.equals("CARD")){
            String name = split[1];
            ProgramCard card = generateCard(split[2], split[3], split[4]);
        }
        else if(header.equals("SET_MAP")){
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    game.createDefaultGameScreen();
                    game.setScreen(game.gameScreen);
                }
            });
        }
        else {
            System.out.println(packet);
        }


    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.flush();
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

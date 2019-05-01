package inf112.roborally.game.server;

import com.badlogic.gdx.Gdx;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.objects.Flag;
import inf112.roborally.game.player.ProgramCard;
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

        switch (header) {
            case "LIST":
                int size = Integer.parseInt(split[1]);
                while (game.playerNames.size() < size) {
                    game.playerNames.add("temp");
                }
                break;
            case "START": {
                String name = split[1];
                int id = Integer.parseInt(split[2]);
                if (!game.playerNames.contains(name)) {
                    game.playerNames.set(id, name);
                    System.out.println(game.playerNames);

                }

                break;
            }
            case "SET_MAP":{
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        game.createDefaultGameScreen();
                        game.setScreen(game.gameScreen);
                    }
                });
                break;
            }
            case "CARD": {
                String name = split[1];
                ProgramCard card = new ProgramCard(split[2], split[3], split[4]);
                break;
            }
            case "MULTI":{
                game.multiPlayer = true;
                break;
            }
            default:
                System.out.println(packet);
                break;
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
}

package inf112.roborally.game.server;

import com.badlogic.gdx.Gdx;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.Rotate;
import inf112.roborally.game.board.MultiplayerLogic;
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
//                        game.createDefaultGameScreen();
                        System.out.printf("Number of players = %d%n", game.numberOfChosenPlayers);
                        game.createDefaultGameScreenForMultiplayer();
                        game.setScreen(game.gameScreen);
                    }
                });
                break;
            }
            case "CARD": {
                String name = split[1];
                ProgramCard card = new ProgramCard(split[2], split[3], split[4]);
                game.gameScreen.getMultiplayerLogic().receiveCardFromServer(name, card);
                break;
            }
            case "ALL_READY":
                game.gameScreen.getMultiplayerLogic().setToRound();
                break;
            case "MULTI":
                game.multiPlayer = true;
                break;
            case "RECEIVE_CARD":{
                System.out.println("RECEIVE_CARD! " + split[1]);
                final String name = split[1];
                Rotate rotate = Rotate.valueOf(split[2]);
                int move = Integer.parseInt(split[3]);
                int priority = Integer.parseInt(split[4]);
                final ProgramCard card = new ProgramCard(rotate, move, priority);
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        game.gameScreen.getMultiplayerLogic().receiveCardFromServer(name, card);
                        game.gameScreen.getHud().updateCards();
                        game.gameScreen.getHud().getHandDisplay().updateCardsInHand(game.gameScreen.getHud());
                    }
                });
                break;
            }

            case "SET_NUMBER_OF_PLAYERS": {
                Integer numPlayers = Integer.parseInt(split[1]);
                System.out.println("Number of players: " + numPlayers);
                game.setNumberOfChosenPlayers(numPlayers);
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

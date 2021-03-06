package inf112.roborally.game.server;

import com.badlogic.gdx.Gdx;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.board.MultiplayerLogic;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.player.ProgramCard;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;

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
//        System.out.println("Packet in ClientHandler: " + packet);
        String[] split = packet.split(" ");
        String header = split[0];

        switch (header) {
            case "HANDSHAKE":
            game.connectedToServer = true;
                System.out.println("CLIENT HANDSHAKE");
            break;
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

                }
                game.playersInGame = game.playerNames.size();

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
                String[] cardSplit = split[1].split("!"); // Split on the character "!"

                ProgramCard card;
                String name = "";
                ArrayList<ProgramCard> allCards = new ArrayList<>();
                for(int i = 1; i < split.length-3; i+=3) {
                    name = split[i]; // Name will be same for all cards
                    String rotate = split[i+1];
                    String move = split[i+2];
                    String priority = split[i+3].split("!")[0];
                    card = new ProgramCard(rotate, move, priority);
                    allCards.add(card);
                }
                ((MultiplayerLogic)game.gameScreen.getBoardLogic())
                        .receiveCardsFromServer(name.split("!")[1], allCards);
                break;
            }
            case "ALL_READY":
                game.gameScreen.getBoardLogic().setToRound();
                break;
            case "MULTI":
                game.multiPlayer = true;
                break;
            case "RECEIVE_CARD":{
//                System.out.println("RECEIVE_CARD! " + split[1]);
                final String name = split[1];
                String rotate = split[2];
                String move = split[3];
                String priority = split[4];
                final ProgramCard card = new ProgramCard(rotate, move, priority);
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        ((MultiplayerLogic)game.gameScreen.getBoardLogic()).receiveCardsFromServer(name, card);
                        game.gameScreen.getHud().updateCardButtons();
                        game.gameScreen.getHud().getHandDisplay().updateCardButtons();
                    }
                });
                break;
            }
            case "POWER_DOWN":
                String nameOfSender = split[1];
                for(Player player : game.getBoard().players) {
                    if(player.getName().equals(nameOfSender) && player.getPlayerState().equals(PlayerState.OPERATIONAL) && !player.getName().equals(game.getBoard().getThisPlayer().getName())) {
                        player.powerDown();
                    }
                }
                break;
            case "POWER_UP":
                nameOfSender = split[1];
                for(Player player : game.getBoard().players) {
                    if(player.getName().equals(nameOfSender) && player.getPlayerState().equals(PlayerState.POWERED_DOWN)&& !player.getName().equals(game.getBoard().getThisPlayer().getName())) {
                        player.powerUp();
                    }
                }
                break;
            case "SET_NUMBER_OF_PLAYERS": {
                Integer numPlayers = Integer.parseInt(split[1]);
                System.out.println("Number of players: " + numPlayers);
                game.setNumberOfChosenPlayers(numPlayers);
                break;
            }
            case "DIED":
                System.out.println(split[1] + " has died the game");
                game.playersInGame--;
                removePlayer(split[1]);
                break;
            case "REMOVED":
                System.out.println(split[1] + " has left the game");
                removePlayer(split[1]);
                break;
            default:
                System.out.println(packet);
                break;

        }
    }

    private void removePlayer(String name) {
        for (int i = 0; i < game.gameScreen.getBoard().players.size(); i++) {
            if(game.gameScreen.getBoard().players.get(i).getName().equals(name)){
                Player leaver = game.gameScreen.getBoard().players.get(i);
                leaver.killPlayer();
                game.gameScreen.getBoard().players.remove(i);
                break;
            }
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Host disconnected");
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
}

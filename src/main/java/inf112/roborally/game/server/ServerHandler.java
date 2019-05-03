package inf112.roborally.game.server;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.objects.Position;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.player.ProgramCard;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashMap;
import java.util.Stack;

import static java.util.Collections.shuffle;
@SuppressWarnings("Duplicates")
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    private final RoboRallyGame game;
    protected Stack<ProgramCard> returnedProgramCards;
    protected Stack<ProgramCard> stackOfProgramCards;
    protected HashMap<Channel, String> connectedPlayers;

    public ServerHandler(RoboRallyGame game) {
        this.game = game;
        stackOfProgramCards = ProgramCard.makeProgramCardDeck();
        returnedProgramCards = new Stack<>();
        connectedPlayers = new HashMap<>();
    }

    public static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            if(channel != incoming) {
                channel.writeAndFlush("LEFT " + connectedPlayers.get(incoming) + "\r\n" );
            }
        }
        channels.remove(ctx.channel());
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        Channel incoming = channelHandlerContext.channel();
        for (Channel channel : channels) {
            if (channel != incoming)
                channel.writeAndFlush("[" + incoming.remoteAddress() + "] " + msg + "\n");
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String packet = msg.toString();
//        System.out.println("Packet: " + packet);
        String[] split = packet.split(" ", 2);
        String header = split[0];

        switch (header) {
            case "HANDSHAKE":
                System.out.println("[SERVER] " + split[1] + " has connected!");
                game.playerNames.add(split[1]);
                connectedPlayers.put(ctx.channel(), split[1]);
                ctx.channel().writeAndFlush("HANDSHAKE SERVER");

                for (Channel channel : channels) {
                    channel.writeAndFlush(split[1] + " has connected!" + "\r\n");
                }
                break;
            case "REQUEST_CARDS":
                String[] cardlimitAndName = split[1].split(" ");
                String name = cardlimitAndName[1].trim();
                int cardLimit = Integer.parseInt(cardlimitAndName[0]);
                for (int i = 0; i < cardLimit; i++) {
                    String card = stackOfProgramCards.pop().toString();
                    if (stackOfProgramCards.isEmpty()) { // in case the game drags on and we run out of cards - Morten
                        reshuffleDeck();
                    }
                    for (Channel channel : channels) {
                        channel.writeAndFlush("RECEIVE_CARD " + name + " " + card + "\r\n");
                    }
                }
                break;
            case "CARD":
                String saveSplit1 = split[1];
                String[] cardSplit = split[1].split("!"); // Split on newline

                for(int i = 0; i < cardSplit.length; i++) {
                    String[] oneCardInformation = cardSplit[i].split(" ");
                    String rotate = oneCardInformation[1];
                    String move = oneCardInformation[2];
                    String priority = oneCardInformation[3].trim(); // This needs to be trimmed it comes with a newline
                    ProgramCard card = new ProgramCard(rotate, move, priority);
                    returnedProgramCards.add(card);
                }

                for (Channel channel : channels) {
                    channel.writeAndFlush(split[0] + " " + saveSplit1 + "\r\n");
                }
                ready(); // Since a player has received his cards, set another player to ready
                break;
            case "POWER_DOWN":
                for (Channel channel : channels) {
                    channel.writeAndFlush(split[0] + " " + split[1] + "\r\n");
                }
                ready();
                break;
            case "READY_AFTER_LEAVER":
                ready();

                break;

            default:
                for (Channel channel : channels) {
                    channel.writeAndFlush(split[0] + " " + split[1] + "\r\n");
                }
                break;
        }
    }

    private void ready() {
        game.readyPlayers++;
        System.out.println("One more player is ready");
        System.out.printf("%d out of %d players are ready%n", game.readyPlayers, game.playerNames.size());
        if(game.readyPlayers == game.playerNames.size()) {
            System.out.println("ALL PLAYERS ARE READY");
            for (Channel channel : channels) {
                channel.writeAndFlush("ALL_READY PAYLOAD\r\n");
            }
            game.readyPlayers = 0;
        }
    }

    private void reshuffleDeck() {
        while (!returnedProgramCards.empty())
            stackOfProgramCards.push(returnedProgramCards.pop());
        shuffle(stackOfProgramCards);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
    }
}

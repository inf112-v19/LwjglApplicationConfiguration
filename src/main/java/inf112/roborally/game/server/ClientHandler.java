package inf112.roborally.game.server;

import inf112.roborally.game.RoboRallyGame;
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
        else {
            System.out.println(packet);
        }


    }
}

package inf112.roborally.game.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatClientHandler extends ChannelInboundHandlerAdapter {

    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        System.out.println(o.toString());
        }

}

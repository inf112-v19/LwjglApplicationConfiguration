package inf112.roborally.game.server;

import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.enums.Rotate;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatClient implements Runnable{

    private final String host;
    private final int port;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatClientInit());
            Channel channel = null;
            try {
                channel = bootstrap.connect(host, port).sync().channel();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                ProgramCard cd = null;
                try {
                    cd = new ProgramCard(Rotate.NONE, Integer.parseInt(in.readLine()), 0);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                channel.writeAndFlush(cd + "\r\n");

            }
        }
        finally {
            group.shutdownGracefully();
        }
    }
/*
    public static void main(String[] args) throws Exception {
        new ChatClient("10.111.35.178", 8000).run();
    }
    */
}

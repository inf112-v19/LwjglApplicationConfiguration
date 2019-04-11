package inf112.roborally.game.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        new ChatClient("localhost", 8000).run();
    }
    private final String host;
    private final int port;

    public ChatClient(String host, int port){
        this.host = host;
        this.port = port;
    }
    public void run() throws IOException, InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try{
            Bootstrap bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class)
                    .handler(new ChatClientInit());
            Channel channel = bootstrap.connect(host, port).sync().channel();

            BufferedReader in   = new BufferedReader(new InputStreamReader(System.in));

            while(true){
                channel.writeAndFlush(in.readLine() + "\r\n");
            }
        }
        finally {
                group.shutdownGracefully();
        }
    }
}

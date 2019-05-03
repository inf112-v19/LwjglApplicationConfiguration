package inf112.roborally.game.server;

import inf112.roborally.game.RoboRallyGame;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server implements Runnable{

    private final int port;
    private final RoboRallyGame game;
    public Dealer dealer;
    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;
    ServerBootstrap bootstrap;

    public Server(int port, RoboRallyGame game) {
        this.port = port;
        this.game = game;
    }

    @Override
    public void run() {
        System.out.println("Starting game server..");
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        dealer = new Dealer(this.game, this);

        try {
             bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInit(game)).childOption(ChannelOption.AUTO_READ, true);

            try {
                bootstrap.bind(port).sync().channel().closeFuture().sync();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        finally {
            shutDown();
        }
    }

    public void shutDown(){
        System.out.println("Shutting down server");
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}

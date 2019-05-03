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

    public Server(int port, RoboRallyGame game) {
        this.port = port;
        this.game = game;
    }

    @Override
    public void run() {
        System.out.println("Starting game server..");
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        dealer = new Dealer(this.game, this);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
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
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
/*
    public static void main(String[] args) throws InterruptedException, SocketException {
        Enumeration<NetworkInterface> a = NetworkInterface.getNetworkInterfaces();
        while (a.hasMoreElements()){
            Enumeration<InetAddress> b = a.nextElement().getInetAddresses();
            while (b.hasMoreElements()){
                System.out.println(b.nextElement());
            }
            System.out.println();
        }
        new Server(8000).run();
    }
    */
}

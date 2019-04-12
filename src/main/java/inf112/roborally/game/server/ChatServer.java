package inf112.roborally.game.server;

import inf112.roborally.game.RoboRallyGame;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ChatServer implements Runnable{

    private final int port;
    private final RoboRallyGame game;

    public ChatServer(int port, RoboRallyGame game) {
        this.port = port;
        this.game = game;
    }

    @Override
    public void run() {
        System.out.println("Starting game server..");
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChatServerInit(game));

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
        new ChatServer(8000).run();
    }
    */
}

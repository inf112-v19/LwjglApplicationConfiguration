package inf112.roborally.game.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class ChatServer implements Runnable{

    private final int port;

    public ChatServer(int port) {
        this.port = port;
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
                    .childHandler(new ChatServerInit());

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

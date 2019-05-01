package inf112.roborally.game.server;

import inf112.roborally.game.RoboRallyGame;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

@SuppressWarnings("Duplicates")
public class ServerInit extends ChannelInitializer<SocketChannel> {
    private final RoboRallyGame game;

    public ServerInit(RoboRallyGame game) {
        this.game = game;
    }

    @Override
    protected void initChannel(SocketChannel arg0) throws Exception {
        ChannelPipeline pipeline = arg0.pipeline();
        pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.weakCachingResolver(null)));
        pipeline.addLast("encoder", new ObjectEncoder());
        pipeline.addLast(new ServerHandler(game));
    }
}

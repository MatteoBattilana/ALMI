package socket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class SocketChannelInitializer extends ChannelInitializer<Channel>
{
    @Override
    protected void initChannel(Channel channel)
    {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new ObjectEncoder())
          .addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())))
          .addLast(new MessageInboundHandler())
          .addLast(new ExceptionHandler());
    }
}

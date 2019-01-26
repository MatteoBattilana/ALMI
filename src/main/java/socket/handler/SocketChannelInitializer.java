package socket.handler;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

@Singleton
public class SocketChannelInitializer extends ChannelInitializer<Channel>
{
    private final MessageInboundHandler mMessageInboundHandler;

    @Inject
    public SocketChannelInitializer(
      MessageInboundHandler messageInboundHandler
    )
    {
        mMessageInboundHandler = messageInboundHandler;
    }

    @Override
    protected void initChannel(Channel channel)
    {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new ObjectEncoder())
          .addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())))
          .addLast(mMessageInboundHandler)
          .addLast(new ExceptionHandler());
    }
}

package socket.handler;

import com.fasterxml.jackson.databind.util.ByteBufferBackedOutputStream;
import com.google.inject.Inject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.stream.ChunkedWriteHandler;
import utils.Constants;

import java.net.InetSocketAddress;

public class SocketChannelInitializer extends ChannelInitializer<Channel>
{
    private final MessageDecoderFactory mMessageDecoderFactory;

    @Inject
    public SocketChannelInitializer(
      MessageDecoderFactory messageDecoderFactory
    )
    {
        mMessageDecoderFactory = messageDecoderFactory;
    }

    @Override
    protected void initChannel(Channel channel)
    {
        ChannelPipeline pipeline = channel.pipeline();
        InetSocketAddress localAddress = (InetSocketAddress) channel.localAddress();

        // TODO: Add SSL/TLS
        // channel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Constants.MAX_FRAME_SIZE, 0, 4, 0, 4));
        // channel.pipeline().addLast(new LengthFieldPrepender(4));
        pipeline.addLast(mMessageDecoderFactory.create());
        switch(localAddress.getPort())
        {
            case Constants.MESSAGE_PORT:
                pipeline.addLast(new MessageEncoder())
                  .addLast(new HandshakeInboundHandler())
                  .addLast(new ExceptionHandler());
                break;
            case Constants.STREAM_PORT:
                pipeline.addLast(new ChunkedWriteHandler());
                pipeline.addLast(new StreamInboundHandler());
                break;
        }

    }
}

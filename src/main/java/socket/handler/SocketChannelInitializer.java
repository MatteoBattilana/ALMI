package socket.handler;

import com.google.inject.Inject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

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
        //SSL/TLS
        // TODO: Add SSL/TLS
        // channel.pipeline().addFirst(new SslHandler())

        //encoder/decoder
        // TODO: when sending check if size + 8 > MAX_FRAME_SIZE -> then give error if true
        // https://stackoverflow.com/questions/18420049/sending-tcp-packets-via-netty-netty-is-dividing-the-data-into-different-packets

        // channel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Constants.MAX_FRAME_SIZE, 0, 4, 0, 4));
        // channel.pipeline().addLast(new LengthFieldPrepender(4));
        channel.pipeline().addLast(new MessageEncoder());
        channel.pipeline().addLast(mMessageDecoderFactory.create());

        //inbound
        channel.pipeline().addLast(new HandshakeInboundHandler());
        channel.pipeline().addLast(new MessageInboundHandler());
        channel.pipeline().addLast(new ExceptionHandler());
    }
}

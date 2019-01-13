package socket.handler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import utils.Constants;

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
        // channel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Constants.MAX_FRAME_SIZE, 0, 8));
        // channel.pipeline().addLast(new LengthFieldPrepender(8));
        channel.pipeline().addLast(new MessageEncoder());
        channel.pipeline().addLast(mMessageDecoderFactory.create());

        //inbounder
        channel.pipeline().addLast(new WelcomeInboundHandler());
        channel.pipeline().addLast(new MessageInboundHandler());
        channel.pipeline().addLast(new ExceptionHandler());
    }
}

package socket.handler;

import exceptions.AlmiException;
import exceptions.HandshakeException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import message.BaseMessage;
import message.HandshakeMessage;
import message.Message;
import utils.NettyAttribute;

public class HandshakeInboundHandler extends SimpleChannelInboundHandler<HandshakeMessage>
{
    public static final AttributeKey<Boolean> sHandshakeRequester = AttributeKey.valueOf("handshakeDone");

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HandshakeMessage in)
      throws AlmiException
    {
        try
        {
            switch(in.getType())
            {
                case HANDSHAKE_RESPONSE:
                    //TODO: check compatibility before remote this handler!
                    ctx.pipeline().remove(this);
                case HANDSHAKE_REQUEST:
                    ctx.writeAndFlush(in.interpret());
                    NettyAttribute.set(ctx, sHandshakeRequester, true);
                    break;
            }
        }
        finally
        {
            ReferenceCountUtil.release(in);
        }
    }
}

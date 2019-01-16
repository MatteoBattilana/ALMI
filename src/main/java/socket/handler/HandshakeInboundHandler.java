package socket.handler;

import exceptions.AlmiException;
import exceptions.HandshakeException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import message.HandshakeMessage;
import utils.NettyAttribute;

public class HandshakeInboundHandler extends SimpleChannelInboundHandler<HandshakeMessage>
{
    @Override
    public void channelRead0(ChannelHandlerContext ctx, HandshakeMessage in)
      throws AlmiException
    {
        try
        {
            switch(in.getType())
            {
                case HANDSHAKE_REQUEST:
                    if(!isCompatible())
                    {
                        throw new HandshakeException();
                    }
                case HANDSHAKE_RESPONSE:
                    ctx.pipeline().remove(this)
                      .addLast(new MessageInboundHandler());
                    ctx.writeAndFlush(in.generateResponse());
                    break;
            }
        }
        finally
        {
            ReferenceCountUtil.release(in);
        }
    }

    private boolean isCompatible()
    {
        //TODO: check compatibility! Eg. version -> if not match close connection
        return true;
    }
}

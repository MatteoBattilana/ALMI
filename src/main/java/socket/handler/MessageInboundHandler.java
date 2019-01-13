package socket.handler;

import exceptions.AlmiException;
import exceptions.HandshakeException;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import message.BaseMessage;
import utils.NettyAttribute;

@ChannelHandler.Sharable
public class MessageInboundHandler extends SimpleChannelInboundHandler<BaseMessage>
{
    @Override
    public void channelRead0(ChannelHandlerContext ctx, BaseMessage msg) throws AlmiException
    {
        assertHandshake(ctx);
        try
        {
            ctx.writeAndFlush(msg.interpret());
        }
        finally
        {
            ReferenceCountUtil.release(msg);
        }
    }

    private void assertHandshake(ChannelHandlerContext ctx)
      throws HandshakeException
    {
        if(!NettyAttribute.get(ctx, HandshakeInboundHandler.sHandshakeRequester, false))
        {
            throw new HandshakeException();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
    {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx)
    {
        System.out.println("Closed connection!");
        ctx.fireChannelInactive();
    }
}

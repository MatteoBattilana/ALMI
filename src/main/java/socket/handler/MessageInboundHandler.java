package socket.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import message.BaseMessage;

@ChannelHandler.Sharable
public class MessageInboundHandler extends SimpleChannelInboundHandler<BaseMessage>
{
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BaseMessage stupidClass)
    {
        try
        {
            System.out.println("Received " + stupidClass.getClass());
        }
        finally
        {
            ReferenceCountUtil.release(stupidClass);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx)
    {
        System.out.println("Closed connection!");
        ctx.fireChannelInactive();
    }
}

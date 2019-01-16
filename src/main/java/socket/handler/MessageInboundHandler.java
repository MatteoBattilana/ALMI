package socket.handler;

import exceptions.AlmiException;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import message.BaseMessage;

@ChannelHandler.Sharable
public class MessageInboundHandler extends SimpleChannelInboundHandler<BaseMessage>
{
    @Override
    public void channelRead0(ChannelHandlerContext ctx, BaseMessage msg) throws AlmiException
    {
        try
        {
            ctx.writeAndFlush(msg.generateResponse());
        }
        finally
        {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx)
    {
        System.out.println("Closed connection!");
        ctx.fireChannelInactive();
    }
}

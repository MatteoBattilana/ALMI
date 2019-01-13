package socket.handler;

import exceptions.BlockingRequestException;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import message.BaseMessage;

@ChannelHandler.Sharable
public class MessageInboundHandler extends SimpleChannelInboundHandler<BaseMessage>
{
    @Override
    public void channelRead0(ChannelHandlerContext ctx, BaseMessage msg)
    {
        try
        {
            ctx.writeAndFlush(msg.generateResponse());
        }
        catch(BlockingRequestException ignore)
        {
        }
        finally
        {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
    {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx)
      throws Exception
    {
        System.out.println("Closed connection!");
        ctx.fireChannelInactive();
    }
}

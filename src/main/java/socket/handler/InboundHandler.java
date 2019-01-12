package socket.handler;

import exceptions.BlockingRequestException;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import message.BaseMessage;

@ChannelHandler.Sharable
public class InboundHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
    {
        BaseMessage in = (BaseMessage) msg;
        try
        {
            ctx.writeAndFlush(in.generateResponse());
        }
        catch(BlockingRequestException ignore)
        {
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
    {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }
}

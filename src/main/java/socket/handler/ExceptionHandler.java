package socket.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import message.ErrorMessage;

public class ExceptionHandler extends ChannelHandlerAdapter
{
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        ctx.writeAndFlush(new ErrorMessage(cause));
    }
}
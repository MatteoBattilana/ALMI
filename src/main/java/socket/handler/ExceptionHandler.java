package socket.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import message.ErrorMessage;

public class ExceptionHandler extends ChannelHandlerAdapter
{
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        cause.printStackTrace();
        // TODO: manage the message id
        ctx.writeAndFlush(new ErrorMessage(cause));
    }
}

package socket.handler;

import exceptions.HandshakeException;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import message.ErrorMessage;

public class ExceptionHandler extends ChannelDuplexHandler
{
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        cause.printStackTrace();
        ctx.writeAndFlush(new ErrorMessage(cause));
        if(cause instanceof HandshakeException)
        {
            ctx.close();
        }
    }
}

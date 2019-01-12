package socket.handler;

import exceptions.BlockingRequestException;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import message.BaseMessage;

@ChannelHandler.Sharable
public class WelcomeHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
      throws BlockingRequestException
    {
        BaseMessage in = (BaseMessage) msg;
        BaseMessage out = in.generateResponse();

        ctx.writeAndFlush(out);
        ctx.pipeline().remove(this);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
    {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        ctx.close();
    }
}

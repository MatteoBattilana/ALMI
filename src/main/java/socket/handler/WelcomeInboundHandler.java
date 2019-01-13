package socket.handler;

import exceptions.BlockingRequestException;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import message.BaseMessage;

@ChannelHandler.Sharable
public class WelcomeInboundHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
      throws BlockingRequestException
    {
        BaseMessage in = (BaseMessage) msg;
        BaseMessage out = in.generateResponse();
        ReferenceCountUtil.release(msg);

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
        // ctx.close();
    }
}

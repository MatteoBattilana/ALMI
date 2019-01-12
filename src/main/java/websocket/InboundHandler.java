package websocket;

import exceptions.AlmiException;
import exceptions.BlockingRequestException;
import exceptions.JsonGenerationException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import message.BaseMessage;
import message.MessageRequestParser;
import message.response.ErrorMessageResponse;

import javax.inject.Inject;

@ChannelHandler.Sharable
public class InboundHandler extends ChannelInboundHandlerAdapter
{
    private final MessageRequestParser mMessageParser;

    @Inject
    public InboundHandler(
      MessageRequestParser messageRequestParser
    )
    {
        mMessageParser = messageRequestParser;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
    {
        ByteBuf in = (ByteBuf) msg;

        try
        {
            BaseMessage request = mMessageParser.parseRequest(in.toString(CharsetUtil.UTF_8));
            sendResponse(ctx, request.generateResponse());
        }
        catch(BlockingRequestException ignore)
        {
        }
        catch(AlmiException e)
        {
            sendResponse(ctx, new ErrorMessageResponse(e));
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
    {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        cause.printStackTrace();
        ctx.close();
    }

    private void sendResponse(ChannelHandlerContext ctx, BaseMessage response)
    {
        try
        {
            ByteBuf byteBufResponse = Unpooled.copiedBuffer(response.toContainer().toJSON(), CharsetUtil.UTF_8);
            ctx.writeAndFlush(byteBufResponse);
        }
        catch(JsonGenerationException e)
        {
            e.printStackTrace();
        }
    }
}

package websocket;

import exceptions.AlmiException;
import exceptions.BlockingRequestException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import websocket.message.ErrorMessage;
import websocket.message.JSONMessage;
import websocket.message.MessageInterpreter;

import javax.inject.Inject;

@ChannelHandler.Sharable
public class InboundHandler extends ChannelInboundHandlerAdapter
{
    private final MessageInterpreter mMessageInterpreter;

    @Inject
    public InboundHandler(
      MessageInterpreter messageInterpreter
    )
    {
        mMessageInterpreter = messageInterpreter;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
    {
        ByteBuf in = (ByteBuf) msg;

        try
        {
            JSONMessage request = mMessageInterpreter.parse(in.toString(CharsetUtil.UTF_8));
            sendResponse(ctx, request.execute());
        }
        catch(BlockingRequestException ignore)
        {
        }
        catch(AlmiException e)
        {
            sendResponse(ctx, new ErrorMessage(e));
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
      throws Exception
    {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        cause.printStackTrace();
        ctx.close();
    }

    private void sendResponse(ChannelHandlerContext ctx, JSONMessage response)
    {
        ByteBuf byteBufResponse = Unpooled.copiedBuffer(response.getJSON(), CharsetUtil.UTF_8);
        ctx.writeAndFlush(byteBufResponse);
    }
}

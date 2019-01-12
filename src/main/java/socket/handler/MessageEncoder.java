package socket.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import message.BaseMessage;

public class MessageEncoder extends MessageToByteEncoder<BaseMessage>
{
    @Override
    protected void encode(
      ChannelHandlerContext channelHandlerContext, BaseMessage baseMessage, ByteBuf out
    )
      throws Exception
    {
        out.writeBytes(Unpooled.copiedBuffer(baseMessage.toJSON().toString(), CharsetUtil.UTF_8));
    }
}

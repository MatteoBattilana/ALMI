package socket.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import message.Message;
import message.MessageType;

public class MessageEncoder extends MessageToByteEncoder<Message>
{
    @Override
    protected void encode(
      ChannelHandlerContext channelHandlerContext, Message baseMessage, ByteBuf out
    )
      throws Exception
    {
        if(baseMessage.getType() != MessageType.STUB_RESPONSE)
        {
            System.out.println("Sent: " + baseMessage.toJSON().toString());
            out.writeBytes(Unpooled.copiedBuffer(baseMessage.toJSON().toString(), CharsetUtil.UTF_8));
        }
    }
}

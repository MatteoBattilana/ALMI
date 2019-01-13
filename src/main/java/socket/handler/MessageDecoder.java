package socket.handler;

import com.google.inject.Inject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import message.MessageParser;

import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder
{
    private final MessageParser mMessageParser;

    @Inject
    public MessageDecoder(
      MessageParser messageParser
    )
    {
        mMessageParser = messageParser;
    }

    @Override
    protected void decode(
      ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out
    )
      throws Exception
    {
        try
        {
            System.out.println("Received: " + in.toString(CharsetUtil.UTF_8));
            out.add(mMessageParser.parseMessage(in.toString(CharsetUtil.UTF_8)));
        }
        finally
        {
            in.resetWriterIndex();
        }
    }
}
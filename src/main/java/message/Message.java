package message;

import exceptions.AlmiException;
import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;
import java.util.UUID;

public abstract class Message implements Serializable
{
    private final String mId;

    public Message(String id)
    {
        mId = id;
    }

    public String getId()
    {
        return mId;
    }

    public static String randomId()
    {
        return UUID.randomUUID().toString();
    }

    public abstract <T> T interpret(
      MessageInterpreter<T> messageInterpreter,
      ChannelHandlerContext ctx
    )
      throws Exception;
}

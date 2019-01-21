package message;

import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;
import java.util.Optional;

public class MethodCallResponse<R extends Serializable> extends BaseMessage
{
    private final R         mReturnValue;
    private final Throwable mException;

    public MethodCallResponse(String requestId, R returnValue)
    {
        super(requestId);
        mReturnValue = returnValue;
        mException = null;
    }

    public MethodCallResponse(String requestId, Throwable exception)
    {
        super(requestId);
        mException = exception;
        mReturnValue = null;
    }

    public R getReturnValue() throws Throwable
    {
        if(mException != null)
        {
            throw mException;
        }
        return mReturnValue;
    }

    public <T> T interpret(MessageInterpreter<T> messageInterpreter, ChannelHandlerContext ctx)
      throws Exception
    {
        return messageInterpreter.interpret(this, ctx);
    }
}
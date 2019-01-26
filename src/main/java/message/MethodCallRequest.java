package message;

import exceptions.AlmiException;
import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;
import java.util.List;

public class MethodCallRequest extends BaseMessage
{
    private final String             mMethodName;
    private final List<Serializable> mMethodParameters;

    public MethodCallRequest(String requestId, String methodName, List<Serializable> params)
    {
        super(requestId);
        this.mMethodName = methodName;
        this.mMethodParameters = params;
    }

    public MethodCallRequest(String methodName, List<Serializable> params)
    {
        this(BaseMessage.randomId(), methodName, params);
    }

    public String getMethodName()
    {
        return mMethodName;
    }

    public List<Serializable> getMethodParameters()
    {
        return mMethodParameters;
    }

    public <T> T interpret(MessageInterpreter<T> messageInterpreter, ChannelHandlerContext ctx)
      throws Exception
    {
        return messageInterpreter.interpret(this, ctx);
    }
}

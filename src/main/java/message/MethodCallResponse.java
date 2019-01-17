package message;

import java.io.Serializable;

public class MethodCallResponse<T extends Serializable> extends BaseMessage
{
    private final T mReturnValue;

    public MethodCallResponse(String requestId, T returnValue)
    {
        super(requestId);
        mReturnValue = returnValue;
    }

    public T getReturnValue()
    {
        return mReturnValue;
    }

    public <K> K interpret(MessageInterpreter<K> messageInterpreter)
    {
        return messageInterpreter.interpret(this);
    }
}

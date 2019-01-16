package message;

public class MethodCallResponse extends BaseMessage
{
    private final Object mReturnValue;

    public MethodCallResponse(String requestId, Object returnValue)
    {
        super(requestId);
        mReturnValue = returnValue;
    }

    public Object getReturnValue()
    {
        return mReturnValue;
    }
}

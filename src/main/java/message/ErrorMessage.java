package message;

public class ErrorMessage extends BaseMessage
{
    private final Throwable mThrowable;

    public ErrorMessage(String requestId, Throwable throwable)
    {
        super(requestId);
        mThrowable = throwable;
    }

    public ErrorMessage(Throwable throwable)
    {
        this(Message.randomId(), throwable);
    }

    public Throwable getThrowable()
    {
        return mThrowable;
    }
}

package exceptions;

import message.MessageType;

public class BlockingRequestException extends AlmiException
{
    public BlockingRequestException(MessageType type)
    {
        super(getErrorMessage(type));
    }

    public BlockingRequestException(Throwable throwable)
    {
        super(throwable);
    }

    public BlockingRequestException(MessageType type, Throwable throwable)
    {
        super(getErrorMessage(type), throwable);
    }

    private static String getErrorMessage(MessageType type)
    {
        return String.format("Blocking request type: %s", type.toString());
    }
}

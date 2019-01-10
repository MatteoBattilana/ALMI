package exceptions;

import message.JSONMessage;

public class BlockingRequestException extends AlmiException
{
    public BlockingRequestException(JSONMessage.MessageType type)
    {
        super(getErrorMessage(type));
    }

    public BlockingRequestException(Throwable throwable)
    {
        super(throwable);
    }

    public BlockingRequestException(JSONMessage.MessageType type, Throwable throwable)
    {
        super(getErrorMessage(type), throwable);
    }

    private static String getErrorMessage(JSONMessage.MessageType type)
    {
        return String.format("Blocking request type: %s", type.toString());
    }
}

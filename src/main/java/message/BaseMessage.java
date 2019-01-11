package message;

import exceptions.BlockingRequestException;

import java.util.UUID;

public abstract class BaseMessage
{
    private final MessageType mMessageType;
    private final String      mId;

    public BaseMessage(MessageType messageType, String id)
    {
        mMessageType = messageType;
        mId = id;
    }

    public String getId()
    {
        return mId;
    }

    public MessageType getType()
    {
        return mMessageType;
    }

    public static String randomId()
    {
        return UUID.randomUUID().toString();
    }

    public abstract String getJSON();
    public abstract BaseMessage generateResponse()
      throws BlockingRequestException;
}

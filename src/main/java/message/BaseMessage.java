package message;

import exceptions.BlockingRequestException;
import exceptions.JsonGenerationException;
import utils.Constants;
import utils.Container;
import utils.SimpleContainer;

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

    public Container toContainer()
      throws JsonGenerationException
    {
        SimpleContainer info = new SimpleContainer();
        info.put(Constants.JSON_MESSAGE_TYPE, getType());
        info.put(Constants.JSON_MESSAGE_ID, getId());

        return info;
    }

    public abstract BaseMessage generateResponse()
      throws BlockingRequestException;
}

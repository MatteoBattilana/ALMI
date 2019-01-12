package message;

import exceptions.BlockingRequestException;
import exceptions.JSONGenerationException;
import org.json.JSONObject;
import utils.Constants;

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

    public JSONObject toJSON()
      throws JSONGenerationException
    {
        JSONObject info = new JSONObject();
        info.put(Constants.JSON_MESSAGE_TYPE, getType().toString());
        info.put(Constants.JSON_MESSAGE_ID, getId());

        return info;
    }

    public abstract BaseMessage generateResponse()
      throws BlockingRequestException;
}

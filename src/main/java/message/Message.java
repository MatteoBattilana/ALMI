package message;

import exceptions.JSONGenerationException;
import org.json.JSONObject;
import utils.Constants;

import java.util.UUID;

public abstract class Message
{
    private final MessageType mMessageType;
    private final String      mId;

    public Message(MessageType messageType, String id)
    {
        mMessageType = messageType;
        mId = id;
    }

    protected String getId()
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

    public abstract Message interpret();
}

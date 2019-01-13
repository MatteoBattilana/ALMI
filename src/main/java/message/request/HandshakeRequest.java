package message.request;

import exceptions.JSONGenerationException;
import message.BaseMessage;
import message.HandshakeMessage;
import message.Message;
import message.MessageType;
import message.response.HandshakeResponse;
import org.json.JSONObject;
import utils.Constants;

public class HandshakeRequest extends HandshakeMessage
{
    private HandshakeRequest(String requestId)
    {
        super(MessageType.HANDSHAKE_REQUEST, requestId);
    }

    public HandshakeRequest()
    {
        this(BaseMessage.randomId());
    }

    @Override
    public HandshakeMessage interpret()
    {
        return new HandshakeResponse(getId());
    }

    @Override
    public JSONObject toJSON()
      throws JSONGenerationException
    {
        return super.toJSON();
    }

    public static HandshakeRequest parse(JSONObject json)
    {
        return new HandshakeRequest(json.getString(Constants.JSON_MESSAGE_ID));
    }
}

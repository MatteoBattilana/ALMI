package message.response;

import exceptions.JSONGenerationException;
import message.BaseMessage;
import message.HandshakeMessage;
import message.Message;
import message.MessageType;
import org.json.JSONObject;
import utils.Constants;

public class HandshakeResponse extends HandshakeMessage
{
    public HandshakeResponse(String requestId)
    {
        super(MessageType.HANDSHAKE_RESPONSE, requestId);
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

    public static HandshakeResponse parse(JSONObject json)
    {
        String messageId = json.getString(Constants.JSON_MESSAGE_ID);

        return new HandshakeResponse(messageId);
    }
}

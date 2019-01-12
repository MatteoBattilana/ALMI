package message.response;

import exceptions.BlockingRequestException;
import exceptions.JSONGenerationException;
import message.BaseMessage;
import message.MessageType;
import org.json.JSONObject;
import utils.Constants;

public class WelcomeResponse extends BaseMessage
{
    public WelcomeResponse(String requestId)
    {
        super(MessageType.METHOD_WELCOME_RESPONSE, requestId);
    }

    @Override
    public BaseMessage generateResponse()
      throws BlockingRequestException
    {
        throw new BlockingRequestException(getType());
    }

    @Override
    public JSONObject toJSON()
      throws JSONGenerationException
    {
        return super.toJSON();
    }

    public static WelcomeResponse parse(JSONObject json)
    {
        String messageId = json.getString(Constants.JSON_MESSAGE_ID);

        return new WelcomeResponse(messageId);
    }
}

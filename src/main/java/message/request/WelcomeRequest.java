package message.request;

import exceptions.JSONGenerationException;
import message.BaseMessage;
import message.MessageType;
import message.response.MethodCallResponse;
import message.response.WelcomeResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Constants;
import utils.Container;

import java.util.ArrayList;
import java.util.List;

public class WelcomeRequest extends BaseMessage
{
    private WelcomeRequest(String requestId)
    {
        super(MessageType.METHOD_WELCOME_REQUEST, requestId);
    }

    public WelcomeRequest()
    {
        this(BaseMessage.randomId());
    }

    @Override
    public BaseMessage generateResponse()
    {
        return new WelcomeResponse(getId());
    }

    @Override
    public JSONObject toJSON()
      throws JSONGenerationException
    {
        return super.toJSON();
    }

    public static WelcomeRequest parse(JSONObject json)
    {
        return new WelcomeRequest(json.getString(Constants.JSON_MESSAGE_ID));
    }
}

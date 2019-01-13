package message.response;

import exceptions.JSONGenerationException;
import message.BaseMessage;
import message.MessageType;
import org.json.JSONObject;
import utils.Constants;

public class StubResponse extends BaseMessage
{
    public StubResponse(String requestId)
    {
        super(MessageType.STUB_RESPONSE, requestId);
    }

    @Override
    public BaseMessage interpret()
    {
        return this;
    }

    @Override
    public JSONObject toJSON()
      throws JSONGenerationException
    {
        return super.toJSON();
    }

    public static StubResponse parse(JSONObject json)
    {
        String messageId = json.getString(Constants.JSON_MESSAGE_ID);

        return new StubResponse(messageId);
    }
}

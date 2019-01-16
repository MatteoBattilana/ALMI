package message.request;

import exceptions.JSONGenerationException;
import message.BaseMessage;
import message.HandshakeMessage;
import message.Message;
import message.MessageType;
import message.StreamMessage;
import message.response.HandshakeResponse;
import message.response.StubResponse;
import org.json.JSONObject;
import utils.Constants;

public class StreamRequest extends StreamMessage
{
    private String mStreamId;

    private StreamRequest(String requestId, String streamId)
    {
        super(MessageType.STREAM_REQUEST, requestId);
    }

    public StreamRequest(String streamId)
    {
        this(BaseMessage.randomId(), streamId);
    }

    @Override
    public Message generateResponse()
    {
        return new StubResponse(getId());
    }

    @Override
    public JSONObject toJSON()
      throws JSONGenerationException
    {
        return super.toJSON();
    }

    public static StreamRequest parse(JSONObject json)
    {
        return new StreamRequest(json.getString(Constants.JSON_MESSAGE_ID));
    }
}

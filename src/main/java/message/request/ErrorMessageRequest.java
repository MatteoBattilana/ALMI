package message.request;

import exceptions.BlockingRequestException;
import exceptions.MalformedRequestException;
import message.BaseMessage;
import message.response.ErrorMessageResponse;
import org.json.JSONObject;

public class ErrorMessageRequest extends ErrorMessageResponse
{
    private ErrorMessageRequest(String messageId, Throwable throwable)
    {
        super(messageId, throwable);
    }

    public ErrorMessageRequest(Throwable throwable)
    {
        this(BaseMessage.randomId(), throwable);
    }

    public static ErrorMessageRequest parse(JSONObject json) throws MalformedRequestException
    {
        ErrorMessageResponse messageResponse = ErrorMessageResponse.parse(json);
        return new ErrorMessageRequest(messageResponse.getId(), messageResponse.getThrowable());
    }

    @Override
    public BaseMessage generateResponse()
      throws BlockingRequestException
    {
        throw new BlockingRequestException(getType());
    }
}

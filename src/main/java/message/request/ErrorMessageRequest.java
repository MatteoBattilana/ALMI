package message.request;

import exceptions.BlockingRequestException;
import exceptions.InvalidRequestException;
import message.BaseMessage;
import message.response.ErrorMessageResponse;
import org.json.JSONObject;

public class ErrorMessageRequest extends ErrorMessageResponse
{
    public ErrorMessageRequest(String messageId, Throwable throwable)
    {
        super(messageId, throwable);
    }

    public static ErrorMessageRequest parse(JSONObject json) throws InvalidRequestException
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

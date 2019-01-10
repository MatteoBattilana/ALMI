package message.request;

import exceptions.BlockingRequestException;
import exceptions.InvalidRequestException;
import message.JSONMessage;
import message.response.ErrorMessageResponse;
import org.json.JSONObject;
import utils.Interpreter;

public class ErrorMessageRequest extends ErrorMessageResponse implements Interpreter<JSONMessage>
{
    public ErrorMessageRequest(String messageId, Throwable throwable)
    {
        super(messageId, throwable);
    }

    public ErrorMessageRequest(Throwable throwable)
    {
        super(throwable);
    }

    public static ErrorMessageRequest parse(JSONObject json) throws InvalidRequestException
    {
        ErrorMessageResponse messageResponse = ErrorMessageResponse.parse(json);
        return new ErrorMessageRequest(messageResponse.getId(), messageResponse.getThrowable());
    }

    @Override
    public JSONMessage interpret() throws BlockingRequestException
    {
        throw new BlockingRequestException(getType());
    }
}

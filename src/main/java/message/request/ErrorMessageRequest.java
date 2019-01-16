package message.request;

import exceptions.ClassConversionException;
import exceptions.JSONGenerationException;
import exceptions.MalformedRequestException;
import message.BaseMessage;
import message.Message;
import message.MessageType;
import message.response.StubResponse;
import org.json.JSONObject;
import utils.Constants;
import utils.TypeUtils;

public class ErrorMessageRequest extends BaseMessage
{
    private final Throwable mThrowable;

    public ErrorMessageRequest(String requestId, Throwable throwable)
    {
        super(MessageType.ERROR, requestId);
        mThrowable = throwable;
    }

    public ErrorMessageRequest(Throwable throwable)
    {
        this(Message.randomId(), throwable);
    }

    public Throwable getThrowable()
    {
        return mThrowable;
    }

    @Override
    public JSONObject toJSON()
      throws JSONGenerationException
    {
        try
        {
            JSONObject json = super.toJSON();
            json.put(Constants.JSON_EXCEPTION, TypeUtils.toString(mThrowable.getMessage()));
            return json;
        }
        catch(ClassConversionException e)
        {
            throw new JSONGenerationException(e);
        }
    }

    @Override
    public BaseMessage generateResponse()
    {
        return new StubResponse(getId());
    }

    public static ErrorMessageRequest parse(JSONObject json)
      throws MalformedRequestException
    {
        try
        {
            String messageId = json.getString(Constants.JSON_MESSAGE_ID);
            Throwable ex = TypeUtils.convertInstanceOfObject(
              TypeUtils.fromString(json.getString(Constants.JSON_EXCEPTION)),
              Throwable.class
            );
            return new ErrorMessageRequest(messageId, ex);
        }
        catch(ClassConversionException e)
        {
            throw new MalformedRequestException(json.toString(), e);
        }
    }
}

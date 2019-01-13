package message.response;

import exceptions.ClassConversionException;
import exceptions.JSONGenerationException;
import exceptions.MalformedRequestException;
import message.BaseMessage;
import message.MessageType;
import org.json.JSONObject;
import utils.Constants;
import utils.TypeUtils;

public class ErrorMessageResponse extends BaseMessage
{
    private final Throwable mThrowable;

    public ErrorMessageResponse(String requestId, Throwable throwable)
    {
        super(MessageType.ERROR, requestId);
        mThrowable = throwable;
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
    public BaseMessage interpret()
    {
        return new StubResponse(getId());
    }

    public static ErrorMessageResponse parse(JSONObject json)
      throws MalformedRequestException
    {
        try
        {
            String messageId = json.getString(Constants.JSON_MESSAGE_ID);
            Throwable ex = TypeUtils.convertInstanceOfObject(
              TypeUtils.fromString(json.getString(Constants.JSON_EXCEPTION)),
              Throwable.class
            );
            return new ErrorMessageResponse(messageId, ex);
        }
        catch(ClassConversionException e)
        {
            throw new MalformedRequestException(json.toString(), e);
        }
    }
}

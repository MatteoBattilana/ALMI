package message.response;

import exceptions.BlockingRequestException;
import exceptions.ClassConversionException;
import exceptions.MalformedRequestException;
import exceptions.JSONGenerationException;
import message.BaseMessage;
import message.MessageType;
import utils.Constants;
import utils.TypeUtils;
import org.json.JSONObject;

import java.util.UUID;

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
    public BaseMessage generateResponse()
      throws BlockingRequestException
    {
        throw new BlockingRequestException(getType());
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

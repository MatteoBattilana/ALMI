package message.request;

import exceptions.BlockingRequestException;
import exceptions.ClassConversionException;
import exceptions.InvalidRequestException;
import message.JSONMessage;
import method.Constants;
import method.TypeUtils;
import org.json.JSONObject;

public class ErrorMessageRequest extends MessageRequest implements JSONMessage
{
    private final Throwable mThrowable;

    public ErrorMessageRequest(Throwable throwable)
    {
        mThrowable = throwable;
    }

    public Throwable getThrowable()
    {
        return mThrowable;
    }

    @Override
    public MessageType getType()
    {
        return MessageType.ERROR_RESPONSE;
    }

    @Override
    public String getJSON()
    {
        try
        {
            return ""
              + "{"
              + String.format(" \"%s\" : \"%s\",", Constants.JSON_MESSAGE_ID, getId())
              + String.format(" \"%s\" : \"%s\",", Constants.JSON_MESSAGE_TYPE, getType().toString())
              + String.format(" \"%s\" : \"%s\"", Constants.JSON_EXCEPTION, TypeUtils.toString(mThrowable.getMessage()))
              + "}";
        }
        catch(ClassConversionException e)
        {
            return "{}";
        }
    }

    @Override
    public JSONMessage execute()
      throws BlockingRequestException
    {
        throw new BlockingRequestException(getType());
    }

    public static ErrorMessageRequest parse(JSONObject json)
      throws InvalidRequestException
    {
        try
        {
            Throwable ex = TypeUtils.convertInstanceOfObject(
              TypeUtils.fromString(json.getString(Constants.JSON_EXCEPTION)),
              Throwable.class
            );
            return new ErrorMessageRequest(ex);
        }
        catch(ClassConversionException e)
        {
            throw new InvalidRequestException(json.toString(), e);
        }
    }
}

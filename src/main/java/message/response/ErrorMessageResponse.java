package message.response;

import exceptions.ClassConversionException;
import exceptions.InvalidRequestException;
import message.JSONMessage;
import method.Constants;
import method.TypeUtils;
import org.json.JSONObject;

import java.util.UUID;

public class ErrorMessageResponse extends MessageResponse implements JSONMessage
{
    private final Throwable mThrowable;

    public ErrorMessageResponse(String messageId, Throwable throwable)
    {
        super(messageId);
        mThrowable = throwable;
    }

    public ErrorMessageResponse(Throwable throwable)
    {
        this(UUID.randomUUID().toString(), throwable);
    }

    public Throwable getThrowable()
    {
        return mThrowable;
    }

    @Override
    public MessageType getType()
    {
        return MessageType.ERROR;
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

    public static ErrorMessageResponse parse(JSONObject json)
      throws InvalidRequestException
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
            throw new InvalidRequestException(json.toString(), e);
        }
    }
}

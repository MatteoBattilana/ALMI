package message.response;

import exceptions.BlockingRequestException;
import exceptions.ClassConversionException;
import exceptions.InvalidRequestException;
import message.JSONMessage;
import method.Constants;
import method.TypeUtils;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public class MethodCallResponse extends MessageResponse implements JSONMessage
{
    private final Serializable mReturnValue;

    public MethodCallResponse(String id, Serializable returnValue)
    {
        super(id);
        mReturnValue = returnValue;
    }

    public <T> T getReturnValue()
      throws ClassConversionException
    {
        try
        {
            return (T) mReturnValue;
        }
        catch(Exception e)
        {
            throw new ClassConversionException(e);
        }
    }

    @Override
    public MessageType getType()
    {
        return MessageType.METHOD_CALL_RESPONSE;
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
              + String.format(" \"%s\" : \"%s\"", Constants.JSON_RETURN_VALUE, TypeUtils.toString(mReturnValue))
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

    public static MethodCallResponse parse(JSONObject json)
      throws InvalidRequestException
    {
        try
        {
            String messageId = json.getString(Constants.JSON_MESSAGE_ID);
            String returnValue = json.getString(Constants.JSON_RETURN_VALUE);

            return new MethodCallResponse(messageId, TypeUtils.fromString(returnValue));
        }
        catch(ClassConversionException e)
        {
            throw new InvalidRequestException(json.toString(), e);
        }
    }
}

package message.response;

import exceptions.BlockingRequestException;
import exceptions.ClassConversionException;
import exceptions.InvalidRequestException;
import exceptions.JsonGenerationException;
import message.BaseMessage;
import message.MessageType;
import utils.Constants;
import utils.Container;
import utils.TypeUtils;
import org.json.JSONObject;

import java.io.Serializable;

public class MethodCallResponse extends BaseMessage
{
    private final Serializable mReturnValue;

    public MethodCallResponse(String id, Serializable returnValue)
    {
        super(MessageType.METHOD_CALL_RESPONSE, id);
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
    public Container toContainer()
      throws JsonGenerationException
    {
        try
        {
            Container json = super.toContainer();
            json.put(Constants.JSON_RETURN_VALUE, TypeUtils.toString(mReturnValue));
            return json;
        }
        catch(ClassConversionException e)
        {
            throw new JsonGenerationException(e);
        }
    }

    @Override
    public BaseMessage generateResponse()
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

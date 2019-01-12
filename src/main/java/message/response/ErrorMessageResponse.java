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

import java.util.UUID;

public class ErrorMessageResponse extends BaseMessage
{
    private final Throwable mThrowable;

    public ErrorMessageResponse(String messageId, Throwable throwable)
    {
        super(MessageType.ERROR, messageId);
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
    public Container toContainer()
      throws JsonGenerationException
    {
        try
        {
            Container json = super.toContainer();
            json.put(Constants.JSON_EXCEPTION, TypeUtils.toString(mThrowable.getMessage()));
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

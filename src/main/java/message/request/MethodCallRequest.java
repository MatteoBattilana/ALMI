package message.request;

import exceptions.ClassConversionException;
import exceptions.MalformedRequestException;
import exceptions.JSONGenerationException;
import message.BaseMessage;
import message.Message;
import message.MessageType;
import message.response.MethodCallResponse;
import utils.Constants;
import utils.TypeUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MethodCallRequest extends BaseMessage
{
    private final String       mMethodName;
    private final List<String> mMethodParameters;

    private MethodCallRequest(String requestId, String methodName, List<String> params)
    {
        super(MessageType.METHOD_CALL_REQUEST, requestId);
        this.mMethodName = methodName;
        this.mMethodParameters = params;
    }

    public MethodCallRequest(String methodName, List<String> params)
    {
       this(BaseMessage.randomId(), methodName, params);
    }

    public String getMethodName()
    {
        return mMethodName;
    }

    public List<Serializable> getMethodParameters()
      throws MalformedRequestException
    {
        try
        {
            List<Serializable> params = new ArrayList<>();
            for(String p : mMethodParameters)
            {
                params.add(TypeUtils.fromString(p));
            }
            return params;
        }
        catch(ClassConversionException e)
        {
            throw new MalformedRequestException(e);
        }
    }

    @Override
    public JSONObject toJSON()
      throws JSONGenerationException
    {
        JSONObject json = super.toJSON();
        json.put(Constants.JSON_METHOD_NAME, mMethodName);
        json.put(Constants.JSON_PARAMETERS, mMethodParameters);

        return json;
    }

    @Override
    public Message interpret()
    {
        // calculate return value
        return new MethodCallResponse(getId(), 10);
    }

    public static MethodCallRequest parse(JSONObject json)
    {
        String methodName = json.getString(Constants.JSON_METHOD_NAME);
        String requestId = json.getString(Constants.JSON_MESSAGE_ID);
        List<String> params = new ArrayList<>();
        JSONArray jsonParams = json.getJSONArray(Constants.JSON_PARAMETERS);
        for(int i = 0; i < jsonParams.length(); i++)
        {
            try
            {
                params.add(jsonParams.getString(i));
            }
            catch(Exception ignore)
            {
            }
        }

        return new MethodCallRequest(requestId, methodName, params);
    }
}

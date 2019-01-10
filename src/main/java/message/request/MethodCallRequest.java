package message.request;

import exceptions.AlmiException;
import exceptions.BlockingRequestException;
import exceptions.ClassConversionException;
import message.response.MethodCallResponse;
import method.Constants;
import method.TypeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import message.JSONMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MethodCallRequest extends MessageRequest implements JSONMessage
{
    private final String             mMethodName;
    private final List<Serializable> mMethodParameter;

    public MethodCallRequest(String methodName, List<Serializable> params)
    {
        this.mMethodName = methodName;
        this.mMethodParameter = params;
    }

    public String getMethodName()
    {
        return mMethodName;
    }

    public List<Serializable> getMethodParameter()
    {
        return mMethodParameter;
    }

    @Override
    public MessageType getType()
    {
        return MessageType.METHOD_CALL_REQUEST;
    }

    @Override
    public String getJSON()
    {
        try
        {
            StringBuilder json = new StringBuilder("{")
              .append(String.format(" \"%s\" : \"%s\",", Constants.JSON_MESSAGE_ID, getId()))
              .append(String.format(" \"%s\" : \"%s\",", Constants.JSON_MESSAGE_TYPE, getType().toString()))
              .append(String.format(" \"%s\" : \"%s\"", Constants.JSON_METHOD_NAME, mMethodName))
              .append(String.format(" \"%s\" : [", Constants.JSON_PARAMETERS));

            Iterator<Serializable> paramIterator = mMethodParameter.iterator();
            while(paramIterator.hasNext())
            {
                Serializable param = paramIterator.next();
                json.append(TypeUtils.toString(param));
                if(paramIterator.hasNext())
                {
                    json.append(", ");
                }
            }

            return json.append("   ]")
              .append("}").toString();
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
        return new ErrorMessageRequest(new AlmiException("ERrror"));
    }

    public static MethodCallRequest parse(JSONObject json)
    {
        String methodName = json.getString(Constants.JSON_METHOD_NAME);
        List<Serializable> params = new ArrayList<>();
        JSONArray jsonParams = json.getJSONArray(Constants.JSON_PARAMETERS);
        for(int i = 0; i < jsonParams.length(); i++)
        {
            try
            {
                params.add(TypeUtils.fromString(jsonParams.getString(i)));
            }
            catch(Exception ignore)
            {
            }
        }

        return new MethodCallRequest(methodName, params);
    }
}

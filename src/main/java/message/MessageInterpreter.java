package message;

import exceptions.AlmiException;
import exceptions.InvalidRequestException;
import message.request.ErrorMessageRequest;
import method.Constants;
import org.json.JSONException;
import org.json.JSONObject;
import message.request.MethodCallRequest;
import utils.Interpreter;

public class MessageInterpreter
{
    public Interpreter<JSONMessage> parseRequest(String json)
      throws AlmiException
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            JSONMessage.MessageType type = JSONMessage.MessageType.fromString(jsonObject.getString(Constants.JSON_MESSAGE_TYPE));

            switch(type)
            {
                case ERROR:
                    return ErrorMessageRequest.parse(jsonObject);
                case METHOD_CALL_REQUEST:
                    return MethodCallRequest.parse(jsonObject);
                default:
                    throw new InvalidRequestException(json);
            }
        }
        catch(JSONException e)
        {
            throw new InvalidRequestException(json, e);
        }
    }
}

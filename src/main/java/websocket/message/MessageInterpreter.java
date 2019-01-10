package websocket.message;

import exceptions.AlmiException;
import exceptions.InvalidRequestException;
import method.Constants;
import org.json.JSONException;
import org.json.JSONObject;
import websocket.message.request.MethodCallRequest;

public class MessageInterpreter
{
    public JSONMessage parse(String json)
      throws AlmiException
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            JSONMessage.MessageType type = JSONMessage.MessageType.fromString(jsonObject.getString(Constants.JSON_MESSAGE_TYPE));

            switch(type)
            {
                case ERROR:
                    return ErrorMessage.parse(jsonObject);
                case METHOD_CALL:
                    return MethodCallRequest.parse(jsonObject);
                default:
                    throw new UnsupportedOperationException();
            }
        }
        catch(JSONException e)
        {
            throw new InvalidRequestException(json, e);
        }
    }
}

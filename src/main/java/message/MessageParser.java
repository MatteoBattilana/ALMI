package message;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import exceptions.AlmiException;
import exceptions.MalformedRequestException;
import exceptions.MissingParserException;
import message.request.ErrorMessageRequest;
import message.request.MethodCallRequest;
import message.request.HandshakeRequest;
import message.request.StreamRequest;
import message.response.MethodCallResponse;
import message.response.HandshakeResponse;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Constants;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class MessageParser
{
    private Map<String, CheckedFunction<JSONObject, Message>> parserList;

    @FunctionalInterface
    public interface CheckedFunction<T, R>
    {
        R apply(T t)
          throws MalformedRequestException;
    }

    @Inject
    public MessageParser()
    {
        populateParserList();
    }

    private void populateParserList()
    {
        parserList = new HashMap<>();
        parserList.put(Constants.MESSAGE_TYPE_ERROR, ErrorMessageRequest::parse);
        parserList.put(Constants.MESSAGE_TYPE_METHOD_CALL_REQUEST, MethodCallRequest::parse);
        parserList.put(Constants.MESSAGE_TYPE_METHOD_CALL_RESPONSE, MethodCallResponse::parse);
        parserList.put(Constants.MESSAGE_TYPE_HANDSHAKE_REQUEST, HandshakeRequest::parse);
        parserList.put(Constants.MESSAGE_TYPE_HANDSHAKE_RESPONSE, HandshakeResponse::parse);
        parserList.put(Constants.MESSAGE_TYPE_STREAM_REQUEST, StreamRequest::parse);
    }

    public Message parseMessage(String json)
      throws AlmiException
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            CheckedFunction<JSONObject, Message> parser = parserList.get(
              jsonObject.getString(Constants.JSON_MESSAGE_TYPE)
            );

            if(parser != null)
            {
                return parser.apply(jsonObject);
            }
            else
            {
                throw new MissingParserException(jsonObject.getString(Constants.JSON_MESSAGE_TYPE));
            }
        }
        catch(JSONException e)
        {
            throw new MalformedRequestException(json, e);
        }
    }
}

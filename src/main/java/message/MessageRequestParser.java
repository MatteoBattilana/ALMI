package message;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import exceptions.AlmiException;
import exceptions.InvalidRequestException;
import message.request.ErrorMessageRequest;
import message.request.MethodCallRequest;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Constants;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class MessageRequestParser
{
    private Map<String, CheckedFunction<JSONObject, BaseMessage>> parserList;

    @FunctionalInterface
    public interface CheckedFunction<T, R>
    {
        R apply(T t)
          throws InvalidRequestException;
    }

    @Inject
    public MessageRequestParser()
    {
        populateParserList();
    }

    private void populateParserList()
    {
        parserList = new HashMap<>();
        parserList.put(Constants.MESSAGE_TYPE_ERROR, ErrorMessageRequest::parse);
        parserList.put(Constants.MESSAGE_TYPE_MERTHOD_CALL_REQUEST, MethodCallRequest::parse);
    }

    public BaseMessage parseRequest(String json)
      throws AlmiException
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            CheckedFunction<JSONObject, BaseMessage> parser = parserList.get(
              jsonObject.getString(Constants.JSON_MESSAGE_TYPE)
            );

            return parser.apply(jsonObject);
        }
        catch(JSONException e)
        {
            throw new InvalidRequestException(json, e);
        }
    }
}

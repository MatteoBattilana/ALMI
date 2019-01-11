package message;

import exceptions.AlmiException;
import exceptions.BlockingRequestException;
import exceptions.InvalidRequestException;
import message.request.ErrorMessageRequest;
import message.request.MethodCallRequest;
import method.TypeUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageRequestParserTest
{
    private MessageRequestParser mMessageRequestParser;

    @Before
    public void setup()
    {
        mMessageRequestParser = new MessageRequestParser();
    }

    @Test (expected = BlockingRequestException.class)
    public void errorMessage() throws Exception
    {
        String json = "" +
          "{  " +
          "   \"messageId\":\"uuid\"," +
          "   \"messageType\":\"error\"," +
          "   \"exception\":\"" + TypeUtils.toString(new AlmiException("Error message!")) + "\"" +
          "}";

        BaseMessage parsed = mMessageRequestParser.parseRequest(json);
        Assert.assertTrue(parsed instanceof ErrorMessageRequest);
        parsed.generateResponse();
    }

    @Test
    public void methodCallMessage() throws Exception
    {
        String json = "" +
          "{  " +
          "   \"messageId\":\"uuid\"," +
          "   \"messageType\":\"methodCallRequest\"," +
          "   \"methodName\":\"toString\"," +
          "   \"apiVersion\":1," +
          "   \"parameters\":[]" +
          "}";

        BaseMessage parsed = mMessageRequestParser.parseRequest(json);
        Assert.assertTrue(parsed instanceof MethodCallRequest);
        parsed.generateResponse();
    }

    @Test (expected = InvalidRequestException.class)
    public void invalidJson() throws Exception
    {
        String json = "wrong-json" +
          "{  " +
          "   \"messageId\":\"uuid\"," +
          "   \"messageType\":\"methodCallRequest\"," +
          "   \"methodName\":\"toString\"," +
          "   \"apiVersion\":1," +
          "   \"parameters\":[]" +
          "}";

        mMessageRequestParser.parseRequest(json);
    }
}
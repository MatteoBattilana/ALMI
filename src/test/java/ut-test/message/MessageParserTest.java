package message;

import exceptions.AlmiException;
import exceptions.MalformedRequestException;
import message.request.ErrorMessageRequest;
import message.request.MethodCallRequest;
import message.response.MethodCallResponse;
import message.response.StubResponse;
import utils.TypeUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageParserTest
{
    private MessageParser mMessageParser;

    @Before
    public void setup()
    {
        mMessageParser = new MessageParser();
    }

    @Test
    public void errorMessage() throws Exception
    {
        String json = "" +
          "{  " +
          "   \"messageId\":\"uuid\"," +
          "   \"messageType\":\"error\"," +
          "   \"exception\":\"" + TypeUtils.toString(new AlmiException("Error message!")) + "\"" +
          "}";

        Message parsed = mMessageParser.parseMessage(json);
        Assert.assertTrue(parsed instanceof ErrorMessageRequest);
        Message interpret = parsed.generateResponse();
        Assert.assertTrue(interpret instanceof StubResponse);
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

        Message parsed = mMessageParser.parseMessage(json);
        Assert.assertTrue(parsed instanceof MethodCallRequest);
        Message interpret = parsed.generateResponse();
        Assert.assertTrue(interpret instanceof MethodCallResponse);
    }

    @Test (expected = MalformedRequestException.class)
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

        mMessageParser.parseMessage(json);
    }
}
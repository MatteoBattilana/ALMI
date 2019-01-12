package message;

import exceptions.AlmiException;
import exceptions.BlockingRequestException;
import exceptions.MalformedRequestException;
import message.request.ErrorMessageRequest;
import message.request.MethodCallRequest;
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

    @Test (expected = BlockingRequestException.class)
    public void errorMessage() throws Exception
    {
        String json = "" +
          "{  " +
          "   \"messageId\":\"uuid\"," +
          "   \"messageType\":\"error\"," +
          "   \"exception\":\"" + TypeUtils.toString(new AlmiException("Error message!")) + "\"" +
          "}";

        BaseMessage parsed = mMessageParser.parseMessage(json);
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

        BaseMessage parsed = mMessageParser.parseMessage(json);
        Assert.assertTrue(parsed instanceof MethodCallRequest);
        parsed.generateResponse();
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
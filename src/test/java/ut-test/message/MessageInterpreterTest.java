package message;

import exceptions.AlmiException;
import exceptions.BlockingRequestException;
import exceptions.InvalidRequestException;
import message.request.ErrorMessageRequest;
import message.request.MethodCallRequest;
import method.TypeUtils;
import org.junit.Assert;
import org.junit.Test;
import utils.Interpreter;

public class MessageInterpreterTest
{
    @Test (expected = BlockingRequestException.class)
    public void errorMessage() throws Exception
    {
        String json = "" +
          "{  " +
          "   \"messageId\":\"uuid\"," +
          "   \"messageType\":\"error\"," +
          "   \"exception\":\"" + TypeUtils.toString(new AlmiException("Error message!")) + "\"" +
          "}";

        Interpreter<JSONMessage> parsed = new MessageInterpreter().parseRequest(json);
        Assert.assertTrue(parsed instanceof ErrorMessageRequest);
        parsed.interpret();
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

        Interpreter<JSONMessage> parsed = new MessageInterpreter().parseRequest(json);
        Assert.assertTrue(parsed instanceof MethodCallRequest);
        parsed.interpret();
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

        new MessageInterpreter().parseRequest(json);
    }
}
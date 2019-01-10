package message;

import exceptions.AlmiException;
import exceptions.BlockingRequestException;
import message.request.ErrorMessageRequest;
import message.request.MethodCallRequest;
import method.TypeUtils;
import org.junit.Assert;
import org.junit.Test;

public class MessageInterpreterTest
{
    @Test (expected = BlockingRequestException.class)
    public void errorMessage() throws Exception
    {
        String json = "" +
          "{  " +
          "   \"messageId\":\"uuid\"," +
          "   \"messageType\":\"errorResponse\"," +
          "   \"exception\":\"" + TypeUtils.toString(new AlmiException("Error message!")) + "\"," +
          "   \"apiVersion\":1," +
          "   \"parameters\":[]" +
          "}";
        JSONMessage parsed = new MessageInterpreter().parse(json);
        Assert.assertTrue(parsed instanceof ErrorMessageRequest);
        parsed.execute();
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

        JSONMessage parsed = new MessageInterpreter().parse(json);
        Assert.assertTrue(parsed instanceof MethodCallRequest);
        parsed.execute();
    }
}
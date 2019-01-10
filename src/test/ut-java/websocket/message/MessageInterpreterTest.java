package websocket.message;

import exceptions.AlmiException;
import exceptions.BlockingRequestException;
import method.TypeUtils;
import org.junit.Assert;
import org.junit.Test;
import websocket.message.request.MethodCallRequest;

public class MessageInterpreterTest
{
    @Test (expected = BlockingRequestException.class)
    public void errorMessage() throws Exception
    {
        String json = "" +
          "{  " +
          "   \"messageType\":\"error\"," +
          "   \"exception\":\"" + TypeUtils.toString(new AlmiException("Error message!")) + "\"," +
          "   \"apiVersion\":1," +
          "   \"parameters\":[]" +
          "}";
        JSONMessage parsed = new MessageInterpreter().parse(json);
        Assert.assertTrue(parsed instanceof ErrorMessage);
        parsed.execute();
    }

    @Test
    public void methodCallMessage() throws Exception
    {
        String json = "" +
          "{  " +
          "   \"messageType\":\"methodCall\"," +
          "   \"methodName\":\"toString\"," +
          "   \"apiVersion\":1," +
          "   \"parameters\":[]" +
          "}";

        JSONMessage parsed = new MessageInterpreter().parse(json);
        Assert.assertTrue(parsed instanceof MethodCallRequest);
        parsed.execute();
    }
}
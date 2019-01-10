package websocket.message.request;

import method.TypeUtils;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import testUtils.Arithmetic;
import websocket.message.JSONMessage;

public class MethodCallRequestTest
{
    @Test
    public void requestNoParameter() throws Exception
    {
        String json = "" +
          "{  " +
          "   \"messageType\":\"methodCall\"," +
          "   \"methodName\":\"toString\"," +
          "   \"apiVersion\":1," +
          "   \"parameters\":[]" +
          "}";

        MethodCallRequest methodDescriptor = MethodCallRequest.parse(new JSONObject(json));
        Assert.assertEquals(JSONMessage.MessageType.METHOD_CALL, methodDescriptor.getType());
        Assert.assertTrue(methodDescriptor.getMethodParameter().isEmpty());
    }

    @Test
    public void requestTwoParameter() throws Exception
    {
        String json = "" +
          "{  " +
          "   \"messageType\":\"methodCall\"," +
          "   \"methodName\":\"sum\"," +
          "   \"apiVersion\":1," +
          "   \"parameters\":[\"" + TypeUtils.toString(new Arithmetic()) + "\",\"" + TypeUtils.toString(4) + "\"]" +
          "}";

        MethodCallRequest methodDescriptor = MethodCallRequest.parse(new JSONObject(json));
        Assert.assertEquals("sum", methodDescriptor.getMethodName());
        Assert.assertEquals(2, methodDescriptor.getMethodParameter().size());
        Assert.assertTrue(methodDescriptor.getMethodParameter().get(0) instanceof Arithmetic);
        Assert.assertTrue(methodDescriptor.getMethodParameter().get(1) instanceof Integer);
    }
}
package message.request;

import message.JSONMessage;
import method.TypeUtils;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import testUtils.Arithmetic;

public class MethodCallRequestTest
{
    @Test
    public void requestNoParameter()
    {
        String json = "" +
          "{  " +
          "   \"messageId\":\"uuid\"," +
          "   \"messageType\":\"methodCallRequest\"," +
          "   \"methodName\":\"toString\"," +
          "   \"apiVersion\":1," +
          "   \"parameters\":[]" +
          "}";

        MethodCallRequest methodDescriptor = MethodCallRequest.parse(new JSONObject(json));
        Assert.assertEquals(JSONMessage.MessageType.METHOD_CALL_REQUEST, methodDescriptor.getType());
        Assert.assertTrue(methodDescriptor.getMethodParameter().isEmpty());
    }

    @Test
    public void requestTwoParameter() throws Exception
    {
        String json = "" +
          "{  " +
          "   \"messageId\":\"uuid\"," +
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
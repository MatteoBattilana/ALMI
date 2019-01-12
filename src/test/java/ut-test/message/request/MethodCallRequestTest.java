package message.request;

import message.MessageType;
import utils.TypeUtils;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import testUtils.Arithmetic;

public class MethodCallRequestTest
{
    @Test
    public void requestNoParameter() throws Exception
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
        Assert.assertEquals(MessageType.METHOD_CALL_REQUEST, methodDescriptor.getType());
        Assert.assertTrue(methodDescriptor.getMethodParameters().isEmpty());
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
        Assert.assertEquals(2, methodDescriptor.getMethodParameters().size());
        Assert.assertTrue(methodDescriptor.getMethodParameters().get(0) instanceof Arithmetic);
        Assert.assertTrue(methodDescriptor.getMethodParameters().get(1) instanceof Integer);
    }
}
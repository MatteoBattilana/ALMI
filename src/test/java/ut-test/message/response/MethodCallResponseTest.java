package message.response;

import message.MessageType;
import utils.TypeUtils;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import testUtils.Arithmetic;
import testUtils.Person;

public class MethodCallResponseTest
{
    @Test (expected = ClassCastException.class)
    public void requestNoParameter() throws Exception
    {
        Person person = new Person("Matteo", 23);
        String json = "" +
          "{  " +
          "   \"messageId\":\"uuid\"," +
          "   \"messageType\":\"methodCallResponse\"," +
          "   \"returnValue\":\"" + TypeUtils.toString(person) + "\"" +
          "}";

        MethodCallResponse methodDescriptor = MethodCallResponse.parse(new JSONObject(json));
        Assert.assertEquals(MessageType.METHOD_CALL_RESPONSE, methodDescriptor.getType());
        Assert.assertTrue(methodDescriptor.getReturnValue() instanceof Person);

        Person p = methodDescriptor.getReturnValue();
        Assert.assertEquals("Matteo", p.getName());
        Assert.assertEquals(23, p.getAge());

        Arithmetic wrong = methodDescriptor.getReturnValue();
    }
}
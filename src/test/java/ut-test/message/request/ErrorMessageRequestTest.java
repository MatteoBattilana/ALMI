package message.request;

import exceptions.AlmiException;
import message.BaseMessage;
import message.MessageType;
import message.response.StubResponse;
import utils.TypeUtils;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class ErrorMessageRequestTest
{
    @Test
    public void requestNoParameter() throws Exception
    {
        String json = "" +
          "{  " +
          "   \"messageType\":\"error\"," +
          "   \"messageId\":\"uuid\"," +
          "   \"exception\":\"" + TypeUtils.toString(new AlmiException("Error message!")) + "\"" +
          "}";

        ErrorMessageRequest message = ErrorMessageRequest.parse(new JSONObject(json));
        Assert.assertEquals(MessageType.ERROR, message.getType());
        Assert.assertTrue(message.getThrowable() instanceof AlmiException);
        Assert.assertEquals("Error message!", message.getThrowable().getMessage());

        BaseMessage stub = message.interpret();
        Assert.assertTrue(stub instanceof StubResponse);
    }
}
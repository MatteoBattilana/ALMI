package message.response;

import exceptions.AlmiException;
import message.JSONMessage;
import method.TypeUtils;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class ErrorMessageResponseTest
{
    @Test
    public void requestNoParameter() throws Exception
    {
        String json = "" +
          "{  " +
          "   \"messageType\":\"error\"," +
          "   \"messageId\":\"uuid\"," +
          "   \"exception\":\"" + TypeUtils.toString(new AlmiException("Error message!")) + "\"," +
          "}";

        ErrorMessageResponse message = ErrorMessageResponse.parse(new JSONObject(json));
        Assert.assertEquals(JSONMessage.MessageType.ERROR, message.getType());
        Assert.assertTrue(message.getThrowable() instanceof AlmiException);
        Assert.assertEquals("Error message!", message.getThrowable().getMessage());
    }
}
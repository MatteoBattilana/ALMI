package message.response;

import exceptions.AlmiException;
import message.MessageType;
import utils.TypeUtils;
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
        Assert.assertEquals(MessageType.ERROR, message.getType());
        Assert.assertTrue(message.getThrowable() instanceof AlmiException);
        Assert.assertEquals("Error message!", message.getThrowable().getMessage());
    }
}
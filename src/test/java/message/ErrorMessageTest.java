package message;

import exceptions.AlmiException;
import exceptions.BlockingRequestException;
import method.TypeUtils;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class ErrorMessageTest
{
    @Test (expected = BlockingRequestException.class)
    public void requestNoParameter() throws Exception
    {
        String json = "" +
          "{  " +
          "   \"messageType\":\"error\"," +
          "   \"exception\":\"" + TypeUtils.toString(new AlmiException("Error message!")) + "\"," +
          "   \"apiVersion\":1," +
          "   \"parameters\":[]" +
          "}";

        ErrorMessage message = ErrorMessage.parse(new JSONObject(json));
        Assert.assertEquals(JSONMessage.MessageType.ERROR, message.getType());
        Assert.assertTrue(message.getThrowable() instanceof AlmiException);
        Assert.assertEquals("Error message!", message.getThrowable().getMessage());

        message.execute();
    }
}
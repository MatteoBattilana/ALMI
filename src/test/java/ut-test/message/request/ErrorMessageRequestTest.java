package message.request;

import exceptions.AlmiException;
import exceptions.BlockingRequestException;
import message.JSONMessage;
import method.TypeUtils;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class ErrorMessageRequestTest
{
    @Test (expected = BlockingRequestException.class)
    public void requestNoParameter() throws Exception
    {
        String json = "" +
          "{  " +
          "   \"messageType\":\"errorResponse\"," +
          "   \"exception\":\"" + TypeUtils.toString(new AlmiException("Error message!")) + "\"," +
          "   \"apiVersion\":1," +
          "   \"parameters\":[]" +
          "}";

        ErrorMessageRequest message = ErrorMessageRequest.parse(new JSONObject(json));
        Assert.assertEquals(JSONMessage.MessageType.ERROR_RESPONSE, message.getType());
        Assert.assertTrue(message.getThrowable() instanceof AlmiException);
        Assert.assertEquals("Error message!", message.getThrowable().getMessage());

        message.execute();
    }
}
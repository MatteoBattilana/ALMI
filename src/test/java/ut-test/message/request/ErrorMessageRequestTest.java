package message.request;

import exceptions.AlmiException;
import exceptions.BlockingRequestException;
import message.MessageType;
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
          "   \"messageType\":\"error\"," +
          "   \"messageId\":\"uuid\"," +
          "   \"exception\":\"" + TypeUtils.toString(new AlmiException("Error message!")) + "\"" +
          "}";

        ErrorMessageRequest message = ErrorMessageRequest.parse(new JSONObject(json));
        Assert.assertEquals(MessageType.ERROR, message.getType());
        Assert.assertTrue(message.getThrowable() instanceof AlmiException);
        Assert.assertEquals("Error message!", message.getThrowable().getMessage());

        message.generateResponse();
    }
}
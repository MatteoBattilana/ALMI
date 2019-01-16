package socket;

import exceptions.AlmiException;
import io.netty.channel.embedded.EmbeddedChannel;
import message.Message;
import message.MessageParser;
import message.request.ErrorMessageRequest;
import message.response.StubResponse;
import org.junit.Assert;
import org.junit.Test;
import socket.handler.MessageDecoder;
import socket.handler.MessageEncoder;

public class MessageEncoderTest
{
    @Test
    public void encodeMessage()
    {
        EmbeddedChannel channel = new EmbeddedChannel(new MessageEncoder(), new MessageDecoder(new MessageParser()));

        Message error = new ErrorMessageRequest(new AlmiException("Test!"));
        channel.writeInbound(error);

        Message returnedMessage = channel.readInbound();
        Assert.assertTrue(returnedMessage instanceof ErrorMessageRequest);
        Message interpret = returnedMessage.generateResponse();
        Assert.assertTrue(interpret instanceof StubResponse);
    }

}
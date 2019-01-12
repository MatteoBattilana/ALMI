package socket;

import exceptions.AlmiException;
import exceptions.BlockingRequestException;
import io.netty.channel.embedded.EmbeddedChannel;
import message.BaseMessage;
import message.MessageParser;
import message.request.ErrorMessageRequest;
import org.junit.Assert;
import org.junit.Test;
import socket.handler.MessageDecoder;
import socket.handler.MessageEncoder;

import static org.junit.Assert.fail;

public class MessageEncoderTest
{
    @Test
    public void encodeMessage()
    {
        EmbeddedChannel channel = new EmbeddedChannel(new MessageEncoder(), new MessageDecoder(new MessageParser()));

        BaseMessage error = new ErrorMessageRequest(new AlmiException("Test!"));
        channel.writeInbound(error);

        BaseMessage returnedMessage = channel.readInbound();
        try
        {
            returnedMessage.generateResponse();
            fail("BlockingRequestException not thrown!");
        }
        catch(BlockingRequestException ignored)
        {
        }
        Assert.assertTrue(returnedMessage instanceof ErrorMessageRequest);
    }

}
package message;

public abstract class StreamMessage extends Message
{
    public StreamMessage(MessageType messageType, String id)
    {
        super(messageType, id);
    }
}

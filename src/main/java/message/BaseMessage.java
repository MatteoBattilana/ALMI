package message;

public abstract class BaseMessage extends Message
{
    public BaseMessage(MessageType messageType, String id)
    {
        super(messageType, id);
    }
}

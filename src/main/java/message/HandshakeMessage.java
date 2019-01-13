package message;

public abstract class HandshakeMessage extends Message
{
    public HandshakeMessage(MessageType messageType, String id)
    {
        super(messageType, id);
    }
}

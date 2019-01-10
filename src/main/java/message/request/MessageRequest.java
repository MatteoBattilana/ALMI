package message.request;

import java.util.UUID;

public class MessageRequest
{
    private final String mId;

    public MessageRequest()
    {
        mId = UUID.randomUUID().toString();
    }

    public String getId()
    {
        return mId;
    }
}

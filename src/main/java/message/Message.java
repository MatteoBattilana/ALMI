package message;

import java.io.Serializable;
import java.util.UUID;

public abstract class Message implements Serializable
{
    private final String mId;

    public Message(String id)
    {
        mId = id;
    }

    protected String getId()
    {
        return mId;
    }

    public static String randomId()
    {
        return UUID.randomUUID().toString();
    }
}

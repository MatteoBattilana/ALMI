package message;

import java.util.concurrent.atomic.AtomicLong;

public class MessageUtils
{
    static AtomicLong idCounter = new AtomicLong();

    public static String createID()
    {
        return String.valueOf(idCounter.getAndIncrement());
    }
}

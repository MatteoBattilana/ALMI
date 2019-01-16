package exceptions;

import java.io.Serializable;

public class AlmiException extends Exception implements Serializable
{
    public AlmiException(String message)
    {
        super(message);
    }

    public AlmiException(Throwable throwable)
    {
        super(throwable);
    }

    public AlmiException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}

package exceptions;

public class AlmiException extends Exception
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

package exceptions;

public class InvisibleWrapperException extends RuntimeException
{
    public InvisibleWrapperException(Throwable e)
    {
        super(e);
    }
}

package exceptions;

public class MissingKeyException extends RuntimeException
{
    public MissingKeyException(String key)
    {
        super(getErrorMessage(key));
    }

    private static String getErrorMessage(String key)
    {
        return String.format("Object not found for key: %s", key);
    }
}

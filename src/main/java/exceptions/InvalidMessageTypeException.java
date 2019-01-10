package exceptions;

public class InvalidMessageTypeException extends AlmiException
{
    public InvalidMessageTypeException(String name)
    {
        super(getErrorMessage(name));
    }

    private static String getErrorMessage(String name)
    {
        return String.format("Message type not valid: %s", name);
    }
}

package exceptions;

public class MissingPropertyException extends AlmiException
{
    public MissingPropertyException(String name)
    {
        super(getErrorMessage(name));
    }

    private static String getErrorMessage(String name)
    {
        return String.format("Missing property %s!", name);
    }
}

package exceptions;

public class MissingMethodException extends AlmiException
{
    public MissingMethodException(String name)
    {
        super(getErrorMessage(name));
    }

    private static String getErrorMessage(String name)
    {
        return String.format("%s missing method!", name);
    }
}

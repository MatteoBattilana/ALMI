package exceptions;

public class MissingMethodException extends AlmiException
{
    public MissingMethodException(String name)
    {
        super(getErrorMessage(name));
    }

    public MissingMethodException(String name, Throwable throwable)
    {
        super(getErrorMessage(name), throwable);
    }

    private static String getErrorMessage(String name)
    {
        return String.format("%s missing method!", name);
    }
}

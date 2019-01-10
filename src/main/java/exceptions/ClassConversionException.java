package exceptions;

public class ClassConversionException extends AlmiException
{
    public ClassConversionException(String name)
    {
        super(getErrorMessage(name));
    }

    public ClassConversionException(Throwable throwable)
    {
        super(throwable);
    }

    public ClassConversionException(String name, Throwable throwable)
    {
        super(getErrorMessage(name), throwable);
    }

    private static String getErrorMessage(String name)
    {
        return String.format("Class not found: %s!", name);
    }
}

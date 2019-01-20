package exceptions;

public class InvalidPropertiesFileException extends AlmiException
{
    public InvalidPropertiesFileException(String name)
    {
        super(getErrorMessage(name));
    }

    public InvalidPropertiesFileException(String name, Throwable e)
    {
        super(getErrorMessage(name), e);
    }

    private static String getErrorMessage(String name)
    {
        return String.format("Invalid properties file %s!", name);
    }
}

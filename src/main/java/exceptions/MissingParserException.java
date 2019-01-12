package exceptions;

public class MissingParserException extends AlmiException
{
    public MissingParserException(String type)
    {
        super(getErrorMessage(type));
    }

    private static String getErrorMessage(String type)
    {
        return String.format("Missing parser for type: %s", type);
    }
}

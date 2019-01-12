package exceptions;

public class JSONParsingException extends AlmiException
{
    public JSONParsingException(String json, Throwable throwable)
    {
        super(getErrorMessage(json), throwable);
    }

    private static String getErrorMessage(String json)
    {
        return String.format("Error while parsing JSON!\n%s", json);
    }
}

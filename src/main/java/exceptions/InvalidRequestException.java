package exceptions;

public class InvalidRequestException extends AlmiException
{
    public InvalidRequestException(String json)
    {
        super(getErrorMessage(json));
    }

    public InvalidRequestException(String json, Throwable throwable)
    {
        super(getErrorMessage(json), throwable);
    }

    private static String getErrorMessage(String json)
    {
        return String.format("Invalid JSON Request for:\n%s\n", json);
    }
}

package exceptions;

public class MalformedRequestException extends AlmiException
{
    public MalformedRequestException(String json)
    {
        super(getErrorMessage(json));
    }

    public MalformedRequestException(Throwable throwable)
    {
        super(throwable);
    }

    public MalformedRequestException(String json, Throwable throwable)
    {
        super(getErrorMessage(json), throwable);
    }

    private static String getErrorMessage(String json)
    {
        return String.format("Invalid JSON Request for:\n%s\n", json);
    }
}

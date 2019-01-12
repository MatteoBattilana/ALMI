package exceptions;

public class JsonGenerationException extends AlmiException
{
    public JsonGenerationException(Throwable throwable)
    {
        super(getErrorMessage(), throwable);
    }

    private static String getErrorMessage()
    {
        return "Error while generating JSON!";
    }
}

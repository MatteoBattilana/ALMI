package exceptions;

public class JSONGenerationException extends AlmiException
{
    public JSONGenerationException(Throwable throwable)
    {
        super(getErrorMessage(), throwable);
    }

    private static String getErrorMessage()
    {
        return "Error while generating JSON!";
    }
}

package exceptions;

public class WrongParametersException extends AlmiException
{
    public WrongParametersException(int expected, int received, Throwable throwable)
    {
        super(getErrorMessage(expected, received), throwable);
    }

    public WrongParametersException(int expected, int received)
    {
        super(getErrorMessage(expected, received));
    }

    private static String getErrorMessage(int expected, int received)
    {
        return String.format("Wrong parameters number! Requested %s but received %d", expected, received);
    }
}

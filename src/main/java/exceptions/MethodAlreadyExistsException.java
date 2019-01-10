package exceptions;

import method.MethodHandler;

public class MethodAlreadyExistsException extends AlmiException
{
    public MethodAlreadyExistsException(MethodHandler methodHandler)
    {
        super(getErrorMessage(methodHandler));
    }

    private static String getErrorMessage(MethodHandler methodHandler)
    {
        return String.format("%s, already exists!", methodHandler.toString());
    }
}

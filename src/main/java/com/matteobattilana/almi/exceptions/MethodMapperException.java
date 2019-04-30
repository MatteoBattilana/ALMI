package com.matteobattilana.almi.exceptions;

public class MethodMapperException extends AlmiException
{
    public MethodMapperException(Throwable e)
    {
        super(getErrorMessage(), e);
    }

    private static String getErrorMessage()
    {
        return String.format("Invalid RemoteMethodMapper configuration!");
    }
}

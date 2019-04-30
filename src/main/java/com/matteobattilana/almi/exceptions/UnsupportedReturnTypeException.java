package com.matteobattilana.almi.exceptions;

public class UnsupportedReturnTypeException extends AlmiException
{
    public UnsupportedReturnTypeException(Class<?> type)
    {
        super(getErrorMessage(type));
    }

    private static String getErrorMessage(Class<?> typee)
    {
        return String.format("Unsupported %s return type!", typee.getSimpleName());
    }
}

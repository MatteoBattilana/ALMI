package com.matteobattilana.almi.exceptions;

public class MissingMethodException extends AlmiException
{
    public MissingMethodException(String name)
    {
        super(getErrorMessage(name));
    }

    private static String getErrorMessage(String name)
    {
        return String.format("%s missing com.matteobattilana.almi.method!", name);
    }
}

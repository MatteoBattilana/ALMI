package com.matteobattilana.almi.exceptions;

public class InvalidPropertyException extends AlmiException
{
    public InvalidPropertyException(String name)
    {
        super(getErrorMessage(name));
    }

    private static String getErrorMessage(String name)
    {
        return String.format("Invalid property %s!", name);
    }
}

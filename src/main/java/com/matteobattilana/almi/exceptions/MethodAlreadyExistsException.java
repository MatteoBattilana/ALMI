package com.matteobattilana.almi.exceptions;

import com.matteobattilana.almi.method.MethodDescriptor;

public class MethodAlreadyExistsException extends AlmiException
{
    public MethodAlreadyExistsException(MethodDescriptor methodDescriptor)
    {
        super(getErrorMessage(methodDescriptor));
    }

    private static String getErrorMessage(MethodDescriptor methodDescriptor)
    {
        return String.format("%s, already exists!", methodDescriptor.toString());
    }
}

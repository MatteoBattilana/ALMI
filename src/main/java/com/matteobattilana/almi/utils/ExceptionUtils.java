package com.matteobattilana.almi.utils;

import com.matteobattilana.almi.exceptions.InvisibleWrapperException;

public class ExceptionUtils
{
    public static Throwable unwrap(InvisibleWrapperException wrapper)
    {
        return wrapper.getCause();
    }

    public static InvisibleWrapperException wrap(Throwable throwable)
    {
        return new InvisibleWrapperException(throwable);
    }
}

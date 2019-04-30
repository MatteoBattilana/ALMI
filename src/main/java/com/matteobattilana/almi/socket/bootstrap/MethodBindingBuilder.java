package com.matteobattilana.almi.socket.bootstrap;

import java.lang.reflect.Method;

public class MethodBindingBuilder
{
    private final Object mInstance;

    public MethodBindingBuilder(Object instance)
    {
        mInstance = instance;
    }

    public NameBindingBuilder method(String methodName, Class<?>... params)
      throws NoSuchMethodException
    {
        if(mInstance instanceof Class<?>)
        {
            return method(((Class) mInstance).getMethod(methodName, params));
        }
        return method(mInstance.getClass().getMethod(methodName, params));
    }

    public NameBindingBuilder method(Method method)
    {
        return new NameBindingBuilder(mInstance, method);
    }
}

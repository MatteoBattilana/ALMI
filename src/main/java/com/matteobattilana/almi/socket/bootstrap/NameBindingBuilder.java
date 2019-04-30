package com.matteobattilana.almi.socket.bootstrap;

import com.matteobattilana.almi.exceptions.AlmiException;
import com.matteobattilana.almi.method.MethodDescriptor;

import java.lang.reflect.Method;

public class NameBindingBuilder
{
    private final Object               mInstance;
    private final Method               mMethod;

    public NameBindingBuilder(
      Object instance,
      Method method
    )
    {
        mInstance = instance;
        mMethod = method;
    }

    public MethodDescriptor withDefaultName()
      throws AlmiException
    {
        return withName(mMethod.getName());
    }

    public MethodDescriptor withName(String name)
      throws AlmiException
    {
        return new MethodDescriptor(mInstance, mMethod, name);
    }
}

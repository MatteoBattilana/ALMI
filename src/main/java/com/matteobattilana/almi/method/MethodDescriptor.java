package com.matteobattilana.almi.method;

import com.matteobattilana.almi.exceptions.UnsupportedReturnTypeException;

import java.io.Serializable;
import java.lang.reflect.Method;

public class MethodDescriptor
{
    private final Object mInstance;
    private final Method mMethod;
    private final String mRemoteName;

    public MethodDescriptor(Object instance, Method method, String remoteName)
      throws UnsupportedReturnTypeException
    {
        assertSerializable(method.getReturnType());

        mInstance = instance;
        mMethod = method;
        mRemoteName = remoteName;
    }

    private void assertSerializable(Class<?> returnType)
      throws UnsupportedReturnTypeException
    {
        if(!returnType.isPrimitive() && !Serializable.class.isAssignableFrom(returnType))
        {
            throw new UnsupportedReturnTypeException(returnType);
        }
    }

    public String getRemoteName()
    {
        return mRemoteName;
    }

    public Method getMethod()
    {
        return mMethod;
    }

    public Object getInstance()
    {
        return mInstance;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(o == null || getClass() != o.getClass())
        {
            return false;
        }
        MethodDescriptor that = (MethodDescriptor) o;
        return mRemoteName.equals(that.getRemoteName());
    }

    @Override
    public int hashCode()
    {
        return mRemoteName.hashCode();
    }

    @Override
    public String toString()
    {
        return String.format("[%s.%s() with remote name : %s]", mInstance.getClass(), mMethod.getName(), mRemoteName);
    }
}

package com.matteobattilana.almi.socket.bootstrap;

import com.matteobattilana.almi.exceptions.AlmiException;
import com.matteobattilana.almi.exceptions.MethodAlreadyExistsException;
import com.matteobattilana.almi.method.MethodDescriptor;

import java.util.HashMap;
import java.util.Map;

public abstract class MethodsMapper
{
    private Map<String, MethodDescriptor> mMethodDescriptors = new HashMap<>();

    public abstract void configure()
      throws Exception;

    protected MethodBindingBuilder bind(Object instance)
    {
        return new MethodBindingBuilder(instance);
    }
    protected MethodBindingBuilder bindStatic(Class<?> clazz)
    {
        return bind(clazz);
    }

    public void addMethods(MethodDescriptor ... methodsDescriptor)
      throws AlmiException
    {
        for(MethodDescriptor method : methodsDescriptor)
        {
            if(!mMethodDescriptors.containsKey(method.getRemoteName()))
            {
                mMethodDescriptors.put(method.getRemoteName(), method);
            }
            else
            {
                throw new MethodAlreadyExistsException(method);
            }
        }
    }

    public Map<String, MethodDescriptor> methodDescriptorMap()
    {
        return mMethodDescriptors;
    }
}

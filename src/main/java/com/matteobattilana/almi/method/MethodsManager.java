package com.matteobattilana.almi.method;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.matteobattilana.almi.exceptions.MissingMethodException;
import com.matteobattilana.almi.exceptions.WrongParametersException;

import java.io.Serializable;
import java.util.*;

@Singleton
public class MethodsManager
{
    private final Map<String, MethodDescriptor> mMethodsMap;

    @Inject
    public MethodsManager()
    {
        mMethodsMap = new HashMap<>();
    }

    public void addAll(Map<String, MethodDescriptor> methodDescriptors)
    {
        mMethodsMap.putAll(methodDescriptors);
    }

    public Serializable execute(String methodName, List<Serializable> params)
      throws Exception
    {
        MethodDescriptor method = mMethodsMap.get(methodName);
        if(method != null)
        {
            if(method.getMethod().getParameterCount() == params.size())
            {
                return (Serializable) method.getMethod().invoke(
                  method.getInstance(),
                  params.toArray(new Object[0])
                );
            }
            throw new WrongParametersException(method.getMethod().getParameterCount(), params.size());
        }
        throw new MissingMethodException(methodName);
    }
}

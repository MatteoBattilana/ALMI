package method;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import exceptions.AlmiException;
import exceptions.MethodAlreadyExistsException;
import exceptions.MissingMethodException;
import exceptions.UnsupportedReturnTypeException;
import exceptions.WrongParametersException;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    public void addMethod(Object instance, Method method, String remoteName)
      throws MethodAlreadyExistsException, UnsupportedReturnTypeException
    {
        addMethod(new MethodDescriptor(instance, method, remoteName));
    }

    public Serializable execute(String methodName, List<Serializable> params)
      throws AlmiException
    {
        MethodDescriptor method = mMethodsMap.get(methodName);
        if(method != null)
        {
            if(method.getMethod().getParameterCount() == params.size())
            {
                try
                {
                    return (Serializable) method.getMethod().invoke(method.getInstance(), params.toArray(new Object[0]));
                }
                catch(IllegalAccessException | InvocationTargetException e)
                {
                    throw new AlmiException(e);
                }
            }
            throw new WrongParametersException(method.getMethod().getParameterCount(), params.size());
        }
        throw new MissingMethodException(methodName);
    }

    private void addMethod(MethodDescriptor methodDescriptor)
      throws MethodAlreadyExistsException
    {
        if(mMethodsMap.containsKey(methodDescriptor.getRemoteName()))
        {
            throw new MethodAlreadyExistsException(methodDescriptor);
        }
        mMethodsMap.put(methodDescriptor.getRemoteName(), methodDescriptor);
    }
}

package method;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import exceptions.AlmiException;
import exceptions.MethodAlreadyExistsException;
import exceptions.MissingMethodException;
import exceptions.WrongParametersException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Singleton
public class MethodsManager
{
    private final Map<String, MethodHandler> mMethodsMap;

    @Inject
    public MethodsManager()
    {
        mMethodsMap = new HashMap<>();
    }

    private void addMethod(MethodHandler methodHandler)
      throws MethodAlreadyExistsException
    {
        if(mMethodsMap.containsKey(methodHandler.getRemoteName()))
        {
            throw new MethodAlreadyExistsException(methodHandler);
        }
        mMethodsMap.put(methodHandler.getRemoteName(), methodHandler);
    }

    public void addMethod(Object instance, Method method, String remoteName)
      throws MethodAlreadyExistsException
    {
        addMethod(new MethodHandler(instance, method, remoteName));
    }

    public Object execute(String methodName, Object... param)
      throws AlmiException
    {
        MethodHandler method = mMethodsMap.get(methodName);
        if(method != null)
        {
            if(method.getMethod().getParameterCount() == param.length)
            {
                try
                {
                    return method.getMethod().invoke(method.getInstance(), param);
                }
                catch(IllegalAccessException | InvocationTargetException e)
                {
                    throw new AlmiException(e);
                }
            }
            throw new WrongParametersException(method.getMethod().getParameterCount(), param.length);
        }
        throw new MissingMethodException(methodName);
    }
}

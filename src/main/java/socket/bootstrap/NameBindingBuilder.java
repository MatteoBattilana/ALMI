package socket.bootstrap;

import exceptions.AlmiException;
import method.MethodDescriptor;

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

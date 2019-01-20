package socket.bootstrap;

import exceptions.AlmiException;
import exceptions.MethodAlreadyExistsException;
import method.MethodDescriptor;

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

    public void install(MethodDescriptor ... methodsDescriptor)
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

package method;

import java.io.Serializable;
import java.lang.reflect.Method;

public class MethodDescriptor
{
    private final Serializable mInstance;
    private final Method       mMethod;
    private final String       mRemoteName;

    public MethodDescriptor(Serializable instance, Method method, String remoteName)
    {
        mInstance = instance;
        mMethod = method;
        mRemoteName = remoteName;
    }

    public String getRemoteName()
    {
        return mRemoteName;
    }

    public Method getMethod()
    {
        return mMethod;
    }

    public Serializable getInstance()
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
        return String.format("[%s() with remote name : %s]", mMethod.getName(), mRemoteName);
    }
}

package method;

import java.lang.reflect.Method;

public class MethodHandler
{
    private final Object mInstance;
    private final Method mMethod;
    private final String mRemoteName;

    public MethodHandler(Object instance, Method method, String remoteName)
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
        MethodHandler that = (MethodHandler) o;
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

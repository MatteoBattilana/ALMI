package exceptions;

import java.net.InetSocketAddress;

public class ResultException extends AlmiException
{
    public ResultException(String methodName, InetSocketAddress address, Throwable e)
    {
        super(getErrorMessage(methodName, address), e);
    }

    public ResultException(String methodName, InetSocketAddress address)
    {
        super(getErrorMessage(methodName, address));
    }

    private static String getErrorMessage(String methodName, InetSocketAddress address)
    {
        return String.format("Cannot get result for %s method call, from host %s!", methodName, address.toString());
    }
}

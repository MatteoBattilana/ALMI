package clientinformation;

import exceptions.InvisibleWrapperException;
import socket.Almi;
import utils.ExceptionUtils;

import java.util.Collections;

public class RemoteMethodMapper implements Methods
{
    private final Almi   mAlmi;
    private final String mAddress;
    private final int    mPort;

    public RemoteMethodMapper(Almi almi, String address, int port)
    {
        mAlmi = almi;
        mAddress = address;
        mPort = port;
    }

    @Override
    public String getInformation()
    {
        return mAlmi.callMethod(mAddress, mPort, "getInformation", Collections.emptyList());
    }

    @Override
    public String getIpAddress() throws NoInternetConnectionException
    {
        try
        {
            return mAlmi.callMethod(mAddress, mPort, "getIpAddress", Collections.emptyList());
        }
        catch(InvisibleWrapperException wrapper)
        {
            Throwable realException = ExceptionUtils.unwrap(wrapper);
            if(realException instanceof NoInternetConnectionException)
            {
                throw (NoInternetConnectionException)realException;
            }
            throw wrapper;
        }
    }
}

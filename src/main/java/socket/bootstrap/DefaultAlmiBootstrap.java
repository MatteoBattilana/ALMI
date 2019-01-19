package socket.bootstrap;

import com.google.inject.Inject;
import socket.Almi;
import socket.AlmiFactory;
import utils.Constants;
import utils.PropertiesUtils;

import java.util.Properties;

public class DefaultAlmiBootstrap implements AlmiBootstrap
{
    private final AlmiFactory mAlmiFactory;

    private String mThreadName     = Constants.SOCKET_SERVICE_THREAD_NAME;
    private String mSocketAddress  = Constants.SOCKET_ADDRESS;
    private int    mPort           = Constants.SOCKET_PORT;
    private int    mConnectTimeout = Constants.SOCKET_TIMEOUT;

    @Inject
    public DefaultAlmiBootstrap(AlmiFactory almiFactory)
    {
        mAlmiFactory = almiFactory;
    }

    @Override
    public AlmiBootstrap from(Properties props)
    {
        withThreadName(PropertiesUtils.optString(props, Constants.PROPERTY_THREAD_NAME, Constants.SOCKET_SERVICE_THREAD_NAME));
        withAddress(PropertiesUtils.optString(props, Constants.PROPERTY_ADDRESS, Constants.SOCKET_ADDRESS));
        withPort(PropertiesUtils.optInt(props, Constants.PROPERTY_PORT, Constants.SOCKET_PORT));
        withConnectionTimeout(PropertiesUtils.optInt(props, Constants.PROPERTY_CONNECTION_TIMEOUT, Constants.SOCKET_TIMEOUT));
        return this;
    }

    @Override
    public AlmiBootstrap withThreadName(String name)
    {
        mThreadName = name;
        return this;
    }

    @Override
    public AlmiBootstrap withAddress(String address)
    {
        mSocketAddress = address;
        return this;
    }

    @Override
    public AlmiBootstrap withPort(int port)
    {
        mPort = port;
        return this;
    }

    @Override
    public AlmiBootstrap withConnectionTimeout(int timeout)
    {
        mConnectTimeout = timeout;
        return this;
    }

    @Override
    public Almi start() {
        return build().start();
    }

    private Almi build() {
        return mAlmiFactory.create(
          mThreadName,
          mSocketAddress,
          mPort,
          mConnectTimeout
        );
    }
}

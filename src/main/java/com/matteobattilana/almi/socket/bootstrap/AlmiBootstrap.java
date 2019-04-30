package com.matteobattilana.almi.socket.bootstrap;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.matteobattilana.almi.exceptions.AlmiException;
import com.matteobattilana.almi.exceptions.InvalidPropertiesFileException;
import com.matteobattilana.almi.exceptions.MethodMapperException;
import com.matteobattilana.almi.guice.AlmiModules;
import com.matteobattilana.almi.method.MethodDescriptor;
import com.matteobattilana.almi.socket.Almi;
import com.matteobattilana.almi.socket.AlmiFactory;
import com.matteobattilana.almi.utils.Constants;
import com.matteobattilana.almi.utils.PropertiesUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AlmiBootstrap implements Bootstrap
{
    private final AlmiFactory mAlmiFactory;

    private Map<String, MethodDescriptor> mMethodDescriptorMap = new HashMap<>();
    private String                        mThreadName          = Constants.DEFAULT_THREAD_NAME;
    private int                           mPort                = Constants.DEFAULT_PORT;
    private int                           mConnectTimeout      = Constants.DEFAULT_CONNECTION_TIMEOUT;
    private int                           mPromiseTimeout      = Constants.DEFAULT_PROMISE_TIMEOUT;

    public static AlmiBootstrap bootstrap()
    {
        Injector injector = Guice.createInjector(new AlmiModules());

        return new AlmiBootstrap(
          injector.getInstance(AlmiFactory.class)
        );
    }

    private AlmiBootstrap(AlmiFactory almiFactory)
    {
        mAlmiFactory = almiFactory;
    }

    @Override
    public Bootstrap from(Properties props) throws AlmiException
    {
        withThreadName(PropertiesUtils.optString(props, Constants.PROPERTY_THREAD_NAME, Constants.DEFAULT_THREAD_NAME));
        withPort(PropertiesUtils.optInt(props, Constants.PROPERTY_PORT, Constants.DEFAULT_PORT));
        withConnectionTimeout(PropertiesUtils.optInt(props, Constants.PROPERTY_CONNECTION_TIMEOUT, Constants.DEFAULT_CONNECTION_TIMEOUT));
        withRemoteCallTimeout(PropertiesUtils.optInt(props, Constants.PROPERTY_PROMISE_TIMEOUT, Constants.DEFAULT_PROMISE_TIMEOUT));
        return this;
    }

    @Override
    public Bootstrap fromPropertiesFile(String path)
      throws AlmiException
    {
        File propertiesFile = new File(path);
        Properties props = new Properties();

        try(InputStream is = new FileInputStream(propertiesFile))
        {
            props.load(is);
            return from(props);
        }
        catch(IOException e)
        {
            throw new InvalidPropertiesFileException(path, e);
        }
    }

    @Override
    public Bootstrap withMethodsMapper(MethodsMapper mapper) throws AlmiException
    {
        try
        {
            mapper.configure();
            mMethodDescriptorMap = mapper.methodDescriptorMap();
        }
        catch(Exception e)
        {
            throw new MethodMapperException(e);
        }
        return this;
    }

    @Override
    public Bootstrap withThreadName(String name)
    {
        mThreadName = name;
        return this;
    }

    @Override
    public Bootstrap withPort(int port)
    {
        mPort = port;
        return this;
    }

    @Override
    public Bootstrap withConnectionTimeout(int timeout)
    {
        mConnectTimeout = timeout;
        return this;
    }

    @Override
    public Bootstrap withRemoteCallTimeout(int timeout)
    {
        mPromiseTimeout = timeout;
        return this;
    }

    @Override
    public Almi start()
    {
        return build().start();
    }

    private Almi build()
    {
        return mAlmiFactory.create(
          mMethodDescriptorMap,
          mThreadName,
          mPort,
          mConnectTimeout,
          mPromiseTimeout
        );
    }

    private String getHostname()
    {
        try
        {
            return InetAddress.getLocalHost().getHostName();
        }
        catch(UnknownHostException e)
        {
            return "localhost";
        }
    }
}

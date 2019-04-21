package socket.bootstrap;

import com.google.inject.Guice;
import com.google.inject.Injector;
import exceptions.AlmiException;
import exceptions.InvalidPropertiesFileException;
import exceptions.MethodMapperException;
import guice.AlmiModules;
import method.MethodDescriptor;
import socket.Almi;
import socket.AlmiFactory;
import utils.Constants;
import utils.PropertiesUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AlmiBootstrap implements Bootstrap
{
    private final AlmiFactory mAlmiFactory;

    private Map<String, MethodDescriptor> mMethodDescriptorMap = new HashMap<>();
    private String                        mThreadName          = Constants.DEFAULT_THREAD_NAME;
    private String                        mSocketAddress       = Constants.DEFAULT_ADDRESS;
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
        withAddress(PropertiesUtils.optString(props, Constants.PROPERTY_ADDRESS, Constants.DEFAULT_ADDRESS));
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
    public Bootstrap withAddress(String address)
    {
        mSocketAddress = address;
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
          mSocketAddress,
          mPort,
          mConnectTimeout,
          mPromiseTimeout
        );
    }
}

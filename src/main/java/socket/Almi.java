package socket;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import exceptions.AlmiException;
import exceptions.ResultException;
import io.netty.util.concurrent.Promise;
import message.MethodCallRequest;
import method.MethodDescriptor;
import method.MethodsManager;
import socket.bootstrap.MethodsMapper;
import socket.client.ConnectionPoolManager;
import socket.client.ConnectionPoolManagerFactory;
import socket.handler.PromisesManager;
import socket.server.ServerSocketService;
import socket.server.ServerSocketServiceFactory;
import utils.Service;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Almi implements Service<Almi>
{
    private final ServerSocketService   mServerSocketService;
    private final MethodsManager        mMethodsManager;
    private final PromisesManager       mPromisesManager;
    private final int                   mPromiseTimeout;
    private final ConnectionPoolManager mConnectionPoolManager;

    @Inject
    private Almi(
      ServerSocketServiceFactory serverSocketServiceFactory,
      ConnectionPoolManagerFactory connectionPoolManagerFactory,
      MethodsManager methodsManager,
      PromisesManager promisesManager,
      @Assisted Map<String, MethodDescriptor> methodDescriptorMap,
      @Assisted("threadName") String threadName,
      @Assisted("socketAddress") String socketAddress,
      @Assisted("port") int port,
      @Assisted("connectTimeout") int connectTimeout,
      @Assisted("promiseTimeout") int promiseTimeout
    )
    {
        mMethodsManager = methodsManager;
        mPromisesManager = promisesManager;
        mPromiseTimeout = promiseTimeout;
        mConnectionPoolManager = connectionPoolManagerFactory.create(connectTimeout);
        mServerSocketService = serverSocketServiceFactory.create(socketAddress, port, connectTimeout, threadName);
        methodsManager.addAll(methodDescriptorMap);
    }

    @Override
    public Almi start()
    {
        mServerSocketService.start();
        return this;
    }

    @Override
    public Almi stop()
    {
        mServerSocketService.stop();
        return this;
    }

    @SuppressWarnings (value="unchecked")
    public <T> T callMethod(InetSocketAddress address, String remoteName, List<Serializable> params)
      throws Exception
    {
        MethodCallRequest methodCallRequest = new MethodCallRequest(remoteName, params);
        Promise<Serializable> promise = mConnectionPoolManager.callMethod(
          address,
          methodCallRequest
        );

        try
        {
            return (T) promise.get(mPromiseTimeout, TimeUnit.MILLISECONDS);
        }
        catch(Exception e)
        {
            //TODO: manage exceptions!
            throw new ResultException(remoteName, address, e);
        }
        finally
        {
            mPromisesManager.cancelPromise(methodCallRequest.getId());
        }
    }

    public <T> T callMethod(String address, int port, String remoteName, List<Serializable> params)
      throws Exception
    {
        return callMethod(new InetSocketAddress(address, port), remoteName, params);
    }
}

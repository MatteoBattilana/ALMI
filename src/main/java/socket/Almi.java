package socket;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import exceptions.InvisibleWrapperException;
import io.netty.util.concurrent.Promise;
import message.MethodCallRequest;
import message.MethodCallResponse;
import method.MethodDescriptor;
import method.MethodsManager;
import socket.client.ConnectionPoolManager;
import socket.client.ConnectionPoolManagerFactory;
import socket.handler.PromisesManager;
import socket.server.ServerSocketService;
import socket.server.ServerSocketServiceFactory;
import utils.ExceptionUtils;
import utils.Service;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Almi implements Service<Almi>
{
    private final ServerSocketService   mServerSocketService;
    private final PromisesManager       mPromisesManager;
    private final ConnectionPoolManager mConnectionPoolManager;
    private final int                   mPromiseTimeout;

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
      @Assisted("connectTimeout") int connectionTimeout,
      @Assisted("promiseTimeout") int promiseTimeout
    )
    {
        mPromisesManager = promisesManager;
        mPromiseTimeout = promiseTimeout;
        mConnectionPoolManager = connectionPoolManagerFactory.create(connectionTimeout);
        mServerSocketService = serverSocketServiceFactory.create(socketAddress, port, connectionTimeout, threadName);
        methodsManager.addAll(methodDescriptorMap);
    }

    @Override
    public Almi start()
    {
        mServerSocketService.start();
        return this;
    }

    @Override
    public void stop()
    {
        mConnectionPoolManager.close();
        mServerSocketService.stop();
    }

    @SuppressWarnings (value="unchecked")
    private  <T> T callMethod(InetSocketAddress address, long timeout,  String remoteName, List<Serializable> params)
      throws InvisibleWrapperException
    {
        MethodCallRequest methodCallRequest = new MethodCallRequest(remoteName, params);
        Promise<MethodCallResponse> promise = mConnectionPoolManager.callMethod(
          address,
          methodCallRequest
        );

        try
        {
            return (T) promise.get(timeout, TimeUnit.MILLISECONDS).getReturnValue();
        }
        catch(Throwable e)
        {
            // TODO: Made a choice!
            throw ExceptionUtils.wrap(e);
        }
        finally
        {
            mPromisesManager.cancelPromise(methodCallRequest.getId());
        }
    }

    public <T> T callMethod(String address, int port, String remoteName, List<Serializable> params)
      throws InvisibleWrapperException
    {
        return callMethod(new InetSocketAddress(address, port), mPromiseTimeout, remoteName, params);
    }

    public <T> T callMethod(String address, int port, long timeout, String remoteName, List<Serializable> params)
      throws InvisibleWrapperException
    {
        return callMethod(new InetSocketAddress(address, port), timeout, remoteName, params);
    }
}
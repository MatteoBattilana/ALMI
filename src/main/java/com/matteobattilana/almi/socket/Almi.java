package com.matteobattilana.almi.socket;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.matteobattilana.almi.exceptions.InvisibleWrapperException;
import io.netty.util.concurrent.Promise;
import com.matteobattilana.almi.message.MethodCallRequest;
import com.matteobattilana.almi.message.MethodCallResponse;
import com.matteobattilana.almi.method.MethodDescriptor;
import com.matteobattilana.almi.method.MethodsManager;
import com.matteobattilana.almi.socket.client.ConnectionPoolManager;
import com.matteobattilana.almi.socket.client.ConnectionPoolManagerFactory;
import com.matteobattilana.almi.socket.handler.PromisesManager;
import com.matteobattilana.almi.socket.server.ServerSocketService;
import com.matteobattilana.almi.socket.server.ServerSocketServiceFactory;
import com.matteobattilana.almi.utils.ExceptionUtils;
import com.matteobattilana.almi.utils.Service;

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
      @Assisted("port") int port,
      @Assisted("connectTimeout") int connectionTimeout,
      @Assisted("promiseTimeout") int promiseTimeout
    )
    {
        mPromisesManager = promisesManager;
        mPromiseTimeout = promiseTimeout;
        mConnectionPoolManager = connectionPoolManagerFactory.create(connectionTimeout);
        mServerSocketService = serverSocketServiceFactory.create(port, connectionTimeout, threadName);
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
            throw ExceptionUtils.wrap(e);
        }
        finally
        {
            mPromisesManager.cancelPromise(methodCallRequest.getId());
        }
    }

    public <T> T callMethod(String hostname, int remotePort, String remoteMethodName, List<Serializable> methodParams)
      throws InvisibleWrapperException
    {
        return callMethod(new InetSocketAddress(hostname, remotePort), mPromiseTimeout, remoteMethodName, methodParams);
    }

    public <T> T callMethod(String hostname, int remotePort, long callTimeout, String remoteMethodName, List<Serializable> methodParams)
      throws InvisibleWrapperException
    {
        return callMethod(new InetSocketAddress(hostname, remotePort), callTimeout, remoteMethodName, methodParams);
    }
}
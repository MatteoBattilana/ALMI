package socket;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import exceptions.AlmiException;
import io.netty.util.concurrent.Promise;
import message.MethodCallRequest;
import method.MethodsManager;
import socket.client.ConnectionPoolManager;
import socket.client.ConnectionPoolManagerFactory;
import socket.server.ServerSocketService;
import socket.server.ServerSocketServiceFactory;
import utils.Constants;
import utils.Service;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Almi implements Service<Almi>
{
    private final ServerSocketService   mServerSocketService;
    private final MethodsManager        mMethodsManager;
    private final ConnectionPoolManager mConnectionPoolManager;

    @Inject
    private Almi(
      ServerSocketServiceFactory serverSocketServiceFactory,
      ConnectionPoolManagerFactory connectionPoolManagerFactory,
      MethodsManager methodsManager,
      @Assisted("threadName") String threadName,
      @Assisted("socketAddress") String socketAddress,
      @Assisted("port") int port,
      @Assisted("connectTimeout") int connectTimeout
    )
    {
        mMethodsManager = methodsManager;
        mConnectionPoolManager = connectionPoolManagerFactory.create(connectTimeout);
        mServerSocketService = serverSocketServiceFactory.create(socketAddress, port, connectTimeout, threadName);
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

    public void addMethod(Object instance, Method method, String remoteName)
      throws AlmiException
    {
        mMethodsManager.addMethod(instance, method, remoteName);
    }

    public void addMethod(Object instance, Method method)
      throws AlmiException
    {
        mMethodsManager.addMethod(instance, method, method.getName());
    }

    public <T> T callMethod(InetSocketAddress address, String remoteName, List<Serializable> params)
      throws AlmiException
    {
        Promise<Serializable> promise = mConnectionPoolManager.callMethod(
          address,
          new MethodCallRequest(remoteName, params)
        );

        try
        {
            //TODO: refactor try/catch
            if(promise.sync().isDone() && promise.isSuccess())
            {
                return (T) promise.get(Constants.PROMISE_TIMEOUT, TimeUnit.MILLISECONDS);
            }
        }
        catch(Exception e)
        {
            throw new AlmiException(e);
        }
        throw new AlmiException("out");
    }

    public Serializable callMethod(String address, int port, String remoteName, List<Serializable> params)
      throws AlmiException
    {
        return callMethod(new InetSocketAddress(address, port), remoteName, params);
    }
}

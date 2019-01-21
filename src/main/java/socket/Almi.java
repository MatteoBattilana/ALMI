package socket;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import exceptions.InvisibleWrapperException;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.GlobalEventExecutor;
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
        mConnectionPoolManager.close();
        mServerSocketService.stop();
        return this;
    }

    @SuppressWarnings (value="unchecked")
    public <T> T callMethod(InetSocketAddress address, String remoteName, List<Serializable> params)
      throws InvisibleWrapperException
    {
        MethodCallRequest methodCallRequest = new MethodCallRequest(remoteName, params);
        Promise<MethodCallResponse> promise = mConnectionPoolManager.callMethod(
          address,
          methodCallRequest
        );

        try
        {
            return (T) promise.get(mPromiseTimeout, TimeUnit.MILLISECONDS).getReturnValue();
        }
        catch(Throwable e)
        {
            // TODO: Made a choice!
            throw new InvisibleWrapperException(e);
        }
        // finally
        // {
        //     mPromisesManager.cancelPromise(methodCallRequest.getId());
        // }
    }

    public <T> T callMethod(String address, int port, String remoteName, List<Serializable> params)
      throws InvisibleWrapperException
    {
        // Promise<MethodCallResponse> promise = GlobalEventExecutor.INSTANCE.newPromise();
        // Thread one = new Thread()
        // {
        //     public void run()
        //     {
        //         try
        //         {
        //             System.out.println("Does it work?");
        //
        //             Thread.sleep(300);
        //             promise.setSuccess(new MethodCallResponse("sd", ""));
        //             System.out.println("Nope, it doesnt...again.");
        //         }
        //         catch(InterruptedException v)
        //         {
        //             System.out.println(v);
        //         }
        //     }
        // };
        //
        // one.start();
        //
        // try
        // {
        //     promise.sync();
        // }
        // catch(InterruptedException e)
        // {
        //     e.printStackTrace();
        // }
        // return (T) new Double(2.0);
        return callMethod(new InetSocketAddress(address, port), remoteName, params);
    }
}
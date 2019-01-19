package socket;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import exceptions.AlmiException;
import io.netty.util.concurrent.Promise;
import method.MethodsManager;
import socket.server.ServerSocketService;
import socket.server.ServerSocketServiceFactory;
import utils.Service;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

public class Almi implements Service<Almi>
{
    private final ServerSocketService mServerSocketService;
    private final MethodsManager      mMethodsManager;

    @Inject
    private Almi(
      ServerSocketServiceFactory serverSocketServiceFactory,
      MethodsManager methodsManager,
      @Assisted("threadName") String threadName,
      @Assisted("socketAddress") String socketAddress,
      @Assisted("port") int port,
      @Assisted("connectTimeout") int connectTimeout
    )
    {
        mMethodsManager = methodsManager;
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

    public Promise<Serializable> callMethod(String remoteName, List<Serializable> param)
    {
        // TODO: implements connection poll!
        return null;
    }
}

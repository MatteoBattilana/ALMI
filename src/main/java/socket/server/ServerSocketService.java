package socket.server;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import exceptions.MethodAlreadyExistsException;
import exceptions.UnsupportedReturnTypeException;
import method.MethodsManager;
import utils.Constants;
import utils.Service;

import java.io.Serializable;
import java.lang.reflect.Method;

public class ServerSocketService implements Service<ServerSocketService>, Runnable
{
    private final ServerSocketFactory mServerSocketFactory;
    private final MethodsManager      mMethodsManager;

    private ServerSocket mServerSocket;
    private int          mPort = Constants.SOCKET_PORT;

    @Inject
    public ServerSocketService(
      ServerSocketFactory serverSocketFactory,
      MethodsManager methodsManager
    )
    {
        mServerSocketFactory = serverSocketFactory;
        mMethodsManager = methodsManager;
    }

    public ServerSocketService setPort(int port)
    {
        mPort = port;
        return this;
    }

    @Override
    public ServerSocketService start()
    {
        mServerSocket = mServerSocketFactory.create(mPort);
        new Thread(
          this,
          Constants.SOCKET_SERVICE_NAME
        ).start();
        return this;
    }

    @Override
    public ServerSocketService stop()
    {
        mServerSocket.close();
        return this;
    }

    @Override
    public void run()
    {
        try
        {
            mServerSocket.startListening();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public ServerSocketService addMethod(Object instance, Method method, String remoteName)
      throws MethodAlreadyExistsException, UnsupportedReturnTypeException
    {
        mMethodsManager.addMethod(instance, method, remoteName);
        return this;
    }
}

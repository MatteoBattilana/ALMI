package socket.server;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import utils.Constants;
import utils.Service;

public class ServerSocketService implements Service<ServerSocketService>, Runnable
{
    private final ServerSocketFactory mServerSocketFactory;

    private ServerSocket mServerSocket;
    private int          mPort = Constants.SOCKET_PORT;

    @Inject
    public ServerSocketService(
      ServerSocketFactory serverSocketFactory
    )
    {
        mServerSocketFactory = serverSocketFactory;
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
}

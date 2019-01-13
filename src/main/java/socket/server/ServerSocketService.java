package socket.server;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import utils.Constants;
import utils.Service;

public class ServerSocketService implements Service, Runnable
{
    private final ServerSocket mServerSocket;

    @Inject
    public ServerSocketService(
      ServerSocketFactory serverSocketFactory,
      @Assisted int port
    )
    {
        mServerSocket = serverSocketFactory.create(port);
    }

    @Override
    public void start()
    {
        new Thread(
          this,
          Constants.SOCKET_SERVICE_NAME
        ).start();
    }

    @Override
    public void stop()
    {
        mServerSocket.close();
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

package websocket;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import method.Constants;

public class ServerSocketService implements Service, Runnable
{
    private final ServerSocket mServerSocket;

    private Thread mThread;

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
        mThread = new Thread(
          this,
          Constants.SOCKET_SERVICE_NAME
        );
        mThread.start();
    }

    @Override
    public void stop()
    {
        try
        {
            mServerSocket.close();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
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

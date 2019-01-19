package socket.server;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import utils.Service;

public class ServerSocketService implements Service<ServerSocketService>, Runnable
{
    private final String       mThreadName;
    private final ServerSocket mServerSocket;

    @Inject
    public ServerSocketService(
      ServerSocketFactory serverSocketFactory,
      @Assisted("socketAddress") String socketAddress,
      @Assisted("port") int port,
      @Assisted("connectTimeout") int connectTimeout,
      @Assisted("threadName") String threadName
      )
    {
        mThreadName = threadName;
        mServerSocket = serverSocketFactory.create(socketAddress, port, connectTimeout);
    }

    @Override
    public ServerSocketService start()
    {
        new Thread(
          this,
          mThreadName
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

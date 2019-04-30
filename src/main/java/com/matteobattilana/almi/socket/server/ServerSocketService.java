package com.matteobattilana.almi.socket.server;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.matteobattilana.almi.utils.Service;

public class ServerSocketService implements Service<ServerSocketService>, Runnable
{
    private final String       mThreadName;
    private final ServerSocket mServerSocket;

    @Inject
    public ServerSocketService(
      ServerSocketFactory serverSocketFactory,
      @Assisted("port") int port,
      @Assisted("connectTimeout") int connectTimeout,
      @Assisted("threadName") String threadName
      )
    {
        mThreadName = threadName;
        mServerSocket = serverSocketFactory.create(port, connectTimeout);
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

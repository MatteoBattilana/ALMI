package socket.client;

import com.google.inject.Inject;
import io.netty.util.concurrent.Promise;
import message.BaseMessage;
import utils.Constants;
import utils.Service;

import java.io.Serializable;

public class ClientSocketService implements Service<ClientSocketService>, Runnable
{
    private final ConnectionPoolManager mConnectionPoolManager;

    @Inject
    public ClientSocketService(
      ClientSocketFactory clientSocketFactory
    )
    {
        mConnectionPoolManager = clientSocketFactory.create("localhost", Constants.SOCKET_PORT);
    }

    public Promise<Serializable> writeMessage(BaseMessage message)
    {
        return mConnectionPoolManager.writeMessage(message);
    }

    @Override
    public ClientSocketService start()
    {
        new Thread(
          this,
          Constants.SOCKET_SERVICE_THREAD_NAME
        ).start();
        return this;
    }

    @Override
    public ClientSocketService stop()
    {
        mConnectionPoolManager.close();
        return this;
    }

    @Override
    public void run()
    {
        try
        {
            mConnectionPoolManager.connect();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}

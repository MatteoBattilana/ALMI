package socket.client;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import io.netty.util.concurrent.Promise;
import message.BaseMessage;
import utils.Constants;
import utils.Service;

import java.io.Serializable;
import java.util.concurrent.Future;

public class ClientSocketService implements Service<ClientSocketService>, Runnable
{
    private final ClientSocket mClientSocket;

    @Inject
    public ClientSocketService(
      ClientSocketFactory clientSocketFactory
    )
    {
        mClientSocket = clientSocketFactory.create("localhost", Constants.SOCKET_PORT);
    }

    public Promise<Serializable> writeMessage(BaseMessage message)
    {
        return mClientSocket.writeMessage(message);
    }

    @Override
    public ClientSocketService start()
    {
        new Thread(
          this,
          Constants.SOCKET_SERVICE_NAME
        ).start();
        return this;
    }

    @Override
    public ClientSocketService stop()
    {
        mClientSocket.close();
        return this;
    }

    @Override
    public void run()
    {
        try
        {
            mClientSocket.connect();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}

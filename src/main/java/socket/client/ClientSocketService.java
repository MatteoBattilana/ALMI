package socket.client;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import message.BaseMessage;
import message.Message;
import utils.Constants;
import utils.Service;

import java.io.Serializable;

public class ClientSocketService implements Service<ClientSocketService>, Runnable
{
    private final ClientSocket mClientSocket;

    @Inject
    public ClientSocketService(
      ClientSocketFactory clientSocketFactory,
      @Assisted String host,
      @Assisted int port
    )
    {
        mClientSocket = clientSocketFactory.create(host, port);
    }

    public void writeMessage(Serializable message)
    {
        mClientSocket.writeMessage(message);
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

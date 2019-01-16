package socket.client;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import message.BaseMessage;
import message.Message;
import utils.Constants;
import utils.Service;

public class ClientSocketService implements Service, Runnable
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

    public void writeMessage(Object message)
    {
        mClientSocket.writeMessage(message);
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
        mClientSocket.close();
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

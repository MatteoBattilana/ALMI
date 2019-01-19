package socket.server;

import com.google.inject.assistedinject.Assisted;

public interface ServerSocketServiceFactory
{
    ServerSocketService create(
      @Assisted("socketAddress") String socketAddress,
      @Assisted("port") int port,
      @Assisted("connectTimeout") int connectTimeout,
      @Assisted("threadName") String threadName
    );
}

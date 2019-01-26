package socket.server;

import com.google.inject.assistedinject.Assisted;

public interface ServerSocketFactory
{
    ServerSocket create(String address, @Assisted("port") int port, @Assisted("connectionTimeout") int connectionTimeout);
}

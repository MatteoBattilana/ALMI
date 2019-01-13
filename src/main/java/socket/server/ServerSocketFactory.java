package socket.server;

public interface ServerSocketFactory
{
    ServerSocket create(int port);
}

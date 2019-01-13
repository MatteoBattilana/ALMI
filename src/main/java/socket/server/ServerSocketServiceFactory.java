package socket.server;

public interface ServerSocketServiceFactory
{
    ServerSocketService create(int port);
}
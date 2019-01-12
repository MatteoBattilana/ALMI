package socket;

public interface ServerSocketServiceFactory
{
    ServerSocketService create(int port);
}
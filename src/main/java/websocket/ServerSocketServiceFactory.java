package websocket;

public interface ServerSocketServiceFactory
{
    ServerSocketService create(int port);
}
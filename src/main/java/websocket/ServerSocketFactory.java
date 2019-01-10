package websocket;

public interface ServerSocketFactory
{
    ServerSocket create(int port);
}

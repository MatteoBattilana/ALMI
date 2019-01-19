package socket.client;

public interface ClientSocketFactory
{
    ConnectionPoolManager create(String host, int port);
}

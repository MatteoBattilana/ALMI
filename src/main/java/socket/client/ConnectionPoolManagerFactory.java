package socket.client;

public interface ConnectionPoolManagerFactory
{
    ConnectionPoolManager create(int timeout);
}
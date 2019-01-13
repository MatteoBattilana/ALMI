package socket.client;

public interface ClientSocketServiceFactory
{
    ClientSocketService create(String host, int port);
}
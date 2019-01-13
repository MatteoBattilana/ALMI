package socket.client;

public interface ClientSocketFactory
{
    ClientSocket create(String host, int port);
}

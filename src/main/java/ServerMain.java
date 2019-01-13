import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.AlmiModules;
import socket.server.ServerSocketService;
import socket.server.ServerSocketServiceFactory;

public class ServerMain
{
    public static void main(String [] args)
    {
        Injector injector = Guice.createInjector(new AlmiModules());

        ServerSocketServiceFactory serverSocketFactory = injector.getInstance(ServerSocketServiceFactory.class);
        ServerSocketService serverSocket = serverSocketFactory.create(11111);
        serverSocket.start();
        Thread.yield();
    }
}

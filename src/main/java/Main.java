import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.AlmiModules;
import websocket.ServerSocketService;
import websocket.ServerSocketServiceFactory;

public class Main
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

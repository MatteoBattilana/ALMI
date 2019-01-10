import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.AlmiModules;
import websocket.ServerSocket;
import websocket.ServerSocketFactory;

public class Main
{
    public static void main(String [] args)
    {
        Injector injector = Guice.createInjector(new AlmiModules());

        ServerSocketFactory serverSocketFactory = injector.getInstance(ServerSocketFactory.class);
        ServerSocket serverSocket = serverSocketFactory.create(11111);
        try {
            serverSocket.start();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}

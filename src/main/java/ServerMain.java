import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.AlmiModules;
import socket.server.ServerSocketService;

public class ServerMain
{
    public static void main(String[] args)
    {
        Injector injector = Guice.createInjector(new AlmiModules());

        ServerSocketService server = injector.getInstance(ServerSocketService.class)
          .setPort(11111)
          .start();
        Thread.yield();
    }
}

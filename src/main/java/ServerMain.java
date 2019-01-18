import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.AlmiModules;
import socket.server.ServerSocketService;

public class ServerMain
{
    public static void main(String[] args) throws Exception
    {
        Injector injector = Guice.createInjector(new AlmiModules());

        ServerSocketService server = injector.getInstance(ServerSocketService.class)
          .setPort(11111)
          .start();


        server.addMethod(new ServerMain(), ServerMain.class.getMethod("test", String.class), "test");
        Thread.yield();
    }

    public void test(String value)
    {
        System.out.println("Remotely called with value: " + value);
    }
}

import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.AlmiModules;
import socket.server.ServerSocketService;

import java.io.IOException;
import java.io.InputStream;

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

    public InputStream test(String value) throws Exception
    {
        System.out.println("Remotely called with value: " + value);
        // return "Remotely called with value: " + value;
        return new InputStream() {
            @Override
            public int read()
              throws IOException
            {
                return 0;
            }
        };
    }
}

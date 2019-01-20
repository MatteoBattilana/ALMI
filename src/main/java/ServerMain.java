import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.AlmiModules;
import socket.Almi;
import socket.AlmiFactory;
import socket.bootstrap.DefaultAlmiBootstrap;
import socket.server.ServerSocketService;

import java.io.IOException;
import java.io.InputStream;

public class ServerMain
{
    public static void main(String[] args)
      throws Exception
    {
        Injector injector = Guice.createInjector(new AlmiModules());

        DefaultAlmiBootstrap bootstrap = injector.getInstance(DefaultAlmiBootstrap.class);

        Almi server = bootstrap.withConnectionTimeout(1000)
          .withAddress("localhost")
          .withPort(8888)
          .withThreadName("netty-server")
          .start();

        server.addMethod(new ServerMain(), ServerMain.class.getMethod("test", String.class), "test");
        Thread.yield();
    }

    public String test(String value)
      throws Exception
    {
        System.out.println("Remotely called with value: " + value);
        return "Remotely called with value: " + value;
    }
}

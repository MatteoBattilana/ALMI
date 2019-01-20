package example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import exceptions.AlmiException;
import guice.AlmiModules;
import socket.Almi;
import socket.bootstrap.DefaultAlmiBootstrap;
import socket.bootstrap.MethodsMapper;

public class ServerMain
{
    public static void main(String[] args)
      throws Exception
    {
        Injector injector = Guice.createInjector(new AlmiModules());

        DefaultAlmiBootstrap bootstrap = injector.getInstance(DefaultAlmiBootstrap.class);

        Almi server = bootstrap
          .withConnectionTimeout(1000)
          .withPromiseTimeout(2000)
          .withAddress("localhost")
          .withPort(8888)
          .withThreadName("netty-server")
          .withMethodsMapper(new MethodsMapper() {
              @Override
              public void configure()
                throws Exception
              {
                  install(
                    bind(new ServerMain()).method("test", String.class).withDefaultName(),
                    bind(new ServerMain()).method("test2", String.class).withDefaultName()
                  );
              }
          })
          .start();

        Thread.yield();
    }

    public String test(String value)
    {
        return "Remotely called with value: " + value;
    }

    public AlmiException test2(String value)
      throws AlmiException
    {
        throw new AlmiException("asd");
    }
}

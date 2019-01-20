import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.AlmiModules;
import io.netty.util.concurrent.Promise;
import socket.Almi;
import socket.bootstrap.DefaultAlmiBootstrap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Collections;

public class ClientMain
{
    public static void main(String[] args)
      throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Injector injector = Guice.createInjector(new AlmiModules());

        DefaultAlmiBootstrap bootstrap = injector.getInstance(DefaultAlmiBootstrap.class);

        Almi server = bootstrap.withConnectionTimeout(1000)
          .withAddress("localhost")
          .withPort(8889)
          .withThreadName("netty-client")
          .start();

        while(true)
        {
            Serializable value = server.callMethod(
              "localhost",
              8888,
              "test",
              Collections.singletonList(br.readLine())
            );
            System.out.println("Value : " + value);
        }
    }
}

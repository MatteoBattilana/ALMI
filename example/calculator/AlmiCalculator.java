package calculator;

import com.google.inject.Guice;
import com.google.inject.Injector;
import exceptions.InvisibleWrapperException;
import guice.AlmiModules;
import socket.Almi;
import socket.bootstrap.DefaultAlmiBootstrap;
import socket.bootstrap.MethodsMapper;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class AlmiCalculator
{
    public static void main(String[] args)
      throws Exception
    {
        Injector injector = Guice.createInjector(new AlmiModules());
        DefaultAlmiBootstrap bootstrap = injector.getInstance(DefaultAlmiBootstrap.class);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Almi server = bootstrap
          .withPort(8888)
          .withPromiseTimeout(100)
          .withMethodsMapper(new MethodsMapper()
          {
              @Override
              public void configure()
                throws Exception
              {
                  install(
                    bind(new Calculator()).method("execute", double.class, Calculator.Operation.class, double.class)
                      .withDefaultName(),
                    bind(new Calculator()).method(Calculator.class.getMethod("sqrt", double.class))
                      .withName("positiveSqrt")
                  );
              }
          })
          .start();
        Thread.sleep(1000);

        for(int i = 0; i < 10; i++)
        {
            try
            {
                double result = server.callMethod(
                  "localhost",
                  8888,
                  "execute",
                  Arrays.asList(1.0, Calculator.Operation.SUM, 2.0)
                );
                System.out.println(result);

                result = server.callMethod(
                  "localhost",
                  8888,
                  "execute",
                  Arrays.asList(1.0, Calculator.Operation.MUL, 2.0)
                );
                System.out.println(result);

                result = server.callMethod(
                  "localhost",
                  8888,
                  "execute",
                  Arrays.asList(1.0, Calculator.Operation.DIV, 32.0)
                );
                System.out.println(result);

                try
                {
                    result = server.callMethod("localhost", 8888, "positiveSqrt", Collections.singletonList(-1.0));
                    System.out.println(result);
                }
                catch(InvisibleWrapperException e)
                {
                    System.out.println(e.getCause().getMessage());
                }
            }
            catch(Exception e)
            {}
        }

        server.stop();
    }
}

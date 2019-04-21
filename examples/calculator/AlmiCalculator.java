package calculator;

import exceptions.InvisibleWrapperException;
import socket.Almi;
import socket.bootstrap.AlmiBootstrap;
import socket.bootstrap.MethodsMapper;

import java.util.Arrays;
import java.util.Collections;

public class AlmiCalculator
{
    public static void main(String[] args)
      throws Exception
    {
        Almi server = AlmiBootstrap.bootstrap()
          .withPort(8888)
          .withRemoteCallTimeout(2000)
          .withMethodsMapper(new MethodsMapper()
          {
              @Override
              public void configure()
                throws Exception
              {
                  addMethods(
                    bindStatic(Calculator.class).method("execute", double.class, Calculator.Operation.class, double.class).withDefaultName(),
                    bind(new Calculator()).method(Calculator.class.getMethod("sqrt", double.class)).withName("positiveSqrt")
                  );
              }
          })
          .start();

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

        server.stop();
    }
}

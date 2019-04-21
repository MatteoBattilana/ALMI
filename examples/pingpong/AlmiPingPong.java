package pingpong;

import socket.Almi;
import socket.bootstrap.AlmiBootstrap;

import java.util.Arrays;
import java.util.Collections;

public class AlmiPingPong
{
    public static void main(String[] args)
      throws Exception
    {
        Almi server = AlmiBootstrap.bootstrap()
          .withPort(8888)
          .withRemoteCallTimeout(2000)
          .withMethodsMapper(new MethodsMapperImpl(new PingPong()))
          .start();

        PingPong.State state = PingPong.State.PING;
        for(int i = 0; i < 10; i++)
        {
            state = server.callMethod(
              "localhost",
              8888,
              "ping",
              Collections.singletonList(state)
            );
            System.out.println(state);
        }
        server.stop();
    }
}

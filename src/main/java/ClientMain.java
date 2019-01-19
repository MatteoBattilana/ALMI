import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.AlmiModules;
import io.netty.util.concurrent.Promise;
import message.MethodCallRequest;
import socket.client.ClientSocketService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;

public class ClientMain
{
    public static void main(String[] args)
      throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Injector injector = Guice.createInjector(new AlmiModules());

        ClientSocketService clientSocketService = injector.getInstance(ClientSocketService.class);

        clientSocketService.start();

        while(true)
        {
            Promise<Serializable> future = clientSocketService.writeMessage(new MethodCallRequest(
              "test",
              Collections
                .singletonList(br.readLine())
            ));
            System.out.println("idDone : " + future.syncUninterruptibly().isSuccess());
            System.out.println("Value : " + future.get().toString());
        }
    }
}

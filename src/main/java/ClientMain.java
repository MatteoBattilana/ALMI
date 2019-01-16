import com.google.inject.Guice;
import com.google.inject.Injector;
import exceptions.AlmiException;
import guice.AlmiModules;
import message.ErrorMessage;
import message.MethodCallRequest;
import socket.client.ClientSocketService;
import socket.client.ClientSocketServiceFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ClientMain {
    public static void main(String [] args) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Injector injector = Guice.createInjector(new AlmiModules());

        ClientSocketServiceFactory clientSocketServiceFactory = injector.getInstance(ClientSocketServiceFactory.class);
        ClientSocketService localhost = clientSocketServiceFactory.create("localhost", 11111);
        localhost.start();

        while(true)
        {
            br.readLine();
            localhost.writeMessage(new MethodCallRequest("uuid", "sum", Arrays.asList(1000,"111111111111111111111111")));
        }
    }
}

package clientinformation;

import com.matteobattilana.almi.socket.Almi;
import com.matteobattilana.almi.socket.bootstrap.AlmiBootstrap;
import com.matteobattilana.almi.socket.bootstrap.MethodsMapper;

import java.lang.management.ManagementFactory;

public class ClientInformation implements Methods
{
    public static void main(String[] args)
      throws Exception
    {
        Almi server = AlmiBootstrap.bootstrap()
          .withPort(8888)
          .withThreadName("ClientInformation-ALMI-server")
          .withRemoteCallTimeout(2000)
          .withMethodsMapper(new MethodsMapper()
          {
              @Override
              public void configure()
                throws Exception
              {
                  addMethods(
                    bind(new ClientInformation()).method(ClientInformation.class.getMethod("getInformation")) .withDefaultName(),
                    bind(new ClientInformation()).method(ClientInformation.class.getMethod("getIpAddress")) .withDefaultName()
                  );
              }
          })
          .start();

        RemoteMethodMapper remote = new RemoteMethodMapper(server, "localhost", 8888);

        String information = remote.getInformation();
        System.out.println(information);

        try
        {
            String ipAddress = remote.getIpAddress();
            System.out.println(ipAddress);
        }
        catch(NoInternetConnectionException e)
        {
            System.err.println(e.getClass());
        }

        server.stop();
    }

    @Override
    public String getInformation()
    {
        return new StringBuilder()
          .append("timestamp: " + System.currentTimeMillis() + "\n")
          .append("freeMemory: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) + "\n")
          .append("jvmUptime: " + ManagementFactory.getRuntimeMXBean().getUptime() + "\n").toString();
    }

    @Override
    public String getIpAddress()
      throws NoInternetConnectionException
    {
        throw new NoInternetConnectionException();
    }
}

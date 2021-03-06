package it;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.matteobattilana.almi.socket.Almi;
import com.matteobattilana.almi.socket.bootstrap.AlmiBootstrap;
import com.matteobattilana.almi.socket.bootstrap.MethodsMapper;

import java.util.Collections;

public class ServerTest
{
    private Almi mServer1;
    private Almi mServer2;

    private final int mPort1 = 8888;
    private final int mPort2 = 8889;

    @Before
    public void setup() throws Exception
    {
        mServer1 = AlmiBootstrap.bootstrap()
          .withPort(mPort1)
          .withRemoteCallTimeout(2000)
          .start();

        mServer2 = AlmiBootstrap.bootstrap()
          .withPort(mPort2)
          .withRemoteCallTimeout(2000)
          .withMethodsMapper(new MethodsMapper()
          {
              @Override
              public void configure()
                throws Exception
              {
                  addMethods(
                    bindStatic(ServerTest.class).method("helloWorld").withDefaultName()
                  );
              }
          })
          .start();
    }

    @Test
    public void remoteCall() throws Exception
    {
        String ret = mServer1.callMethod(
          "localhost",
          mPort2,
          "helloWorld",
          Collections.emptyList()
        );

        Assert.assertEquals(helloWorld(), ret);
    }

    public static String helloWorld()
    {
        return "Hello World!";
    }
}

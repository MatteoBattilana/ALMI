package socket.bootstrap;

import exceptions.InvalidPropertiesFileException;
import exceptions.InvalidPropertyException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import socket.Almi;
import socket.AlmiFactory;
import utils.Constants;

import java.util.Collections;


public class DefaultAlmiBootstrapTest
{
    private AlmiFactory mAlmiFactory;

    @Before
    public void setup()
    {
        mAlmiFactory = Mockito.mock(AlmiFactory.class);
        Mockito.when(mAlmiFactory.create(
          Mockito.anyMap(),
          Mockito.anyString(),
          Mockito.anyString(),
          Mockito.anyInt(),
          Mockito.anyInt(),
          Mockito.anyInt()
        )).thenReturn(Mockito.mock(Almi.class));
    }

    @Test
    public void bootstrapDefault()
    {
        new DefaultAlmiBootstrap(mAlmiFactory)
          .start();

        Mockito.verify(mAlmiFactory).create(
          Collections.emptyMap(),
          Constants.DEFAULT_THREAD_NAME,
          Constants.DEFAULT_ADDRESS,
          Constants.DEFAULT_PORT,
          Constants.DEFAULT_CONNECTION_TIMEOUT,
          Constants.DEFAULT_PROMISE_TIMEOUT
        );
    }

    @Test
    public void bootstrapNormal()
      throws Exception
    {
        new DefaultAlmiBootstrap(mAlmiFactory)
          .withPromiseTimeout(2000)
          .withThreadName("thread-name")
          .withPort(12345)
          .withAddress("localhost")
          .withConnectionTimeout(4000)
          .start();

        Mockito.verify(mAlmiFactory).create(
          Collections.emptyMap(),
          "thread-name",
          "localhost",
          12345,
          4000,
          2000
        );
    }

    @Test
    public void bootstrapPropertiesFile()
      throws Exception
    {
        new DefaultAlmiBootstrap(mAlmiFactory)
          .fromPropertiesFile("src/test/java/test-assets/bootstrap.properties")
          .start();

        Mockito.verify(mAlmiFactory).create(
          Collections.emptyMap(),
          "thread-name",
          "localhost",
          12345,
          2000,
          1000
        );
    }

    @Test(expected = InvalidPropertiesFileException.class)
    public void bootstrapMissingPropertiesFile()
      throws Exception
    {
        new DefaultAlmiBootstrap(mAlmiFactory)
          .fromPropertiesFile("wrong-path")
          .start();
    }

    @Test (expected = InvalidPropertyException.class)
    public void bootstrapWrongPropertiesFile()
      throws Exception
    {
        new DefaultAlmiBootstrap(mAlmiFactory)
          .fromPropertiesFile("src/test/java/test-assets/bootstrap-wrong.properties")
          .start();

        Mockito.verify(mAlmiFactory).create(
          Collections.emptyMap(),
          "thread-name",
          "localhost",
          Constants.DEFAULT_PORT,
          2000,
          1000
        );
    }
}
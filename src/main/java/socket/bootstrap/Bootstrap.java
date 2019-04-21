package socket.bootstrap;

import exceptions.AlmiException;
import socket.Almi;

import java.util.Properties;

public interface Bootstrap
{
    /**
     * @param name
     */
    Bootstrap withThreadName(String name);

    /**
     * @param address
     */
    Bootstrap withAddress(String address);

    /**
     * @param port
     */
    Bootstrap withPort(int port);

    /**
     * @param timeout
     */
    Bootstrap withConnectionTimeout(int timeout);

    /**
     * @param timeout
     */
    Bootstrap withRemoteCallTimeout(int timeout);

    /**
     * @param props
     */
    Bootstrap from(Properties props)
      throws AlmiException;

    /**
     * @param path
     */
    Bootstrap fromPropertiesFile(String path)
      throws AlmiException;

    /**
     * @param mapper
     */
    Bootstrap withMethodsMapper(MethodsMapper mapper)
      throws AlmiException;

    Almi start()
      throws AlmiException;
}

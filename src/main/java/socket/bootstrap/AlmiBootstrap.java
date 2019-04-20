package socket.bootstrap;

import exceptions.AlmiException;
import exceptions.InvalidPropertiesFileException;
import socket.Almi;

import java.io.File;
import java.util.Properties;

public interface AlmiBootstrap
{
    /**
     * @param name
     */
    AlmiBootstrap withThreadName(String name);

    /**
     * @param address
     */
    AlmiBootstrap withAddress(String address);

    /**
     * @param port
     */
    AlmiBootstrap withPort(int port);

    /**
     * @param timeout
     */
    AlmiBootstrap withConnectionTimeout(int timeout);

    /**
     * @param timeout
     */
    AlmiBootstrap withRemoteCallTimeout(int timeout);

    /**
     * @param props
     */
    AlmiBootstrap from(Properties props)
      throws AlmiException;

    /**
     * @param path
     */
    AlmiBootstrap fromPropertiesFile(String path)
      throws AlmiException;

    /**
     * @param mapper
     */
    AlmiBootstrap withMethodsMapper(MethodsMapper mapper)
      throws AlmiException;

    Almi start()
      throws AlmiException;
}

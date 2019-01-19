package socket.bootstrap;

import socket.Almi;

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
     * @param props
     */
    AlmiBootstrap from(Properties props);

    Almi start();
}

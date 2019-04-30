package com.matteobattilana.almi.socket.server;

import com.google.inject.assistedinject.Assisted;

public interface ServerSocketFactory
{
    ServerSocket create(
      @Assisted("port") int port,
      @Assisted("connectionTimeout") int connectionTimeout
    );
}

package com.matteobattilana.almi.socket.server;

import com.google.inject.assistedinject.Assisted;

public interface ServerSocketServiceFactory
{
    ServerSocketService create(
      @Assisted("port") int port,
      @Assisted("connectTimeout") int connectTimeout,
      @Assisted("threadName") String threadName
    );
}

package example;

import exceptions.AlmiException;
import socket.bootstrap.MethodsMapper;

public class MethodsMapperImpl extends MethodsMapper
{
    @Override
    public void configure()
      throws Exception
    {
        install(
          bind(new AlmiException("e")).method("toString").withName("toString"),
          bind(new AlmiException("e")).method("toString").withName("toString")
        );
    }
}

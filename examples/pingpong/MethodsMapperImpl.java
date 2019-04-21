package pingpong;

import socket.bootstrap.MethodsMapper;

public class MethodsMapperImpl extends MethodsMapper
{
    private final PingPong mPingPong;

    public MethodsMapperImpl(PingPong pingPong)
    {
        mPingPong = pingPong;
    }

    @Override
    public void configure()
      throws Exception
    {
        addMethods(
          bind(mPingPong).method("getReponse", PingPong.State.class).withName("ping")
        );
    }
}

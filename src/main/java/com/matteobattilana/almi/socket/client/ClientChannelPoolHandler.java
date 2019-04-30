package com.matteobattilana.almi.socket.client;

import com.google.inject.Inject;
import io.netty.channel.Channel;
import io.netty.channel.pool.ChannelPoolHandler;
import com.matteobattilana.almi.socket.handler.SocketChannelInitializer;

public class ClientChannelPoolHandler implements ChannelPoolHandler
{
    private final SocketChannelInitializer mSocketChannelInitializer;

    @Inject
    public ClientChannelPoolHandler(SocketChannelInitializer socketChannelInitializer)
    {
        mSocketChannelInitializer = socketChannelInitializer;
    }

    @Override
    public void channelReleased(Channel channel)
    {}

    @Override
    public void channelAcquired(Channel channel)
    {}

    @Override
    public void channelCreated(Channel channel)
    {
        channel.pipeline().addLast(mSocketChannelInitializer);
    }
}
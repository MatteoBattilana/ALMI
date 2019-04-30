package com.matteobattilana.almi.socket.server;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import com.matteobattilana.almi.socket.handler.SocketChannelInitializer;

import java.net.InetSocketAddress;

public class ServerSocket
{
    private NioEventLoopGroup mGroup;
    private ServerBootstrap   mBootstrap;

    @Inject
    public ServerSocket(
      SocketChannelInitializer socketChannelInitializer,
      @Assisted("port") int port,
      @Assisted("connectionTimeout") int connectionTimeout
    )
    {
        mGroup = new NioEventLoopGroup();
        mBootstrap = new ServerBootstrap();
        mBootstrap.group(mGroup)
          .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
          .channel(NioServerSocketChannel.class)
          .localAddress(new InetSocketAddress(port))
          .childHandler(socketChannelInitializer);
    }

    public void close()
    {
        mGroup.shutdownGracefully().syncUninterruptibly();
        System.out.println("Stopped listening!");
    }

    public void startListening()
      throws InterruptedException
    {
        ChannelFuture future = mBootstrap.bind().sync();
        System.out.println("Started listening on" + future.channel().localAddress());
        future.channel().closeFuture().sync();
    }
}

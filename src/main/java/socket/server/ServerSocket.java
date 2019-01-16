package socket.server;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import socket.handler.SocketChannelInitializer;
import utils.Constants;

import java.io.Closeable;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ServerSocket implements Closeable
{
    private NioEventLoopGroup mGroup;
    private ServerBootstrap   mBootstrap;

    @Inject
    public ServerSocket(
      @Assisted int port
    )
    {
        init(port);
    }

    private void init(int port)
    {
        mGroup = new NioEventLoopGroup();
        mBootstrap = new ServerBootstrap();
        mBootstrap.group(mGroup)
          .channel(NioServerSocketChannel.class)
          .localAddress(new InetSocketAddress(port))
          .childHandler(new SocketChannelInitializer());
    }

    @Override
    public void close()
    {
        mGroup.shutdownGracefully().syncUninterruptibly();
        System.out.println("Stopped listening!");
    }

    public void startListening()
      throws InterruptedException
    {
        ChannelFuture future = mBootstrap.bind().sync();
        System.out.println("Started listening on" + future.channel().localAddress().toString());
        future.channel().closeFuture().sync();
    }
}

package socket.server;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import socket.handler.SocketChannelInitializer;

import java.io.Closeable;
import java.net.InetSocketAddress;

public class ServerSocket implements Closeable
{

    private final SocketChannelInitializer mSocketChannelInitializer;

    private NioEventLoopGroup mGroup;
    private ServerBootstrap   mBootstrap;

    @Inject
    public ServerSocket(
      SocketChannelInitializer socketChannelInitializer,
      @Assisted int port
    )
    {
        mSocketChannelInitializer = socketChannelInitializer;
        init(port);
    }

    private void init(int port)
    {
        mGroup = new NioEventLoopGroup();
        mBootstrap = new ServerBootstrap();
        mBootstrap.group(mGroup)
          .channel(NioServerSocketChannel.class)
          .localAddress(new InetSocketAddress(port))
          .childHandler(mSocketChannelInitializer);
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
        ChannelFuture f = mBootstrap.bind().sync();
        System.out.println(ServerSocket.class.getName() + " started and listening for connections on " + f.channel()
          .localAddress());
        f.channel().closeFuture().sync();
    }
}

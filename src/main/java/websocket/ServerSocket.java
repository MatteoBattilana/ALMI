package websocket;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.Closeable;
import java.net.InetSocketAddress;

public class ServerSocket implements Closeable
{
    private final int            mPort;
    private final InboundHandler mInboundHandler;

    private NioEventLoopGroup mGroup;

    @Inject
    public ServerSocket(
      InboundHandler inboundHandler,
      @Assisted int port
    )
    {
        mInboundHandler = inboundHandler;
        mPort = port;
    }

    @Override
    public void close()
    {
        // mGroup.shutdownGracefully().sync();
    }

    public void startListening()
      throws InterruptedException
    {
        mGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(mGroup)
          .channel(NioServerSocketChannel.class)
          .localAddress(new InetSocketAddress(mPort))
          .childHandler(new ChannelInitializer<SocketChannel>()
          {
              @Override
              public void initChannel(SocketChannel ch)
              {
                  ch.pipeline().addLast(mInboundHandler);
              }
          });

        ChannelFuture f = b.bind().sync();
        System.out.println(ServerSocket.class.getName() + " started and listening for connections on " + f.channel()
          .localAddress());
        f.channel().closeFuture().sync();
        System.out.println("Stopped listening!");
    }
}

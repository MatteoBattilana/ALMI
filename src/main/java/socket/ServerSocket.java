package socket;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import socket.handler.ExceptionHandler;
import socket.handler.InboundHandler;
import socket.handler.MessageDecoderFactory;
import socket.handler.MessageEncoder;
import socket.handler.WelcomeHandler;

import java.io.Closeable;
import java.net.InetSocketAddress;

public class ServerSocket implements Closeable
{
    private final MessageDecoderFactory mMessageDecoderFactory;
    private final int                   mPort;
    private final InboundHandler        mInboundHandler;

    private NioEventLoopGroup mGroup;

    @Inject
    public ServerSocket(
      InboundHandler inboundHandler,
      MessageDecoderFactory messageDecoderFactory,
      @Assisted int port
    )
    {
        mInboundHandler = inboundHandler;
        mMessageDecoderFactory = messageDecoderFactory;
        mPort = port;
    }

    @Override
    public void close()
    {
        try
        {
            mGroup.shutdownGracefully().sync();
        }
        catch(InterruptedException ignored)
        {
        }
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
                  ch.pipeline().addLast(new MessageEncoder());
                  ch.pipeline().addLast(mMessageDecoderFactory.create());
                  ch.pipeline().addLast(new WelcomeHandler());
                  ch.pipeline().addLast(mInboundHandler);
                  ch.pipeline().addLast(new ExceptionHandler());
              }
          });

        ChannelFuture f = b.bind().sync();
        System.out.println(ServerSocket.class.getName() + " started and listening for connections on " + f.channel()
          .localAddress());
        f.channel().closeFuture().sync();
        System.out.println("Stopped listening!");
    }
}

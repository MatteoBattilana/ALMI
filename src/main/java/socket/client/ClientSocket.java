package socket.client;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import message.BaseMessage;
import message.Message;
import socket.handler.SocketChannelInitializer;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;

public class ClientSocket implements Closeable
{
    private final SocketChannelInitializer mSocketChannelInitializer;

    private NioEventLoopGroup mGroup;
    private Bootstrap         mBootstrap;
    private ChannelFuture     mChannelFuture;

    @Inject
    public ClientSocket(
      SocketChannelInitializer socketChannelInitializer,
      @Assisted String host,
      @Assisted int port
    )
    {
        mSocketChannelInitializer = socketChannelInitializer;
        init(host, port);
    }

    private void init(String host, int port)
    {
        mGroup = new NioEventLoopGroup();
        mBootstrap = new Bootstrap();
        mBootstrap.group(mGroup)
          .channel(NioSocketChannel.class)
          .remoteAddress(new InetSocketAddress(host, port))
          .handler(mSocketChannelInitializer);
    }

    public void connect()
      throws InterruptedException
    {
        mChannelFuture = mBootstrap.connect().sync();
        System.out.println("Connected to: " + mChannelFuture.channel().remoteAddress());
        mChannelFuture.channel().closeFuture().sync();
    }

    @Override
    public void close()
    {
        mGroup.shutdownGracefully().syncUninterruptibly();
    }

    public void writeMessage(Serializable message)
    {
        mChannelFuture.channel().writeAndFlush(message);
    }
}

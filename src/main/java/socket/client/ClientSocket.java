package socket.client;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Promise;
import message.BaseMessage;
import message.Message;
import socket.handler.MessageInboundHandler;
import socket.handler.SocketChannelInitializer;
import utils.Constants;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.concurrent.Future;

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
          .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Constants.SOCKET_TIMEOUT)
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

    public Promise<Serializable> writeMessage(BaseMessage message)
    {
        return mChannelFuture.channel().pipeline().get(MessageInboundHandler.class).sendMessage(message);
    }
}

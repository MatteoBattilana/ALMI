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
        List<Integer> ports = Arrays.asList(Constants.MESSAGE_PORT, Constants.STREAM_PORT);
        Collection<Channel> channels = new ArrayList<>(ports.size());
        for (int port : ports) {
            Channel serverChannel = mBootstrap.bind(port).sync().channel();
            System.out.println("Started listen on: " + port);
            channels.add(serverChannel);
        }
        for (Channel ch : channels) {
            ch.closeFuture().sync();
        }
    }
}

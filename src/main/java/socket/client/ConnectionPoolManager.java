package socket.client;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.SimpleChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import message.MethodCallRequest;
import socket.handler.PromisesManager;

import java.io.Serializable;
import java.net.InetSocketAddress;

public class ConnectionPoolManager
{
    private final ClientChannelPoolHandler mClientChannelPoolHandler;
    private final PromisesManager          mPromisesManager;

    private ChannelPoolMap<InetSocketAddress, SimpleChannelPool> mConnectionPool;
    private Bootstrap                                            mBootstrap;

    @Inject
    public ConnectionPoolManager(
      ClientChannelPoolHandler clientChannelPoolHandler,
      PromisesManager promisesManager,
      @Assisted int connectionTimeout
    )
    {
        mClientChannelPoolHandler = clientChannelPoolHandler;
        mPromisesManager = promisesManager;
        init(connectionTimeout);
    }

    private void init(int connectionTimeout)
    {
        NioEventLoopGroup group = new NioEventLoopGroup();
        mBootstrap = new Bootstrap();
        mBootstrap.group(group)
          .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
          .channel(NioSocketChannel.class);

        mConnectionPool = new AbstractChannelPoolMap<InetSocketAddress, SimpleChannelPool>()
        {
            @Override
            protected SimpleChannelPool newPool(InetSocketAddress key)
            {
                return new SimpleChannelPool(mBootstrap.remoteAddress(key), mClientChannelPoolHandler);
            }
        };
    }

    public Promise<Serializable> callMethod(InetSocketAddress address, MethodCallRequest request)
    {
        Promise<Serializable> promise = new DefaultEventLoop().newPromise();
        final SimpleChannelPool pool = mConnectionPool.get(address);
        Future<Channel> future = pool.acquire();
        future.addListener((FutureListener<Channel>) f ->
        {
            if(f.isSuccess())
            {
                Channel ch = f.getNow();
                mPromisesManager.put(request.getId(), promise);
                ch.writeAndFlush(request);
                pool.release(ch);
            }
        });

        return promise;
    }
}
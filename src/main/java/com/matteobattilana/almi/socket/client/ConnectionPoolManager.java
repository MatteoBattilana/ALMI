package com.matteobattilana.almi.socket.client;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.SimpleChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.Promise;
import com.matteobattilana.almi.message.MethodCallRequest;
import com.matteobattilana.almi.message.MethodCallResponse;
import com.matteobattilana.almi.socket.handler.PromisesManager;

import java.net.InetSocketAddress;

public class ConnectionPoolManager
{
    private final ClientChannelPoolHandler mClientChannelPoolHandler;
    private final PromisesManager          mPromisesManager;

    private ChannelPoolMap<InetSocketAddress, SimpleChannelPool> mConnectionPool;
    private Bootstrap                                            mBootstrap;
    private NioEventLoopGroup                                    mGroup;

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
        mGroup = new NioEventLoopGroup();
        mBootstrap = new Bootstrap();
        mBootstrap.group(mGroup)
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

    public void close()
    {
        mGroup.shutdownGracefully().syncUninterruptibly();
    }

    public Promise<MethodCallResponse> callMethod(InetSocketAddress address, MethodCallRequest request)
    {
        Promise<MethodCallResponse> promise = GlobalEventExecutor.INSTANCE.newPromise();
        mPromisesManager.put(request.getId(), promise);
        final SimpleChannelPool pool = mConnectionPool.get(address);
        Future<Channel> future = pool.acquire();
        future.addListener((FutureListener<Channel>) f ->
        {
            if(f.isSuccess())
            {
                Channel ch = f.getNow();
                ch.writeAndFlush(request);
                pool.release(ch);
            }
            else
            {
                promise.setFailure(f.cause());
            }
        });

        return promise;
    }
}
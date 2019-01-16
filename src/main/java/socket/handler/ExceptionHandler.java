package socket.handler;

import exceptions.HandshakeException;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import message.request.ErrorMessageRequest;

public class ExceptionHandler extends ChannelDuplexHandler
{
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        ctx.writeAndFlush(new ErrorMessageRequest(cause));
        if(cause instanceof HandshakeException)
        {
            ctx.close();
        }
    }

    // @Override
    // public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
    //     ctx.connect(remoteAddress, localAddress, promise.addListener(new ChannelFutureListener() {
    //         @Override
    //         public void operationComplete(ChannelFuture future) {
    //             if (!future.isSuccess()) {
    //                 // Handle connect exception here...
    //             }
    //         }
    //     }));
    // }
    //
    // @Override
    // public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
    //     ctx.write(msg, promise.addListener(new ChannelFutureListener() {
    //         @Override
    //         public void operationComplete(ChannelFuture future) {
    //             if (!future.isSuccess()) {
    //                 // Handle write exception here...
    //             }
    //         }
    //     }));
    // }
}

package socket.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.stream.ChunkedStream;
import message.request.StreamRequest;

import java.io.File;
import java.io.FileInputStream;

@ChannelHandler.Sharable
public class StreamInboundHandler extends SimpleChannelInboundHandler<StreamRequest>
{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StreamRequest streamRequest)
      throws Exception
    {
        FileInputStream byteArrayInputStream = new FileInputStream((new File("/tmp/test")));
        ctx.writeAndFlush(streamRequest);
        ChunkedStream chunkedStream = new ChunkedStream(byteArrayInputStream, 8848);
        ctx.writeAndFlush(chunkedStream);
    }
}

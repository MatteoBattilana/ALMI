package message;

import io.netty.channel.ChannelHandlerContext;

public interface MessageInterpreter<T>
{
    T interpret(MethodCallResponse methodCallResponse, ChannelHandlerContext ctx)
      throws Exception;
    T interpret(MethodCallRequest methodCallRequest, ChannelHandlerContext ctx)
      throws Exception;
    T interpret(ErrorMessage errorMessage, ChannelHandlerContext ctx);
}

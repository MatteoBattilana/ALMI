package message;

import exceptions.AlmiException;
import io.netty.channel.ChannelHandlerContext;

public interface MessageInterpreter<T>
{
    T interpret(MethodCallResponse methodCallResponse, ChannelHandlerContext ctx);
    T interpret(MethodCallRequest methodCallRequest, ChannelHandlerContext ctx)
      throws AlmiException;
    T interpret(ErrorMessage errorMessage, ChannelHandlerContext ctx);
}

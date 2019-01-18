package socket.handler;

import com.google.inject.Inject;
import exceptions.AlmiException;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import message.BaseMessage;
import message.ErrorMessage;
import message.MessageInterpreter;
import message.MethodCallRequest;
import message.MethodCallResponse;
import method.MethodsManager;

import java.io.Serializable;

@ChannelHandler.Sharable
public class MessageInboundHandler extends SimpleChannelInboundHandler<BaseMessage> implements MessageInterpreter<Void>
{
    private final MethodsManager mMethodsManager;

    @Inject
    public MessageInboundHandler(
      MethodsManager methodsManager
    )
    {
        mMethodsManager = methodsManager;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMessage in)
      throws AlmiException
    {
        try
        {
            in.interpret(this, ctx);
        }
        finally
        {
            ReferenceCountUtil.release(in);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx)
    {
        System.out.println("Closed connection!");
        ctx.fireChannelInactive();
    }

    @Override
    public Void interpret(MethodCallResponse methodCallResponse, ChannelHandlerContext ctx)
    {
        System.out.println(methodCallResponse.getReturnValue());
        return null;
    }

    @Override
    public Void interpret(MethodCallRequest methodCallRequest, ChannelHandlerContext ctx) throws AlmiException
    {
        Serializable results = mMethodsManager.execute(
          methodCallRequest.getMethodName(),
          methodCallRequest.getMethodParameters()
        );
        ctx.writeAndFlush(results);
        
        return null;
    }

    @Override
    public Void interpret(ErrorMessage errorMessage, ChannelHandlerContext ctx)
    {
        System.out.println(errorMessage.getThrowable().getMessage());
        return null;
    }
}

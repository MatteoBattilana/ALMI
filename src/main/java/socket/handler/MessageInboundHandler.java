package socket.handler;

import com.google.inject.Inject;
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
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BaseMessage in)
    {
        try
        {
            in.interpret(this);
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
    public Void interpret(MethodCallResponse methodCallResponse)
    {
        System.out.println(methodCallResponse.getReturnValue());
        return null;
    }

    @Override
    public Void interpret(MethodCallRequest methodCallRequest)
    {
        System.out.println(methodCallRequest.getMethodName());
        return null;
    }

    @Override
    public Void interpret(ErrorMessage errorMessage)
    {
        System.out.println(errorMessage.getThrowable().getMessage());
        return null;
    }
}

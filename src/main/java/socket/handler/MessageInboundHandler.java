package socket.handler;

import com.google.inject.Inject;
import exceptions.AlmiException;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import message.BaseMessage;
import message.MethodCallRequest;
import message.MethodCallResponse;
import method.MethodsManager;

@ChannelHandler.Sharable
public class MessageInboundHandler extends SimpleChannelInboundHandler<BaseMessage>
{
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BaseMessage stupidClass) throws Exception
    {
        try
        {
            System.out.println("Received " + stupidClass.getClass());
            if(stupidClass instanceof MethodCallRequest)
            {
                MethodCallRequest call = (MethodCallRequest) stupidClass;

                MethodsManager methodsManager = new MethodsManager();
                Arithmetic arithmetic = new Arithmetic();
                methodsManager.addMethod(arithmetic, Arithmetic.class.getMethod("sum", int.class, String.class), "sum");
                Object result = methodsManager.execute(call.getMethodName(), call.getMethodParameters().toArray(new Object[0]));
                channelHandlerContext.writeAndFlush(new MethodCallResponse("uuid", result));
            }
            else if(stupidClass instanceof MethodCallResponse)
            {
                System.out.println(((MethodCallResponse) stupidClass).getReturnValue());
            }
        }
        finally
        {
            ReferenceCountUtil.release(stupidClass);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx)
    {
        System.out.println("Closed connection!");
        ctx.fireChannelInactive();
    }
}

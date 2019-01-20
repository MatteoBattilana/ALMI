package socket.handler;

import com.google.inject.Inject;
import exceptions.AlmiException;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Promise;
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
    private final MethodsManager  mMethodsManager;
    private final PromisesManager mPromisesManager;

    @Inject
    public MessageInboundHandler(
      MethodsManager methodsManager,
      PromisesManager promisesManager
    )
    {
        mMethodsManager = methodsManager;
        mPromisesManager = promisesManager;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx)
      throws Exception
    {
        System.out.println("Connected!");
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMessage in)
    {
        try
        {
            System.out.println("Received: " + in.getClass());
            in.interpret(this, ctx);
        }
        catch(Exception e)
        {
            ctx.writeAndFlush(new ErrorMessage(in.getId(), e));
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
        Promise<Serializable> returnPromise = mPromisesManager.get(methodCallResponse.getId());
        if(returnPromise != null)
        {
            returnPromise.setSuccess(methodCallResponse.getReturnValue());
        }

        return null;
    }

    @Override
    public Void interpret(MethodCallRequest methodCallRequest, ChannelHandlerContext ctx)
      throws Exception
    {
        Serializable results = mMethodsManager.execute(
          methodCallRequest.getMethodName(),
          methodCallRequest.getMethodParameters()
        );
        ctx.writeAndFlush(new MethodCallResponse<>(methodCallRequest.getId(), results));

        return null;
    }

    @Override
    public Void interpret(ErrorMessage errorMessage, ChannelHandlerContext ctx)
    {
        Promise<Serializable> returnValue = mPromisesManager.get(errorMessage.getId());
        if(returnValue != null)
        {
            returnValue.setFailure(errorMessage.getThrowable());
        }

        return null;
    }
}

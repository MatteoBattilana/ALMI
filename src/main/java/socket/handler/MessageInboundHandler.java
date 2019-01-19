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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@ChannelHandler.Sharable
public class MessageInboundHandler extends SimpleChannelInboundHandler<BaseMessage> implements MessageInterpreter<Void>
{
    private final MethodsManager                       mMethodsManager;
    private final BlockingQueue<Promise<Serializable>> mPromisesQueue;

    private ChannelHandlerContext mCtx;

    @Inject
    public MessageInboundHandler(
      MethodsManager methodsManager
    )
    {
        mMethodsManager = methodsManager;
        mPromisesQueue = new LinkedBlockingDeque<>();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx)
      throws Exception
    {
        super.channelActive(ctx);
        mCtx = ctx;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, BaseMessage in)
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
        mPromisesQueue.poll().setSuccess(methodCallResponse.getReturnValue());
        return null;
    }

    @Override
    public Void interpret(MethodCallRequest methodCallRequest, ChannelHandlerContext ctx)
      throws AlmiException
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
        System.out.println(errorMessage.getThrowable().getMessage());
        mPromisesQueue.poll().setFailure(errorMessage.getThrowable());
        return null;
    }

    public Promise<Serializable> sendMessage(BaseMessage message)
    {
        Promise<Serializable> promise = mCtx.channel().eventLoop().newPromise();
        mPromisesQueue.offer(promise);
        mCtx.writeAndFlush(message);

        return promise;
    }
}

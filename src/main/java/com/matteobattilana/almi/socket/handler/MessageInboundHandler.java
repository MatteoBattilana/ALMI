package com.matteobattilana.almi.socket.handler;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Promise;
import com.matteobattilana.almi.message.BaseMessage;
import com.matteobattilana.almi.message.ErrorMessage;
import com.matteobattilana.almi.message.MessageInterpreter;
import com.matteobattilana.almi.message.MethodCallRequest;
import com.matteobattilana.almi.message.MethodCallResponse;
import com.matteobattilana.almi.method.MethodsManager;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

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
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMessage in)
    {
        try
        {
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
        ctx.fireChannelInactive();
    }

    @Override
    public Void interpret(MethodCallResponse methodCallResponse, ChannelHandlerContext ctx)
    {
        Promise<MethodCallResponse> returnPromise = mPromisesManager.get(methodCallResponse.getId());
        if(returnPromise != null)
        {
            returnPromise.setSuccess(methodCallResponse);
        }

        return null;
    }

    @Override
    public Void interpret(MethodCallRequest methodCallRequest, ChannelHandlerContext ctx)
      throws Exception
    {
        try
        {
            Serializable results = mMethodsManager.execute(
              methodCallRequest.getMethodName(),
              methodCallRequest.getMethodParameters()
            );
            ctx.writeAndFlush(new MethodCallResponse<>(methodCallRequest.getId(), results));
        }
        catch(InvocationTargetException e)
        {
            ctx.writeAndFlush(new MethodCallResponse<>(methodCallRequest.getId(), e.getCause()));
        }

        return null;
    }

    @Override
    public Void interpret(ErrorMessage errorMessage, ChannelHandlerContext ctx)
    {
        Promise<MethodCallResponse> returnValue = mPromisesManager.get(errorMessage.getId());
        if(returnValue != null)
        {
            returnValue.setFailure(errorMessage.getThrowable());
        }

        return null;
    }
}

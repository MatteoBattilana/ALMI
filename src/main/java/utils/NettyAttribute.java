package utils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

public class NettyAttribute
{
    public static <T> T get(ChannelHandlerContext ctx, AttributeKey<T> attributeKey, T def)
    {
        if(ctx.hasAttr(attributeKey))
        {
            return ctx.attr(attributeKey).get();
        }
        return def;
    }

    public static <T> T set(ChannelHandlerContext ctx, AttributeKey<T> attributeKey, T value)
    {
        ctx.attr(attributeKey).set(value);
        return value;
    }

    public static <T> T getAndSet(ChannelHandlerContext ctx, AttributeKey<T> attributeKey, T value)
    {
        return ctx.attr(attributeKey).getAndSet(value);
    }
}

package com.matteobattilana.almi.socket.handler;

import com.google.inject.Singleton;
import io.netty.util.concurrent.Promise;
import com.matteobattilana.almi.message.MethodCallResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class PromisesManager
{
    private final Map<String, Promise<MethodCallResponse>> mReturnValuePromises = new ConcurrentHashMap<>();

    public Promise<MethodCallResponse> get(String key)
    {
        return mReturnValuePromises.get(key);
    }

    public Promise<MethodCallResponse> put(String key, Promise<MethodCallResponse> promise)
    {
        return mReturnValuePromises.put(key, promise);
    }

    public void cancelPromise(String key)
    {
        mReturnValuePromises.remove(key);
    }
}
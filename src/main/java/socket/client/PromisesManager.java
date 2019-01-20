package socket.client;

import com.google.inject.Singleton;
import io.netty.util.concurrent.Promise;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class PromisesManager
{
    private final Map<String, Promise<Serializable>> mReturnValuePromises = new ConcurrentHashMap<>();

    public Promise<Serializable> get(String key)
    {
        return mReturnValuePromises.get(key);
    }

    public Promise<Serializable> put(String key, Promise<Serializable> promise)
    {
        return mReturnValuePromises.put(key, promise);
    }
}
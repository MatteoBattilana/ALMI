package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.JsonGenerationException;
import exceptions.MissingKeyException;

import java.util.HashMap;

public class SimpleContainer implements Container<String>
{
    private final static ObjectMapper sObjectMapper = new ObjectMapper();

    private final HashMap<String, Object> mMap;

    public SimpleContainer()
    {
        mMap = new HashMap<>();
    }

    @Override
    public void put(String key, Object value)
    {
        mMap.put(key, value);
    }

    @Override
    public boolean isEmpty()
    {
        return mMap.isEmpty();
    }

    @Override
    public boolean has(String key)
    {
        return mMap.containsKey(key);
    }

    @Override
    public String getString(String key)
      throws MissingKeyException
    {
        if(has(key))
        {
            return (String) mMap.get(key);
        }
        throw new MissingKeyException(key);
    }

    @Override
    public String optString(String key, String def)
    {
        try
        {
            return getString(key);
        }
        catch(MissingKeyException e)
        {
            return def;
        }
    }

    @Override
    public int getInt(String key)
      throws MissingKeyException
    {
        if(has(key))
        {
            return (int) mMap.get(key);
        }
        throw new MissingKeyException(key);
    }

    @Override
    public int optInt(String key, int def)
    {
        try
        {
            return getInt(key);
        }
        catch(MissingKeyException e)
        {
            return def;
        }
    }

    @Override
    public long getLong(String key)
      throws MissingKeyException
    {
        if(has(key))
        {
            return (long) mMap.get(key);
        }
        throw new MissingKeyException(key);
    }

    @Override
    public long optLong(String key, Long def)
    {
        try
        {
            return getLong(key);
        }
        catch(MissingKeyException e)
        {
            return def;
        }
    }

    @Override
    public double getDouble(String key)
      throws MissingKeyException
    {
        if(has(key))
        {
            return (double) mMap.get(key);
        }
        throw new MissingKeyException(key);
    }

    @Override
    public double optDouble(String key, double def)
    {
        try
        {
            return getDouble(key);
        }
        catch(MissingKeyException e)
        {
            return def;
        }
    }

    @Override
    public String toJSON()
      throws JsonGenerationException
    {
        try
        {
            return sObjectMapper.writeValueAsString(mMap);
        }
        catch(JsonProcessingException e)
        {
            throw new JsonGenerationException(e);
        }
    }
}

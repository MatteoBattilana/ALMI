package com.matteobattilana.almi.utils;

import com.matteobattilana.almi.exceptions.AlmiException;
import com.matteobattilana.almi.exceptions.InvalidPropertyException;
import com.matteobattilana.almi.exceptions.MissingPropertyException;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

public class PropertiesUtils
{
    public static int getInt(Properties properties, String key)
      throws AlmiException
    {
        if(properties.getProperty(key) != null)
        {
            String value = properties.getProperty(key);
            if(StringUtils.isNotBlank(value) && StringUtils.isNumeric(value))
            {
                return Integer.valueOf(value);
            }
            throw new InvalidPropertyException(key);
        }
        throw new MissingPropertyException(key);
    }

    public static int optInt(Properties properties, String key, int def)
      throws AlmiException
    {
        try
        {
            return getInt(properties, key);
        }
        catch(MissingPropertyException e)
        {
            return def;
        }
    }

    public static boolean getBoolean(Properties properties, String key)
      throws AlmiException
    {
        if(properties.getProperty(key) != null)
        {
            String value = properties.getProperty(key);
            if(StringUtils.isNotBlank(value) && value.equalsIgnoreCase("false") || value.equalsIgnoreCase("true"))
            {
                return Boolean.valueOf(value);
            }
            throw new InvalidPropertyException(key);
        }
        throw new MissingPropertyException(key);
    }

    public static boolean optBoolean(Properties properties, String key, boolean def)
      throws AlmiException
    {
        try
        {
            return getBoolean(properties, key);
        }
        catch(MissingPropertyException e)
        {
            return def;
        }
    }

    public static String getString(Properties properties, String key)
      throws MissingPropertyException
    {
            String value = properties.getProperty(key);
            if(StringUtils.isNotBlank(value))
            {
                return value;
            }
            throw new MissingPropertyException(key);
    }

    public static String optString(Properties properties, String key, String def)
    {
        try
        {
            return getString(properties, key);
        }
        catch(MissingPropertyException e)
        {
            return def;
        }
    }
}

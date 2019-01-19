package utils;

import exceptions.InvalidPropertyException;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

public class PropertiesUtils
{
    public static int getInt(Properties properties, String key)
      throws InvalidPropertyException
    {
        String value = properties.getProperty(key);
        if(StringUtils.isNotBlank(value) && StringUtils.isNumeric(value))
        {
            return Integer.valueOf(value);
        }
        throw new InvalidPropertyException(key);
    }

    public static int optInt(Properties properties, String key, int def)
    {
        try
        {
            return getInt(properties, key);
        }
        catch(InvalidPropertyException e)
        {
            return def;
        }
    }

    public static boolean getBoolean(Properties properties, String key)
      throws InvalidPropertyException
    {
        String value = properties.getProperty(key);
        if(StringUtils.isNotBlank(value))
        {
            return Boolean.valueOf(value);
        }
        throw new InvalidPropertyException(key);
    }

    public static boolean optBoolean(Properties properties, String key, boolean def)
    {
        try
        {
            return getBoolean(properties, key);
        }
        catch(InvalidPropertyException e)
        {
            return def;
        }
    }

    public static String getString(Properties properties, String key)
      throws InvalidPropertyException
    {
        String value = properties.getProperty(key);
        if(StringUtils.isNotBlank(value))
        {
            return value;
        }
        throw new InvalidPropertyException(key);
    }

    public static String optString(Properties properties, String key, String def)
    {
        try
        {
            return getString(properties, key);
        }
        catch(InvalidPropertyException e)
        {
            return def;
        }
    }
}

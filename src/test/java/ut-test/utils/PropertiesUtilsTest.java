package utils;

import exceptions.AlmiException;
import exceptions.InvalidPropertyException;
import exceptions.MissingPropertyException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

public class PropertiesUtilsTest
{
    private Properties mProperties;

    @Before
    public void setup()
    {
        mProperties = new Properties();
        mProperties.put("key1", "1");
        mProperties.put("key2", "hello!");
        mProperties.put("key3", "false");
        mProperties.put("key4", "");
    }

    @Test
    public void propertiesBoolean() throws Exception
    {
        boolean val1 = PropertiesUtils.getBoolean(mProperties, "key3");
        Assert.assertFalse(val1);

        boolean val2 = PropertiesUtils.optBoolean(mProperties, "missing-key", true);
        Assert.assertTrue(val2);

        try
        {
            PropertiesUtils.getBoolean(mProperties, "key1");
            fail();
        }
        catch(InvalidPropertyException ignored) {}

        try
        {
            PropertiesUtils.optBoolean(mProperties, "key2", true);
            fail();
        }
        catch(InvalidPropertyException ignored) {}

        try
        {
            PropertiesUtils.getBoolean(mProperties, "missing-key");
            fail();
        }
        catch(MissingPropertyException ignored) {}
    }

    @Test
    public void propertiesInteger() throws Exception
    {
        int val1 = PropertiesUtils.getInt(mProperties, "key1");
        Assert.assertEquals(1, val1);

        int val2 = PropertiesUtils.optInt(mProperties, "missing-key", 2);
        Assert.assertEquals(2, val2);

        try
        {
            PropertiesUtils.getInt(mProperties, "key3");
            fail();
        }
        catch(InvalidPropertyException ignored) {}

        try
        {
            PropertiesUtils.optInt(mProperties, "key2", 2);
            fail();
        }
        catch(InvalidPropertyException ignored) {}

        try
        {
            PropertiesUtils.getBoolean(mProperties, "missing-key");
            fail();
        }
        catch(MissingPropertyException ignored) {}
    }

    @Test
    public void propertiesString() throws Exception
    {
        String val1 = PropertiesUtils.getString(mProperties, "key2");
        Assert.assertEquals("hello!", val1);

        String val2 = PropertiesUtils.optString(mProperties, "missing-key", "ciao!");
        Assert.assertEquals("ciao!", val2);

        try
        {
            PropertiesUtils.getString(mProperties, "key4");
            fail();
        }
        catch(AlmiException ignored) {}
    }
}
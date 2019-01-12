package utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SimpleContainerTest
{
    @Test
    public void testJSON()
      throws Exception
    {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        SimpleContainer simpleContainer = new SimpleContainer();
        simpleContainer.put("key1", "value1");
        simpleContainer.put("key2", 2);
        simpleContainer.put("key3", list);

        Assert.assertEquals(
          "{\"key1\":\"value1\",\"key2\":2,\"key3\":[\"1\",\"2\"]}",
          simpleContainer.toJSON()
        );
    }
}
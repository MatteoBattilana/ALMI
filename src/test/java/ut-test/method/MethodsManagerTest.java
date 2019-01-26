package method;

import exceptions.MissingMethodException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import testUtils.Arithmetic;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MethodsManagerTest {

    private MethodsManager mMethodsManager;

    @Before
    public void setup()
    {
        mMethodsManager = new MethodsManager();
    }

    @Test (expected = MissingMethodException.class)
    public void missingMethod() throws Exception
    {
        mMethodsManager.execute("toString", Collections.emptyList());
    }

    @Test
    public void methodCall() throws Exception
    {
        Map<String, MethodDescriptor> map = new HashMap<>();
        Arithmetic arithmeticInstance = new Arithmetic();
        map.put("toString", new MethodDescriptor(arithmeticInstance, Arithmetic.class.getMethod("toString"), "toString"));
        map.put("randomName", new MethodDescriptor(arithmeticInstance, Arithmetic.class.getMethod("sum", int.class, int.class), "randomName"));
        mMethodsManager.addAll(map);

        Serializable results = mMethodsManager.execute("toString", Collections.emptyList());
        Assert.assertEquals(arithmeticInstance.toString(), results);

        Serializable sum = mMethodsManager.execute("randomName", Arrays.asList(2, 4));
        Assert.assertEquals(arithmeticInstance.sum(2, 4), sum);
    }
}
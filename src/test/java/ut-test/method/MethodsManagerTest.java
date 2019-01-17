package method;

import exceptions.MethodAlreadyExistsException;
import exceptions.MissingMethodException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import testUtils.Arithmetic;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;

public class MethodsManagerTest {

    private MethodsManager mMethodsManager;

    @Before
    public void setup()
    {
        mMethodsManager = new MethodsManager();
    }

    @Test (expected = MethodAlreadyExistsException.class)
    public void addMethodsError() throws Exception
    {
        mMethodsManager.addMethod(new Arithmetic(), Arithmetic.class.getMethod("sum", int.class, int.class), "sum");
        mMethodsManager.addMethod(new Arithmetic(), Arithmetic.class.getMethod("sum", int.class, int.class), "sum");
    }

    @Test (expected = MissingMethodException.class)
    public void missingMethod() throws Exception
    {
        mMethodsManager.execute("toString", Collections.emptyList());
    }

    @Test
    public void methodCall() throws Exception
    {
        Arithmetic arithmeticInstance = new Arithmetic();
        mMethodsManager.addMethod(arithmeticInstance, Arithmetic.class.getMethod("toString"), "toString");
        mMethodsManager.addMethod(arithmeticInstance, Arithmetic.class.getMethod("sum", int.class, int.class), "randomName");

        Serializable results = mMethodsManager.execute("toString", Collections.emptyList());
        Assert.assertEquals(arithmeticInstance.toString(), results);

        Serializable sum = mMethodsManager.execute("randomName", Arrays.asList(2, 4));
        Assert.assertEquals(arithmeticInstance.sum(2, 4), sum);
    }
}
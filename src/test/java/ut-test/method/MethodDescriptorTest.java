package method;

import org.junit.Assert;
import org.junit.Test;
import testUtils.Arithmetic;

public class MethodDescriptorTest
{

    @Test
    public void testEquals() throws Exception
    {
        MethodDescriptor methodDescriptor = new MethodDescriptor(new Arithmetic(), Arithmetic.class.getMethod("sum", int.class, int.class), "sum");
        MethodDescriptor methodDescriptor1 = new MethodDescriptor(new Arithmetic(), Arithmetic.class.getMethod("sum", int.class, int.class), "sum");
        MethodDescriptor methodDescriptor2 = new MethodDescriptor(new Arithmetic(), Arithmetic.class.getMethod("toString"), "toString");

        Assert.assertEquals(methodDescriptor, methodDescriptor1);
        Assert.assertNotEquals(methodDescriptor, methodDescriptor2);

        Assert.assertEquals("sum", methodDescriptor.getMethod().getName());
        Assert.assertEquals("sum", methodDescriptor.getRemoteName());
    }
}
package method;

import org.junit.Assert;
import org.junit.Test;
import testUtils.Arithmetic;

public class MethodHandlerTest {

    @Test
    public void testEquals() throws Exception
    {
        MethodHandler methodHandler = new MethodHandler(new Arithmetic(), Arithmetic.class.getMethod("sum", int.class, int.class), "sum");
        MethodHandler methodHandler1 = new MethodHandler(new Arithmetic(), Arithmetic.class.getMethod("sum", int.class, int.class), "sum");
        MethodHandler methodHandler2 = new MethodHandler(new Arithmetic(), Arithmetic.class.getMethod("toString"), "toString");

        Assert.assertEquals(methodHandler, methodHandler1);
        Assert.assertNotEquals(methodHandler, methodHandler2);

        Assert.assertEquals("sum", methodHandler.getMethod().getName());
        Assert.assertEquals("sum", methodHandler.getRemoteName());
    }
}
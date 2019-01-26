package socket.bootstrap;

import exceptions.MethodAlreadyExistsException;
import org.junit.Assert;
import org.junit.Test;
import testUtils.Arithmetic;

public class MethodsMapperTest
{
    @Test
    public void addMethod() throws Exception
    {
        MethodsMapper methodsMapper = new MethodsMapper()
        {
            @Override
            public void configure()
              throws Exception
            {
                addMethods(bind(new Arithmetic()).method("sum", int.class, int.class).withName("sum"),
                  bindStatic(Arithmetic.class).method("toLowerCase", String.class).withDefaultName());
            }
        };
        methodsMapper.configure();
        Assert.assertEquals(2, methodsMapper.methodDescriptorMap().size());
    }

    @Test (expected = MethodAlreadyExistsException.class)
    public void duplicatedWithSameName() throws Exception
    {
        MethodsMapper methodsMapper = new MethodsMapper()
        {
            @Override
            public void configure()
              throws Exception
            {
                addMethods(bind(new Arithmetic()).method("sum", int.class, int.class).withName("sum"));
                addMethods(bind(new Arithmetic()).method("sum", int.class, int.class).withName("sum"));
            }
        };
        methodsMapper.configure();
        Assert.assertEquals(1, methodsMapper.methodDescriptorMap().size());
    }

    @Test
    public void duplicatedWithDifferentName() throws Exception
    {
        MethodsMapper methodsMapper = new MethodsMapper()
        {
            @Override
            public void configure()
              throws Exception
            {
                addMethods(bind(new Arithmetic()).method("sum", int.class, int.class).withName("sum"));
                addMethods(bind(new Arithmetic()).method("sum", int.class, int.class).withName("sum1"));
            }
        };
        methodsMapper.configure();
        Assert.assertEquals(2, methodsMapper.methodDescriptorMap().size());
    }

    @Test (expected = NoSuchMethodException.class)
    public void methodNotFount() throws Exception
    {
        MethodsMapper methodsMapper = new MethodsMapper()
        {
            @Override
            public void configure()
              throws Exception
            {
                addMethods(bind(new Arithmetic()).method("sum2", int.class, int.class).withName("sum"));
            }
        };
        methodsMapper.configure();
    }
}
package method;

import org.junit.Assert;
import org.junit.Test;
import testUtils.Person;
import utils.TypeUtils;

public class TypeUtilsTest {

    @Test
    public void fromStringToObject() throws Exception
    {
        Person person = new Person("Matteo", 23);
        person.addChild(new Person("Luca", 7));
        person.addChild(new Person("Maria", 10));
        String serialized = TypeUtils.toString(person);
        Object o = TypeUtils.fromString(serialized);

        Assert.assertTrue(o instanceof Person);
        Assert.assertEquals(o, person);
    }
}

package ut.testUtils;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class Arithmetic implements Serializable
{
    public int sum(int a1, int a2)
    {
        return a1 + a2;
    }

    public static void toLowerCase(String s)
    {
        StringUtils.lowerCase(s);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString()
    {
        return "test";
    }
}

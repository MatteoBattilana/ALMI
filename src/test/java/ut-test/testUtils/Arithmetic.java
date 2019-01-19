package testUtils;

import java.io.InputStream;
import java.io.Serializable;

public class Arithmetic implements Serializable
{
    public int sum(int a1, int a2)
    {
        return a1 + a2;
    }

    public InputStream notValid()
    {
        return new InputStream() {
            @Override
            public int read()
            {
                return 0;
            }
        };
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

package socket.handler;

import java.io.Serializable;

public class Arithmetic implements Serializable
{
    public int sum(int a1, String a2)
    {
        return a1 + a2.length();
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

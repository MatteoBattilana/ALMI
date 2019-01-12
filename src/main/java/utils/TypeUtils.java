package utils;

import exceptions.ClassConversionException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

public class TypeUtils
{
    public static <T> T convertInstanceOfObject(Object o, Class<T> clazz)
    {
        try
        {
            return clazz.cast(o);
        }
        catch(ClassCastException e)
        {
            return null;
        }
    }

    public static Serializable fromString(String str)
      throws ClassConversionException
    {
        byte[] data = Base64.getDecoder().decode(str);
        try
        {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Object o = ois.readObject();
            ois.close();
            return convertInstanceOfObject(o, Serializable.class);
        }
        catch(Exception e)
        {
            throw new ClassConversionException(e);
        }
    }

    public static String toString(Serializable o)
      throws ClassConversionException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            oos.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        }
        catch(Exception e)
        {
            throw new ClassConversionException(o.getClass().getName(), e);
        }
    }
}

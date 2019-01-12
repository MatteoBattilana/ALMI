package utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import exceptions.JSONGenerationException;
import exceptions.JSONParsingException;
import exceptions.MissingKeyException;

import java.util.List;

public interface Container
{
    void put(String key, Object value);
    boolean isEmpty();
    boolean has(String key);

    String getString(String key)
      throws MissingKeyException;
    String optString(String key, String def);
    int getInt(String key)
      throws MissingKeyException;
    int optInt(String key, int def);
    long getLong(String key)
      throws MissingKeyException;
    long optLong(String key, Long def);
    double getDouble(String key)
      throws MissingKeyException;
    double optDouble(String key, double def);
    List<String> getStringList(String key)
      throws MissingKeyException;
    List<String> optStringList(String key, List<String> def);
    String toJSON()
      throws JsonGenerationException, JSONGenerationException;
}

package utils;

import exceptions.JsonGenerationException;
import exceptions.MissingKeyException;

public interface Container<K>
{
    void put(K key, Object value);
    boolean isEmpty();
    boolean has(K key);

    String getString(K key)
      throws MissingKeyException;
    String optString(K key, String def);
    int getInt(K key)
      throws MissingKeyException;
    int optInt(K key, int def);
    long getLong(K key)
      throws MissingKeyException;
    long optLong(K key, Long def);
    double getDouble(K key)
      throws MissingKeyException;
    double optDouble(K key, double def);

    String toJSON()
      throws JsonGenerationException;
}

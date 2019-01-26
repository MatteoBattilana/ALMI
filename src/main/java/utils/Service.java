package utils;

public interface Service<T>
{
    T start();
    void stop();
}
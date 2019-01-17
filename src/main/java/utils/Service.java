package utils;

public interface Service<T>
{
    T start();
    T stop();
}
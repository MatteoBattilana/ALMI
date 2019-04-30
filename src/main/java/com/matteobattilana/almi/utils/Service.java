package com.matteobattilana.almi.utils;

public interface Service<T>
{
    T start();
    void stop();
}
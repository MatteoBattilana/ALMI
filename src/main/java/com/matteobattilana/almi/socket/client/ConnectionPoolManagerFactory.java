package com.matteobattilana.almi.socket.client;

public interface ConnectionPoolManagerFactory
{
    ConnectionPoolManager create(int timeout);
}
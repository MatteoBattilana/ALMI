package com.matteobattilana.almi.socket;

import com.google.inject.assistedinject.Assisted;
import com.matteobattilana.almi.method.MethodDescriptor;

import java.util.Map;

public interface AlmiFactory
{
    Almi create(
      Map<String, MethodDescriptor> methodDescriptorMap,
      @Assisted("threadName") String threadName,
      @Assisted("port") int port,
      @Assisted("connectTimeout") int connectTimeout,
      @Assisted("promiseTimeout") int promiseTimeout
    );
}
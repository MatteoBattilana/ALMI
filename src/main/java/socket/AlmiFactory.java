package socket;

import com.google.inject.assistedinject.Assisted;
import method.MethodDescriptor;

import java.util.Map;

public interface AlmiFactory
{
    Almi create(
      Map<String, MethodDescriptor> methodDescriptorMap,
      @Assisted("threadName")
      String threadName,
      @Assisted("socketAddress")
      String socketAddress,
      @Assisted("port")
      int port,
      @Assisted("connectTimeout")
      int connectTimeout,
      @Assisted("promiseTimeout")
      int promiseTimeout
    );
}
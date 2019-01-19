package socket;

import com.google.inject.assistedinject.Assisted;

public interface AlmiFactory
{
    Almi create(
      @Assisted("threadName") String threadName,
      @Assisted("socketAddress") String socketAddress,
      @Assisted("port") int port,
      @Assisted("connectTimeout") int connectTimeout)
      ;
}
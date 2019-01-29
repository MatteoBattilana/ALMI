package guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import method.MethodsManager;
import socket.AlmiFactory;
import socket.client.ConnectionPoolManagerFactory;
import socket.handler.PromisesManager;
import socket.server.ServerSocketFactory;
import socket.server.ServerSocketServiceFactory;

public class AlmiModules extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(MethodsManager.class).in(Singleton.class);
        bind(PromisesManager.class).in(Singleton.class);

        install(new FactoryModuleBuilder().build(ServerSocketFactory.class));
        install(new FactoryModuleBuilder().build(ServerSocketServiceFactory.class));
        install(new FactoryModuleBuilder().build(ConnectionPoolManagerFactory.class));
        install(new FactoryModuleBuilder().build(AlmiFactory.class));
    }
}

package guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import method.MethodsManager;
import socket.handler.MessageDecoderFactory;
import socket.ServerSocketFactory;
import socket.ServerSocketServiceFactory;
import utils.Container;

public class AlmiModules extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(MethodsManager.class).in(Singleton.class);

        install(new FactoryModuleBuilder().build(ServerSocketFactory.class));
        install(new FactoryModuleBuilder().build(ServerSocketServiceFactory.class));
        install(new FactoryModuleBuilder().build(MessageDecoderFactory.class));
    }
}

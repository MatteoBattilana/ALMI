package com.matteobattilana.almi.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.matteobattilana.almi.method.MethodsManager;
import com.matteobattilana.almi.socket.AlmiFactory;
import com.matteobattilana.almi.socket.client.ConnectionPoolManagerFactory;
import com.matteobattilana.almi.socket.handler.PromisesManager;
import com.matteobattilana.almi.socket.server.ServerSocketFactory;
import com.matteobattilana.almi.socket.server.ServerSocketServiceFactory;

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

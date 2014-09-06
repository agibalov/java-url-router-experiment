package me.loki2302;

import com.google.inject.AbstractModule;

public class DummyModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DummyServlet.class).asEagerSingleton();
    }
}

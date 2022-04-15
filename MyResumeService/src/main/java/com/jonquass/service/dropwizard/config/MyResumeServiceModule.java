package com.jonquass.service.dropwizard.config;

import com.google.inject.AbstractModule;
import com.jonquass.data.config.MyResumeDataModule;
import com.jonquass.service.dropwizard.MyResumeApplication;
import com.jonquass.service.dropwizard.resources.HelloWorldResource;
import com.jonquass.service.dropwizard.resources.TheCatApiResource;

public class MyResumeServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new MyResumeDataModule());
        bind(HelloWorldResource.class);
        bind(TheCatApiResource.class);
        bind(MyResumeApplication.class);
    }
}

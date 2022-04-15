package com.jonquass.service.dropwizard;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jonquass.data.dropwizard.health.check.HelloWorldResourceHealthCheck;
import com.jonquass.data.dropwizard.health.check.PurgoMalumClientHealthCheck;
import com.jonquass.data.dropwizard.health.check.TheCatApiClientHealthCheck;
import com.jonquass.data.dropwizard.health.check.TheCatApiResourceHealthCheck;
import com.jonquass.service.dropwizard.filter.CrossOriginFilter;
import com.jonquass.service.dropwizard.config.ApplicationConfig;
import com.jonquass.service.dropwizard.config.MyResumeServiceModule;
import com.jonquass.service.dropwizard.resources.HelloWorldResource;
import com.jonquass.service.dropwizard.resources.TheCatApiResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.inject.Inject;

public class MyResumeApplication extends Application<ApplicationConfig> {

    @Inject
    MyResumeApplication() {
        super();
    }

    public static void main(String[] args) throws Exception {
        MyResumeApplication application = getInjector().getInstance(MyResumeApplication.class);
        application.run(args);
    }

    @Override
    public String getName() {
        return "my-resume-application";
    }

    @Override
    public void initialize(Bootstrap<ApplicationConfig> bootstrap) {

    }

    @Override
    public void run(ApplicationConfig configuration, Environment environment) {
        registerHealthChecks(environment);
        registerResources(environment);
        addFilters(configuration, environment);
    }

    private static void registerHealthChecks(Environment environment) {
        registerHealthCheck(environment, PurgoMalumClientHealthCheck.class);
        registerHealthCheck(environment, TheCatApiClientHealthCheck.class);
        registerHealthCheck(environment, HelloWorldResourceHealthCheck.class);
        registerHealthCheck(environment, TheCatApiResourceHealthCheck.class);
    }

    private static void registerHealthCheck(Environment environment, Class<? extends HealthCheck> clazz) {
        environment.healthChecks()
                   .register(clazz.getSimpleName(), getInjector().getInstance(clazz));
    }

    private static void registerResources(Environment environment) {
        environment.jersey()
                   .register(getInjector().getInstance(HelloWorldResource.class));
        environment.jersey()
                   .register(getInjector().getInstance(TheCatApiResource.class));
    }

    private static void addFilters(ApplicationConfig configuration, Environment environment) {
        environment.servlets()
                   .addFilter("CORS", new CrossOriginFilter(configuration))
                   .addMappingForUrlPatterns(java.util.EnumSet.allOf(javax.servlet.DispatcherType.class), true, "/*");
    }

    private static Injector getInjector() {
        return Guice.createInjector(new MyResumeServiceModule());
    }
}

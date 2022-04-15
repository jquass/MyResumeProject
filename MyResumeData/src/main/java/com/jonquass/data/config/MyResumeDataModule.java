package com.jonquass.data.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.net.http.HttpClient;

public class MyResumeDataModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    public HttpClient providesHttpClient() {
        return HttpClient.newBuilder()
                         .version(HttpClient.Version.HTTP_2)
                         .followRedirects(HttpClient.Redirect.NORMAL)
                         .build();
    }
}

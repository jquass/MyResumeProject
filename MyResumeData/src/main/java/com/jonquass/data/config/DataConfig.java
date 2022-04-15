package com.jonquass.data.config;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Singleton
public class DataConfig {

    private static final System.Logger LOG = System.getLogger(DataConfig.class.getName());
    private final Properties properties;

    @Inject
    DataConfig() {
        properties = new Properties();
        String propFileName = "config.properties";
        InputStream inputStream = getClass().getClassLoader()
                                            .getResourceAsStream(propFileName);

        if (inputStream == null) {
            LOG.log(System.Logger.Level.ERROR, "No config.properties file found");
            return;
        }

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            LOG.log(System.Logger.Level.ERROR, e);
        }
    }

    public Properties getProperties() {
        return properties;
    }

}

package com.haulmont.propsdemo.rps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

public class ReloadablePropertySourceEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        ReloadablePropertySourceFactory.reloadablePropertySourceMap.values()
                .forEach(ReloadablePropertySource::invalidate);
    }
}

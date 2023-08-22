package com.haulmont.propsdemo.rps;

import com.haulmont.propsdemo.ps.ReloadablePropertySource;
import org.springframework.boot.BootstrapContext;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Collection;

public class ReloadablePropertySourceEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        ReloadablePropertySourceFactory.reloadablePropertySourceMap.values()
                .forEach(ReloadablePropertySource::invalidate);
    }
}

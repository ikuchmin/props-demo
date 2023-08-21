package com.haulmont.propsdemo.ps;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ConfigurationBuilder;
import org.apache.commons.configuration2.builder.ConfigurationBuilderEvent;
import org.apache.commons.configuration2.builder.ConfigurationBuilderResultCreatedEvent;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.configuration2.builder.ConfigurationBuilderEvent.RESET;
import static org.apache.commons.configuration2.builder.ConfigurationBuilderResultCreatedEvent.RESULT_CREATED;

public class ReloadablePropertySource extends PropertySource {

    private static final Logger logger = LoggerFactory.getLogger(ReloadablePropertySource.class);

    private final ConfigurationBuilder<PropertiesConfiguration> configurationBuilder;

    public ReloadablePropertySource(String name, ConfigurationBuilder<PropertiesConfiguration> configurationBuilder) {
        super(name);
        this.configurationBuilder = configurationBuilder;
    }

    public ReloadablePropertySource(String name, String path) {
        super(StringUtils.hasText(name) ? path : name);

        var reloadingFileConfBuilder = new ReloadingFileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                        .configure(new Parameters().fileBased().setFile(new File(path)));

        var reloadingController = reloadingFileConfBuilder.getReloadingController();
        reloadingFileConfBuilder.connectToReloadingController(reloadingController);

        PeriodicReloadingTrigger trigger = new PeriodicReloadingTrigger(reloadingController,
                null, 5, TimeUnit.SECONDS);
        trigger.start();

        reloadingFileConfBuilder.addEventListener(RESULT_CREATED, event -> logger.info("Reload properties from {}", path));
        reloadingFileConfBuilder.addEventListener(RESET, event -> logger.info("Detect changing property file {}", path));

        this.configurationBuilder = reloadingFileConfBuilder;
    }

    @Override
    public Object getProperty(String s) {
        try {
            return configurationBuilder.getConfiguration().getProperty(s);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}

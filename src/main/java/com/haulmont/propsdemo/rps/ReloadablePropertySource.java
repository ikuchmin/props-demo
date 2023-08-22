package com.haulmont.propsdemo.rps;

import lombok.SneakyThrows;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.apache.commons.configuration2.builder.ConfigurationBuilderEvent.RESET;
import static org.apache.commons.configuration2.builder.ConfigurationBuilderResultCreatedEvent.RESULT_CREATED;

public class ReloadablePropertySource extends EnumerablePropertySource<ReloadingFileBasedConfigurationBuilder<PropertiesConfiguration>> {

    private static final Logger logger = LoggerFactory.getLogger(ReloadablePropertySource.class);

    public ReloadablePropertySource(String name, ReloadingFileBasedConfigurationBuilder<PropertiesConfiguration> configurationBuilder) {
        super(name, configurationBuilder);
    }

    public ReloadablePropertySource(String name, String path) {
        super(StringUtils.hasText(name) ? path : name, new ReloadingFileBasedConfigurationBuilder<>(PropertiesConfiguration.class));

        source.configure(new Parameters().fileBased().setFile(new File(path)));

        var reloadingController = source.getReloadingController();
        source.connectToReloadingController(reloadingController);

        source.addEventListener(RESULT_CREATED, event -> logger.info("Reload properties from {}", path));
        source.addEventListener(RESET, event -> logger.info("Detect changing property file {}", path));
    }

    @Override
    @SneakyThrows
    public String[] getPropertyNames() {
        final List<String> keys = new ArrayList<>();
        final Iterator<String> keysIterator = source.getConfiguration().getKeys();
        while (keysIterator.hasNext()) {
            keys.add(keysIterator.next());
        }
        return keys.toArray(new String[0]);
    }

    @Override
    public Object getProperty(String s) {
        try {
            return source.getConfiguration().getProperty(s);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public void invalidate() {
        source.getReloadingController().checkForReloading(null);
    }
}

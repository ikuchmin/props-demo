package com.haulmont.propsdemo.config;

import com.haulmont.propsdemo.PropDependencyBean;
import com.haulmont.propsdemo.props.GitHubProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class RefreshableConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(RefreshableConfiguration.class);

    @Bean
    @RefreshScope
    PropDependencyBean propDependencyBean() {
        logger.info("Init PropDependencyBean");

        String randValue = String.valueOf(ThreadLocalRandom.current().nextInt());
        return new PropDependencyBean(randValue);
    }

}

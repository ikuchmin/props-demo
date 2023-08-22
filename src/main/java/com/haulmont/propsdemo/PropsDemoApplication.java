package com.haulmont.propsdemo;

import com.haulmont.propsdemo.props.GitHubProperties;
import com.haulmont.propsdemo.rps.ReloadablePropertySourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ConfigurationPropertiesScan("com.haulmont.propsdemo")
@PropertySource(name = "external", value = "file:src/main/resources/external.properties", // setUp absolute path to external file
		factory = ReloadablePropertySourceFactory.class)
public class PropsDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropsDemoApplication.class, args);
	}



}

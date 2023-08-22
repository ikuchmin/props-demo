package com.haulmont.propsdemo;

import com.haulmont.propsdemo.rps.ReloadablePropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ConfigurationPropertiesScan("com.haulmont.propsdemo")
@PropertySource(name = "external", value = "file:/Users/ikuchmin/Sources/amplicode-test-projects/props-demo/src/main/resources/external.properties", // setUp absolute path to external file
		ignoreResourceNotFound = true, factory = ReloadablePropertySourceFactory.class)
public class PropsDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropsDemoApplication.class, args);
	}

}

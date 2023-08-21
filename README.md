## Change App behaviour without restart App

The main idea is to have ability reconfigure Spring Boot Application in runtime by changing
properties

**Motivation.** Sometimes we are interesting to reconfigure application without restart.
For example switching between real service and mock service.

Todo this Spring Boot has instruments:

1. Configuration properties (Environment)
2. Spring Boot Cloud Actuator Refresh

There is a problem that Spring Boot doesn't reload properties files during Refresh. To resolve
this we will use Apache Commons Configuration[3]. 

### How to run

Running as usual Spring Boot App.

Do not forget to set up correct path to prop file in PropsDemoApplication

### TODO

1. ~~Add Reloadable PropertySource ReloadablePropertySource~~
2. Add `getPropertyNames` to `ReloadablePropertySource`
3. Try to use RefreshScope, to refresh state of beans
4. Is it possible to support profiles?


### Additional Ideas

1. Accidentally find ConfigurationPropertySource in Apache Commons Configuration[3]
2. CompositeConfiguration has ability to remove/add configuration (but how to find appropriate)
3. Read section "Wrapping Configuration Builders" in Apache Commons Configuration[3]. Builder
   can manage state of configuration

### Concerns

Is it real needed to refresh properties without restarting app? Possible that
industry is resolving the problem by restarting app

### References

1. [Spring Boot Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config) 
2. [Reloading Properties Files in Spring](https://www.baeldung.com/spring-reloading-properties)
3. [Apache Commons Configuration 2 Documentation](https://commons.apache.org/proper/commons-configuration/userguide/user_guide.html)


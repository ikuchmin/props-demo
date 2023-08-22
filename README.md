## Change App behaviour without restart App

The main idea is to have ability reconfigure Spring Boot Application in runtime by changing
properties

**Motivation.** Sometimes we are interesting to reconfigure application without restart.
For example switching between real service and mock service.

Todo this Spring Boot has instruments:

1. Configuration properties (Environment)
2. Spring Boot Cloud Actuator Refresh

There is a problem that Spring Boot doesn't reload properties from file during Refresh. To resolve
this we will use Apache Commons Configuration [3].

Using `@ConditionOn*` will not have effect by Spring Boot design [4].

To support runtime reloading properties defined in external prop by /actuator/refresh
will be implemented:

- ReloadablePropertySource
- ReloadablePropertySourceFactory
- ReloadablePropertySourceEnvironmentPostProcessor

### How to run

Running as usual Spring Boot App

### How to use

#### Refresh property value in external configuration

1. Run app
2. Get value from http://localhost:8080/date-tz (curl -X GET http://localhost:8080/date-tz)
3. Change value in external.properties
4. Do /actuator/refresh (curl -X POST http://localhost:8080/actuator/refresh)
5. Get value from http://localhost:8080/date-tz again (curl -X GET http://localhost:8080/date-tz)

#### Refresh bean state using @RefreshScope

1. Run app
2. Get value from http://localhost:8080/github-login (curl -X GET http://localhost:8080/github-login)
3. Do /actuator/refresh (curl -X POST http://localhost:8080/actuator/refresh)
4. Get value from http://localhost:8080/github-login again (curl -X GET http://localhost:8080/github-login)

#### Using EnvironmentChangeEvent

Project doesn't contain example like this

### TODO

1. ~~Add Reloadable PropertySource ReloadablePropertySource~~
2. ~~Add `getPropertyNames` to `ReloadablePropertySource`~~
3. ~~Try to use RefreshScope, to refresh state of beans~~
4. Is it possible to support profiles?


### Additional Ideas

1. ~~Accidentally find ConfigurationPropertySource in Apache Commons Configuration [3]~~
2. ~~CompositeConfiguration has ability to remove/add configuration (but how to find appropriate)~~
3. ~~Read section "Wrapping Configuration Builders" in Apache Commons Configuration [3]. Builder
   can manage state of configuration~~

### Concerns

Is it real needed to refresh properties without restarting app? Possible that
industry is resolving the problem by restarting app

### References

1. [Spring Boot Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config) 
2. [Reloading Properties Files in Spring](https://www.baeldung.com/spring-reloading-properties)
3. [Apache Commons Configuration 2 Documentation](https://commons.apache.org/proper/commons-configuration/userguide/user_guide.html)
4. [GitHub Issue: @RefreshScope with @ConditionalOnProperty should re-process affected beans on refresh events #1645](https://github.com/spring-cloud/spring-cloud-config/issues/1645#issuecomment-653070180)
5. [SoF: @RefreshScope with @ConditionalOnProperty does not work](https://stackoverflow.com/questions/46271052/refreshscope-with-conditionalonproperty-does-not-work)
6. [SoF: Use Spring @RefreshScope, @Conditional annotations to replace bean injection at runtime after a ConfigurationProperties has changed](https://stackoverflow.com/questions/52008261/use-spring-refreshscope-conditional-annotations-to-replace-bean-injection-at#comment90967560_52008261)
7. [Dynamic Configuration Properties in Spring Boot and Spring Cloud](https://gist.github.com/dsyer/a43fe5f74427b371519af68c5c4904c7)


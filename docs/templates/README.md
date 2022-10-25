Livedata SDK 2.x
----------------
Livedata SDK is a client library that enables easier integration with the Livedata XML feed. SDK exposes XML feed service interface in a more user-friendly way and isolates the client from having to do XML feed parsing, proper connection handling, error recovery, event queuing and dispatching. It also makes a client solution more stable and robust when it comes to feed handling, especially with the release of new and updated XML feed versions.

### CONFIGURATION
In your sdk.properties you need at least to set-up the following: (replace xxx with actual credentials).
```
sdk.livescout.enabled=true
sdk.livescout.username=xxx
sdk.livescout.password=xxx
```
A complete list of properties and their defaults (when appropriate) can be found [here](https://github.com/sportradar/LivedataSdkJava/blob/main/sdk-example/src/main/resources/sdk.properties.all).
- SDK will throw [MissingPropertyFileException](https://sportradar.github.io/LivedataSdkJava/2.0.0/com/sportradar/sdk/common/exceptions/MissingPropertyFileException.html) if sdk.properties file is not found
- SDK will throw [MissingPropertyException](https://sportradar.github.io/LivedataSdkJava/2.0.0/com/sportradar/sdk/common/exceptions/MissingPropertyException.html) if any mandatory property is missing from the properties file
- SDK will throw [InvalidPropertyException](https://sportradar.github.io/LivedataSdkJava/2.0.0/com/sportradar/sdk/common/exceptions/InvalidPropertyException.html) if any property written in properties file is malformed

[Javadoc](https://sportradar.github.io/LivedataSdkJava/${project.version}/index.html)

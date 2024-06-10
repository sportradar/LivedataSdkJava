Livedata SDK 2.x
----------------
Livedata SDK is a client library that enables easier integration with the Livedata XML feed. SDK exposes XML feed service interface in a more user-friendly way and isolates the client from having to do XML feed parsing, proper connection handling, error recovery, event queuing and dispatching. It also makes a client solution more stable and robust when it comes to feed handling, especially with the release of new and updated XML feed versions.

### BUILDING
Project consists of next modules:
- **sdk**. Sdk itself.
- **sdk-example**. Example project to test sdk.
- **sdk-test**. Unit tests.
- **sdk-loginterceptor**. Jmx functionality for debugging.
- **sdk-jar-example**. Separate example project to test sdk.jar independently. _Requirs sdk.jar in release folder._

Running "_package_" will run unit tests, generate javadoc and all needed jar files at _sdk/target/_.

### INSTALLATION
Project builds three different jars:
* sdk-2.0.9.jar - only sdk classes, need to upload dependencies from maven.
* sdk-2.0.9-fatjar.jar - stores all needed libraries inside jar.
* sdk-2.0.9-fatjar-shaded.jar - stores all needed libraries inside jar. Libraries are shaded to avoid overwriting by newer versions.
> **_NOTE:_**	Despite the availability of original jar, we recommend to use fatjar-shaded to avoid libraries versions incompatibility.

Livedata sdk can be imported from [Maven Central Repository](https://mvnrepository.com/artifact/com.sportradar.livedata.sdk/sdk).
Just add the fatjar-shaded dependency to your pom.xml file:
```
<dependency>
    <groupId>com.sportradar.livedata.sdk</groupId>
    <artifactId>sdk</artifactId>
    <version>2.0.9</version>
    <classifier>fatjar-shaded</classifier>
    <exclusions>
        <exclusion>
            <groupId>*</groupId>
            <artifactId>*</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```
If you want to manage sdk libraries original jar be used:
```
<dependency>
    <groupId>com.sportradar.livedata.sdk</groupId>
    <artifactId>sdk</artifactId>
    <version>2.0.9</version>
</dependency>
```

### CONFIGURATION
In your _sdk.properties_ you need at least to set-up the following: (replace xxx with actual credentials).
```
sdk.livescout.enabled=true
sdk.livescout.username=xxx
sdk.livescout.password=xxx
```
A complete list of properties and their defaults (when appropriate) can be found [here](https://github.com/sportradar/LivedataSdkJava/blob/main/sdk-example/src/main/resources/sdk.properties.all).
- SDK will throw [MissingPropertyFileException](https://sportradar.github.io/LivedataSdkJava/com/sportradar/livedata/sdk/common/exceptions/MissingPropertyFileException.html) if sdk.properties file is not found
- SDK will throw [MissingPropertyException](https://sportradar.github.io/LivedataSdkJava/com/sportradar/livedata/sdk/common/exceptions/MissingPropertyException.html) if any mandatory property is missing from the properties file
- SDK will throw [InvalidPropertyException](https://sportradar.github.io/LivedataSdkJava/com/sportradar/livedata/sdk/common/exceptions/InvalidPropertyException.html) if any property written in properties file is malformed

### Usage
First you need to implement the [LiveScoutFeedListener](https://sportradar.github.io/LivedataSdkJava/com/sportradar/livedata/sdk/feed/livescout/interfaces/LiveScoutFeedListener.html) that will receive callbacks for each message/event.
Then to actually connect and start receiving messages you do the following:
```java
final Sdk sdk = Sdk.getInstance();
final LiveScoutFeed liveScoutFeed = sdk.getLiveScout();
final LiveScoutFeedListener scoutFeedListener = new LiveScoutFeedListenerImpl();
liveScoutFeed.open(scoutFeedListener);
```
> **_NOTE:_**  Bookmaker SDK is a singleton. There should be only one SDK instance per process. When using multiple processes avoid running multiple SDK instances, especially if the same access credentials are used. You may end up in an inconsistent state and get problems due to limits on the server side. Use IPC instead in such cases.

SDK provider(s) will try to connect to the corresponding XML feed server and keep the connection alive. If the connection is lost the provider will try to reconnect automatically - you will be informed of this through the corresponding events.
<br>To send message use [LiveScoutFeed](https://sportradar.github.io/LivedataSdkJava/com/sportradar/livedata/sdk/feed/livescout/interfaces/LiveScoutFeed.html) instace. For example:
```java
final LiveScoutFeed liveScoutFeed = sdk.getLiveScout();
liveScoutFeed.getMatchList(1,3,true);
```
### Logs
SDK will make various logs during its operation. Logs are organized into various categories, based on whether these are critical alerts, invalid messages received, configuration updates, message traffic, etc. Level of logging can be configured through _sdk.properties_. All logger settings are listed in _sdk.properties.all_.
### Gotchas
SDK generates implicit "*bet stop*" message after disconnect and does automatic error recovery. It does however not keep track of bet clearings!
If you are disconnected for a long time it may happen that after you come back the match is already over. In that time-frame bets were not accepted (so you are safe) but it might still be necessary to clear the bets placed at the begining of the match. In that (rare) case you it is up to you to invoke [getMatchStatus](https://sportradar.github.io/LivedataSdkJava/com/sportradar/livedata/sdk/feed/livescout/entities/MatchUpdateEntity.html#getMatchStatus()) method to obtain bet clearings to do correct pay-outs (if / when required).

<!--If match is suspended or cancelled you will receive onMetaInfoReceived and see the change periodically in onAliveReceived as AliveEntity.getEventHeaders().getStatus(),
but again you can be disconnected too long and miss that. So same logic as before applies, you need to be sure to do some sort of "garbage-collection" and delete stale matches.-->

SDK never generates implicit "_bet start_". You should not rely on "_bet start_" to start accepting bets again but check [MatchHeaderEntity.getBetStatus()](https://sportradar.github.io/LivedataSdkJava/com/sportradar/livedata/sdk/feed/livescout/entities/MatchHeaderEntity.html#getBetStatus())!

### Documentation
[Javadoc](https://sportradar.github.io/LivedataSdkJava/) for latest version. For older version [javadoc.io](https://javadoc.io/) can be used.

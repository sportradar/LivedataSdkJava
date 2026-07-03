Livedata SDK 2.x
----------------
Livedata SDK is a client library that enables easier integration with the Livedata XML feed. SDK exposes XML feed service interface in a more user-friendly way and isolates the client from having to do XML feed parsing, proper connection handling, error recovery, event queuing and dispatching. It also makes a client solution more stable and robust when it comes to feed handling, especially with the release of new and updated XML feed versions.

### BUILDING
Project consists of next modules:

**Customer-facing**
- **sdk** — the published library (Maven Central).
- **sdk-example** — runnable quickstart and config reference ([sdk-example/README.md](sdk-example/README.md)).

**Internal (maintainers only)**
- **sdk-test** — automated tests and CI coverage.
- **sdk-loginterceptor** — legacy JMX/log debugging tool for SDK developers.
- **sdk-jar-example** — manual smoke test for the fatjar-shaded JAR (see [sdk-jar-example/README.md](sdk-jar-example/README.md)).

Running `_verify_` runs unit tests and builds all JARs at _sdk/target/_ without regenerating Javadoc. Javadoc HTML is updated on release via the GitLab `publish-javadoc` CI job (mirrored to [GitHub Pages](https://sportradar.github.io/LivedataSdkJava/)).

```bash
# Default local build (no Javadoc)
mvn clean verify -Dgpg.skip=true

# Optional local Javadoc preview (writes to sdk/target/apidocs, not docs/)
mvn -pl sdk -DskipSiteJavadoc=false package -Dgpg.skip=true
```

To run the example locally:

```bash
cp sdk-example/src/main/resources/sdk.properties.all sdk-example/src/main/resources/sdk.properties
# edit sdk.properties with your credentials
mvn -pl sdk-example -am package -Dgpg.skip=true
mvn -pl sdk-example exec:java -Dgpg.skip=true
```

### INSTALLATION
Project builds three different jars:
* sdk-${revision}.jar - only sdk classes, need to upload dependencies from maven.
* sdk-${revision}-fatjar.jar - stores all needed libraries inside jar.
* sdk-${revision}-fatjar-shaded.jar - stores all needed libraries inside jar. Libraries are shaded to avoid overwriting by newer versions.
> **_NOTE:_**	Despite the availability of original jar, we recommend to use fatjar-shaded to avoid libraries versions incompatibility.

Livedata sdk can be imported from [Maven Central Repository](https://mvnrepository.com/artifact/com.sportradar.livedata.sdk/sdk).
Just add the fatjar-shaded dependency to your pom.xml file:
```
<dependency>
    <groupId>com.sportradar.livedata.sdk</groupId>
    <artifactId>sdk</artifactId>
    <version>${revision}</version>
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
    <version>${revision}</version>
</dependency>
```

### CONFIGURATION
The SDK authenticates with Sportradar Common IAM using **Private Key JWT**. In your _sdk.properties_ you need at least the following (replace `xxx` with actual values):
```
sdk.livescout.auth0.domain=https://auth.sportradar.com/
sdk.livescout.auth0.client_id=xxx
sdk.livescout.auth0.kid=xxx
sdk.livescout.auth0.private_key=xxx
```

| Property | Value |
|----------|-------|
| `sdk.livescout.auth0.domain` | Common IAM domain. Production: `https://auth.sportradar.com/`; staging: `https://stg-auth.sportradar.com/` (recommended for integration testing). |
| `sdk.livescout.auth0.client_id` | Service Account ID (Auth0 client ID), provided by your Sportradar contact after Service Account setup. |
| `sdk.livescout.auth0.kid` | Key ID assigned when your public key is registered in Common IAM; provided by your Sportradar contact. |
| `sdk.livescout.auth0.private_key` | RSA private key (PEM) from the key pair you generate locally; keep it secret and never commit it. |

**How to obtain credentials:** Service Accounts are not self-managed today — coordinate with your **Sportradar contact**. You generate an RSA key pair (RS256/RS384/PS256, PEM, 2048-bit recommended), share the **public key** with Sportradar, and they register it and return the **Client ID**, **KID**, and audience details via approved secure channels (e.g. Keeper). See the [Common IAM Guide — Environments](https://docs.sportradar.com/live-data/live-data-golf-api-design/ld-golf-api-documentation/common-iam-guide#environments) for the full Private Key JWT flow.

**Staging:** For integration testing against staging, use the staging IAM domain and override the feed host (default production host is `livedata.betradar.com`):
```
sdk.livescout.auth0.domain=https://stg-auth.sportradar.com/
sdk.livescout.host_name=livedata.betradar.dev
```

Legacy login option is still available, but it is deprecated and will be removed in the future. If you want to use legacy login, you need to set-up the following:
```
sdk.livescout.username=xxx
sdk.livescout.password=xxx
```
Optional properties and code defaults are documented in [sdk.properties.all](sdk-example/src/main/resources/sdk.properties.all). Copy it to `sdk.properties` and set credentials; uncomment keys to override defaults.
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
SDK will make various logs during its operation. Logs are organized into various categories (alerts, invalid messages, client interaction, traffic, execution). Levels are configured through _sdk.properties_; see _sdk.properties.all_ for keys and defaults.
### Gotchas
SDK generates implicit "*bet stop*" message after disconnect and does automatic error recovery. It does however not keep track of bet clearings!
If you are disconnected for a long time it may happen that after you come back the match is already over. In that time-frame bets were not accepted (so you are safe) but it might still be necessary to clear the bets placed at the begining of the match. In that (rare) case you it is up to you to invoke [getMatchStatus](https://sportradar.github.io/LivedataSdkJava/com/sportradar/livedata/sdk/feed/livescout/entities/MatchUpdateEntity.html#getMatchStatus()) method to obtain bet clearings to do correct pay-outs (if / when required).

<!--If match is suspended or cancelled you will receive onMetaInfoReceived and see the change periodically in onAliveReceived as AliveEntity.getEventHeaders().getStatus(),
but again you can be disconnected too long and miss that. So same logic as before applies, you need to be sure to do some sort of "garbage-collection" and delete stale matches.-->

SDK never generates implicit "_bet start_". You should not rely on "_bet start_" to start accepting bets again but check [MatchHeaderEntity.getBetStatus()](https://sportradar.github.io/LivedataSdkJava/com/sportradar/livedata/sdk/feed/livescout/entities/MatchHeaderEntity.html#getBetStatus())!

### Documentation
[Javadoc](https://sportradar.github.io/LivedataSdkJava/) for latest version. For older version [javadoc.io](https://javadoc.io/) can be used.

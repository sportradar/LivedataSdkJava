# sdk-example

Runnable quickstart for the Livedata SDK. Use this module when developing against the repo, or as a template for your own integration.

## Prerequisites

- Java 11+
- Maven 3.6+
- Sportradar Common IAM credentials (see root [README.md](../README.md#configuration))

## Setup

1. Copy the property reference and add your credentials:

```bash
cp src/main/resources/sdk.properties.all src/main/resources/sdk.properties
```

2. Edit `src/main/resources/sdk.properties` — at minimum set:

```
sdk.livescout.auth0.domain=https://stg-auth.sportradar.com/
sdk.livescout.auth0.client_id=<your-client-id>
sdk.livescout.auth0.kid=<your-kid>
sdk.livescout.auth0.private_key=<your-pem-key>
sdk.livescout.host_name=livedata.betradar.dev
```

Never commit `sdk.properties` (it is gitignored).

## Build

From the repository root:

```bash
mvn -pl sdk-example -am package -Dgpg.skip=true
```

## Run

```bash
mvn -pl sdk-example exec:java -Dgpg.skip=true
```

Or run `com.sportradar.livedata.sdk.example.Main` from your IDE with the `sdk-example` module classpath.

### Interactive commands

| Command | Description |
|---------|-------------|
| `matchlist` | Request matches for the next/past 13 hours |
| `subscribe <id[,id...]>` | Subscribe to one or more match IDs |
| `book <id[,id...]>` | Book one or more match IDs |
| `help` | Show available commands |
| `close` | Shut down the SDK and exit |

Typical flow: run the example, type `matchlist`, note match IDs from the log output, then `subscribe <id>`.

## Using the fatjar-shaded JAR instead of Maven

Most production integrations depend on the published artifact from Maven Central (see root README). If you need to verify the shaded fat JAR locally:

1. Build the SDK: `mvn -pl sdk package -Dgpg.skip=true`
2. Add `sdk/target/sdk-*-fatjar-shaded.jar` to your classpath
3. Copy `sdk.properties` to your classpath root

The legacy [`sdk-jar-example`](../sdk-jar-example/) module is kept for internal fatjar smoke tests only — prefer this module for day-to-day development.

## Key files

| File | Purpose |
|------|---------|
| `Main.java` | Opens the feed and reads commands from stdin |
| `LiveScoutFeedListenerImpl.java` | Logs all feed callbacks (no automatic booking/subscribing) |
| `sdk.properties.all` | Full list of SDK configuration keys and defaults |

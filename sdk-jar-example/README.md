# sdk-jar-example

**Internal only.** Manual smoke test that the `sdk-*-fatjar-shaded.jar` works as a classpath dependency.

For day-to-day integration work, use [`sdk-example`](../sdk-example/) instead.

## When to use this

After building the SDK, verify the shaded fat JAR before release — especially if you do not use Maven dependency management in your consumer project.

## Setup

1. Build the shaded JAR (version must match `sdk.version` in `pom.xml`):

```bash
mvn -pl sdk package -Dgpg.skip=true
```

2. Copy credentials:

```bash
cp ../sdk-example/src/main/resources/sdk.properties.all src/main/resources/sdk.properties
```

3. Compile:

```bash
mvn compile
```

4. Run (adjust version in the path if needed):

```bash
java -cp "target/classes:src/main/resources:../sdk/target/sdk-2.0.19-fatjar-shaded.jar" example.Main
```

Commands: `matchlist`, `close`.

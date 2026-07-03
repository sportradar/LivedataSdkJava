# AGENTS.md — LivedataSdkJava

## Project Overview

Java client library for integrating with the Sportradar LiveScout (Livedata) XML feed. The SDK handles TCP connection management, XML parsing, authentication (Auth0 JWT or legacy credentials), error recovery, event queuing, and dispatch to application callbacks. Downstream bookmaker applications depend on the published Maven artifact; this repo does not deploy a long-running service.

- **Language**: Java 11
- **Build**: Maven (multi-module)
- **Framework**: Google Guice (DI), JAXB (XML), LMAX Disruptor (dispatch), SLF4J/Logback
- **Runtime target**: Library JAR (Maven Central); example apps for local/manual testing
- **Group ID / root package**: `com.sportradar.livedata.sdk`
- **Related docs**: [README.md](README.md), [Javadoc](https://sportradar.github.io/LivedataSdkJava/), property reference `sdk-example/src/main/resources/sdk.properties.all`

---

## Module Structure

| Module | Language | Purpose |
|--------|----------|---------|
| `sdk` | Java 11 | Core library; sole published artifact |
| `sdk-test` | Java 11 | Unit/integration tests; JaCoCo aggregate report for Sonar |
| `sdk-example` | Java 11 | Runnable example using project dependency on `sdk` |
| `sdk-loginterceptor` | Java 11 | JMX log-interception debugging tool (not published) |
| `sdk-jar-example` | Java 11 | Standalone example against pre-built `sdk-*-fatjar-shaded.jar` (not in parent `default` profile) |

**Dependency rule**: `sdk-test`, `sdk-example`, and `sdk-loginterceptor` depend on `sdk` only. Only `sdk` is published to Maven Central (`sdk-example`, `sdk-test`, `sdk-loginterceptor` are excluded in `central-publishing-maven-plugin`).

---

## Repo Layout

```
LivedataSdkJava/
  pom.xml                         ← parent; ${revision} version (currently 2.0.19)
  sdk/                            ← core library + XSD/JAXB codegen
    src/main/java/.../sdk/        ← common, proto, feed, dispatch, di
    src/main/xsd/                 ← Scout.xsd, ScoutRequest.xsd (edit these, not generated Java)
    target/                       ← sdk-*.jar, fatjar, fatjar-shaded, javadoc inputs
  sdk-test/src/test/java/         ← all automated tests
  sdk-example/src/main/resources/ ← sdk.properties (local), sdk.properties.all (reference)
  sdk-loginterceptor/             ← JMX debugging helper
  sdk-jar-example/                ← fatjar smoke test (manual; version pinned separately)
  docs/                           ← Javadoc HTML for GitHub Pages (updated by publish-javadoc CI job, not local builds)
  .gitlab-ci.yml
  .m2/settings.xml                ← Maven Central publish credentials placeholders
```

---

## Key Source Locations

- **Entry point (library)**: `sdk/src/main/java/com/sportradar/livedata/sdk/feed/sdk/Sdk.java` (`Sdk.getInstance()` singleton)
- **Public API** (preserve backward compatibility; bump `${revision}` on breaking changes):
  - `sdk/src/main/java/com/sportradar/livedata/sdk/feed/livescout/interfaces/`
  - `sdk/src/main/java/com/sportradar/livedata/sdk/feed/livescout/entities/`
  - `sdk/src/main/java/com/sportradar/livedata/sdk/feed/livescout/enums/`
  - `sdk/src/main/java/com/sportradar/livedata/sdk/common/exceptions/`
- **Internal implementations**: `sdk/.../feed/livescout/classes/`, `sdk/.../proto/`, `sdk/.../dispatch/`, `sdk/.../common/`
- **Config**: `sdk.properties` on classpath (`PropertyFileSettingsLoader.DEFAULT_SETTINGS_FILE_NAME = "/sdk.properties"`); full key list in `sdk-example/src/main/resources/sdk.properties.all`
- **Generated / do not edit**:
  - JAXB classes under `sdk/target/generated-sources/jaxb/` (from `sdk/src/main/xsd/` via `jaxb2-maven-plugin`)
  - Javadoc HTML under `docs/` (updated on release via `publish-javadoc` CI job; local preview at `sdk/target/apidocs/`)
- **Do not modify without setup**: `sdk-test/.../system/LiveScoutSystemTest.java` (`@Disabled` system test with mock server framework)

---

## Build & Test Commands

```bash
# Default local build + tests (no Javadoc; does not touch docs/)
mvn clean verify -Dgpg.skip=true

# Full build with all JARs (still skips Javadoc by default)
mvn clean package -Dgpg.skip=true

# Optional local Javadoc preview (sdk/target/apidocs only)
mvn -pl sdk -DskipSiteJavadoc=false package -Dgpg.skip=true

# Run tests in sdk-test module (JaCoCo report at sdk-test/target/site/jacoco-aggregate/)
mvn -pl sdk-test -am test -Dgpg.skip=true

# Build sdk module only (skip tests)
mvn -pl sdk -DskipTests package -Dgpg.skip=true

# Publish to Maven Central (CI: manual on protected branch; requires GPG + Central credentials)
mvn -s .m2/settings.xml clean deploy -Dgpg.skip=false -DskipSiteJavadoc=false
```

### Java Maven extras

```bash
# CI-equivalent Sonar analysis command prefix (from .gitlab-ci.yml)
mvn -s .m2/settings.xml -U -Dmaven.repo.local=$CI_PROJECT_DIR/.m2/repository -Denv.type=aws -Dgpg.skip=true clean verify
```

### Local run

```bash
# 1. Build sdk + example
mvn -pl sdk-example -am package -Dgpg.skip=true

# 2. Copy/configure credentials (never commit secrets)
cp sdk-example/src/main/resources/sdk.properties.all sdk-example/src/main/resources/sdk.properties
# Edit sdk.properties: set Auth0 (or legacy) credentials; uncomment optional keys as needed

# 3. Run the example
mvn -pl sdk-example exec:java -Dgpg.skip=true
# Or run com.sportradar.livedata.sdk.example.Main from IDE (sdk-example module classpath)
```

**Build outputs** (under `sdk/target/` after `package`):

| Artifact | Classifier | Notes |
|----------|------------|-------|
| `sdk-${revision}.jar` | — | SDK classes only; consumer manages transitive deps |
| `sdk-${revision}-fatjar.jar` | `fatjar` | Bundled dependencies |
| `sdk-${revision}-fatjar-shaded.jar` | `fatjar-shaded` | **Recommended** — dependencies relocated under `com.sportradar.shaded.*` |

**Test notes**: Tests live in `sdk-test` only (not in `sdk` module). Existing tests use JUnit 5 with Hamcrest and JMock; prefer AssertJ for new tests. JaCoCo aggregate XML path for Sonar: `sdk-test/target/site/jacoco-aggregate/jacoco.xml`. Lombok annotation processing is required for `sdk` compilation.

---

## Environment Variables & Configuration

Runtime configuration is **file-based** (`sdk.properties` on the classpath), not environment variables. Never commit credentials.

| Property | Default | Purpose |
|----------|---------|---------|
| `sdk.livescout.auth0.domain` | `https://auth.sportradar.com/` | Auth0 domain for JWT login |
| `sdk.livescout.auth0.client_id` | — (required for token auth) | Auth0 client ID |
| `sdk.livescout.auth0.kid` | — (required for token auth) | Key ID for JWT signing |
| `sdk.livescout.auth0.private_key` | — (required for token auth) | RSA private key (PEM) |
| `sdk.livescout.auth0.audience` | `livedata-feed` / `livedata-replay` (test) | JWT audience |
| `sdk.livescout.username` | — | Legacy login (deprecated) |
| `sdk.livescout.password` | — | Legacy login (deprecated) |
| `sdk.livescout.enabled` | — | Enable LiveScout feed |
| `sdk.livescout.test` | `false` | Connect to test/replay server |

See `sdk-example/src/main/resources/sdk.properties.all` for feed connection, rate limits, dispatcher, and logger settings.

**CI publish credentials** (GitLab CI variables, not in repo):

- Maven Central (`publish-maven-central`): `GPG_PRIVATE_KEY`, `GPG_KEYID`, `GPG_PASSPHRASE`, `MAVEN_CENTRAL_USERNAME`, `MAVEN_CENTRAL_PASSWORD`
- Git push (`publish-javadoc`): `GITLAB_GIT_PUSH_USER`, `GITLAB_GIT_PUSH_TOKEN`, `GITLAB_LD_PUSH_USERNAME`, `GITLAB_LD_PUSH_EMAIL`

---

## Operational Guides

### Version bump and release

1. Update `${revision}` in root `pom.xml` (currently `2.0.19`).
2. Run `mvn clean verify -Dgpg.skip=true` — verifies tests (Javadoc skipped by default).
3. Merge to protected branch (`main`).
4. Trigger manual `publish-javadoc` job — generates Javadoc, commits `docs/` to GitLab `main` (mirrored to GitHub Pages).
5. Trigger manual `publish-maven-central` job — deploys artifacts + Javadoc JAR to Maven Central.

**GitHub Pages:** served from `docs/` on GitHub `main`, populated by the GitLab→GitHub mirror. Do **not** remove `docs/` from GitLab `main`.

### Known pitfalls (SDK behaviour)

- **Singleton**: One `Sdk` instance per process; multiple instances with same credentials can hit server-side limits.
- **Implicit bet stop**: SDK emits a synthetic bet-stop on disconnect; it does **not** track bet clearings. After long disconnects, call `getMatchStatus()` to recover clearing state.
- **No implicit bet start**: Do not rely on bet-start messages; check `MatchHeaderEntity.getBetStatus()`.
- **Stale pom resource paths**: `sdk/pom.xml` references `../sdk-feed-layer/src/main/resources` and `sdk-test/pom.xml` references `../sdk-proto-layer/` — these sibling directories are **not present** in this repo layout (legacy split remnants).

---

## AGENTS.md Maintenance Rule

AI agent changes code → **MUST** check if nearest `AGENTS.md` needs update.

Update when change adds/modifies **durable knowledge**:
- Build, test, run, bootstrap commands
- Architecture or module boundaries
- Naming conventions or code organisation
- Data-source, protocol, auth, or scheduling behaviour
- Known pitfalls, anti-patterns, required validation

Do **NOT** update for trivial refactors or local changes.

**Scope**:
- Module change → update that module's `AGENTS.md`
- Multi-module / repo-wide → update root `AGENTS.md`
- Details unknown → add `TODO` placeholder (don't invent)

---

## README.md Maintenance Rule

AI agent changes code → **MUST** check if relevant `README.md` needs update.

Update when change affects developer/operator knowledge:
- Build, test, run, env-setup commands
- Module responsibilities, architecture, dependency flow
- New/changed config keys and env vars
- API behaviour, data flow, scheduling, retry, integration expectations
- Known limitations, migration steps, operational caveats

Do **NOT** update for trivial internal refactors that don't change behaviour/usage.

Edit root `README.md` directly. Use `${revision}` as the version placeholder in dependency examples (matches the property in root `pom.xml`).

---

## Scope of Change

- Changes: **focused, minimal**. No sweeping multi-file changes.
- Refactor: preserve external behaviour; maintain backward compatibility.
- New code: follow all guidelines below. Existing code: follow guidelines for touched code; avoid large refactors unless needed.
- Challenge bad suggestions; propose alternatives instead of blindly following.
- Ask for clarification when reasoning behind change unclear.
- Bump `${revision}` in root `pom.xml` for releases; update root `README.md` when user-facing docs change.

---

## Code Style

### Formatting

- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).
- Newline at end of every file.
- 2-space indent; continuation lines 4-space indent.
- Line length ≤ 100 columns; wrap long method signatures, each arg on own line.

### Member Ordering

**Production classes**: static fields → instance fields → static factory methods → constructors (public first) → public methods → private methods. Empty line after class declaration and between field groups.

**Test classes**: static fields → final fields → `@Mock` fields → mutable fields → `@BeforeEach`/`@AfterEach` → test methods → helper methods. Group related tests with `@Nested`.

### Imports

- Static imports before regular imports, blank line between.
- Alphabetical within each group.
- **No wildcard imports** (`import java.util.*` forbidden).
- Static-import frequently used methods (`assertThat`, `requireNonNull`, `when`, `argThat`).
- Access enum constants via type (`MatchStatus.ACTIVE`); don't import individual constants.

### Annotations and Keywords

- `@Override` on all overriding methods.
- `@FunctionalInterface` on all functional interfaces.
- `final` on local vars, method params (except constructor params), and private fields in new code.

### Package Structure

- Root package: `com.sportradar.livedata.sdk` with layer subpackages: `common`, `proto`, `feed`, `dispatch`, `di`
- No classes in the root package; use subpackages (`interfaces`, `entities`, `classes`, `settings`, `networking`, etc.)
- Public API stays under `feed/livescout/interfaces`, `feed/livescout/entities`, `feed/livescout/enums`

### Naming Conventions

| Kind | Suffix | Example |
|------|--------|---------|
| Feed contract | `LiveScout*` interface | `LiveScoutFeed`, `LiveScoutFeedListener` |
| Implementation | `*Impl` | `LiveScoutFeedImpl` |
| Entity | `*Entity` | `MatchUpdateEntity` |
| Factory | `*Factory` | `JaxbLiveScoutEntityFactory` |
| Provider | `*Provider` | `TokenAuthMessageProvider` |
| Manager | `*Manager` | `LiveScoutUserRequestManager` |
| Gateway | `*Gateway` | `TcpGateway`, `ReconnectingGateway` |
| Event handler | `*Handler` | `EntityEventHandler` |
| Dispatcher | `*Dispatcher` | `LiveScoutDisruptorDispatcher` |

---

## Comments and Javadoc

- Javadoc on all **public** methods, classes, interfaces: behaviour, params, return values, exceptions.
- Inline comments: explain **why**, not **what**. Keep minimal.

---

## Language Features

- Java 11 source/target (`maven.compiler.source` / `target` in root `pom.xml`).
- Always use try-with-resources for `AutoCloseable`/`Closeable`.
- No `var` in Java — always explicit types.
- Production code: prefer stable/final JDK features. Preview/incubator/experimental needs explicit architecture approval + documented rationale.

---

## Logging

```java
private static final Logger logger = LoggerFactory.getLogger(ThisClass.class);
```

- **SLF4J only** — no framework-specific logging APIs. Don't pass loggers as params.
- Parameterised logging with `{}` — **no string concatenation**. Exceptions as last arg.
- Censor sensitive data (passwords, tokens, keys, PII) in logs
- Avoid logging in tight loops.
- Level guards (`isDebugEnabled()`) only when computing log args expensive.

---

## Nullability and Defensive Programming

- Annotate public method params and return types with `@Nonnull`/`@Nullable` (`javax.annotation`).
  - Where JSpecify available: prefer package-level `@NullMarked`; use `@Nullable` only where null is explicit contract.
- `requireNonNull` in constructors for every non-null field.
- Prefer primitives over boxed types. Prefer Null Object pattern and `Optional` over `null`-based control flow.
- Avoid excessive null checks, over-eager input validation, Pokémon exception handling.

---

## Error Handling

- **Actions** (state-changing): `void` return + checked exception on failure.
- **Calculations** (pure/query): return `Optional` or functional type on failure — no exceptions.
- Catch **specific** exceptions. No broad `Exception`/`RuntimeException` blind rethrow.
- No exceptions for control flow.
- Vavr types (`Try`, `Either`): internal use only — don't expose in public API.

---

## Concurrency

- Prefer `java.util.concurrent` over `synchronized`. Synchronise at finest practical granularity.
- Use concurrent collections (`ConcurrentHashMap`, `CopyOnWriteArrayList`) over manually synchronised standard collections.
- Use scheduled executors instead of sleep loops.
- Clean up threads, executors, locks on shutdown.

---

## Dependencies

- Reuse existing dependencies. New ones require justification.
- Don't force downstream consumers to pull new transitive deps; use `provided`/`optional` scope for compile-only deps in library modules.
- Test-only libs (JUnit, Mockito, AssertJ, Awaitility) must stay in `test` scope.

---

## Testing

### Conventions

- Test class: `[ClassUnderTest]Test` (unit), `[ClassUnderTest]IT` (integration).
- Test method: `should[ExpectedBehavior]When[StateUnderTest]`.
- **AssertJ** for all new Java assertions. Chain on same subject: `assertThat(x).isNotNull().hasSize(3)`.
  - Prefer specific matchers (`isZero()` over `isEqualTo(0)`).
  - Assert most specific condition; avoid redundant assertions.
- No mixing JUnit assertions with AssertJ in same test.
- `@Nested` to group tests for same method or related behaviour.
- Parameterised tests for multiple input scenarios.

### Coverage and Design

- Target **100% branch/condition coverage** for new and changed code.
- One behaviour per test. No branches in tests. No assertions in setup/teardown.
- No test-only code in production classes.
- No reflection or bytecode manipulation to access private state.
- Avoid static mocking — design for testability via dependency injection.
- Don't test logger interactions.

### Parallelism and Resources

- Tests must be safe for parallel execution (parallel between test classes).
- Use `@ResourceLock` for tests needing exclusive access to shared state.
- Avoid `Thread.sleep`/timeouts — use latches, semaphores, or `Awaitility`.

---

## Architecture

- **Separate actions from calculations**: calculations don't alter state; actions don't return values.
- Don't mutate method arguments. Return new values.
- Favour immutability — return unmodifiable collections.
- Prefer `package-private`/`private`. Use `public` only for intended API surface.
- Use interfaces for service contracts. Null Object pattern for defaults.
- Prefer streams/lambdas over loops, except when checked exceptions make lambdas awkward.
- New methods ≤ ~15 lines. Extract helpers from long legacy methods instead of extending.
- **No circular module dependencies.** Business logic modules must not depend on each other; only assembly/app module may wire them.

### Repo-specific architecture

Layered design inside the `sdk` module (wired via Guice in `di/`):

```
Application
    └── Sdk (singleton) ──► LiveScoutFeed (interface)
            │
            ▼
    feed/livescout/classes/   ← feed orchestration (LiveScoutFeedImpl, ProtocolManager)
            │
            ▼
    proto/                    ← XML tokenize/parse/write, auth (JWT/credentials)
            │                      JAXB bindings from Scout.xsd / ScoutRequest.xsd
            ▼
    common/networking/        ← TCP, reconnect, connection monitoring
            │
            ▼
    dispatch/                 ← LMAX Disruptor queue → LiveScoutFeedListener callbacks
```

**Data flow**: TCP bytes → `Gateway` → `MessageTokenizer`/`MessageParser` (JAXB) → `MessageProcessor`/`MessagePipeline` → `LiveScoutDispatcher` (Disruptor) → user `LiveScoutFeedListener`.

**Auth**: `TokenAuthMessageProvider` (Auth0 JWT, preferred) or `CredentialsAuthMessageProvider` (legacy username/password). Settings loaded from `sdk.properties` via `PropertyFileSettingsLoader`.

**DI**: `GeneralInjectionModule` + `LiveScoutInjectionModule`; constructor injection throughout (Guice `@Inject`).

**Deployment model**: Published as Maven artifact to Central; consumers embed JAR (prefer `fatjar-shaded` classifier). No Kubernetes/Helm deployment in this repo.

---

## What NOT to Do

- Do **not** commit secrets, credentials, or private keys to source control (including `sdk.properties`).
- Do **not** add dependencies between sibling business modules.
- Do **not** put business logic in wiring/assembly modules (`sdk-example`, `sdk-loginterceptor`).
- Do **not** add `@Tag("flaky")` to new tests (excluded from CI).
- Do **not** break public API under `feed/livescout/interfaces/`, `entities/`, or `enums/` without bumping `${revision}` and documenting the change.
- Do **not** edit JAXB-generated sources under `sdk/target/generated-sources/jaxb/`.
- Do **not** edit generated Javadoc under `docs/` (updated by `publish-javadoc` CI job only).
- Do **not** remove `docs/` from GitLab `main` — GitHub Pages reads it via the GitLab→GitHub mirror.
- Do **not** run multiple `Sdk.getInstance()` processes with the same credentials.
- Do **not** publish `sdk-example`, `sdk-test`, or `sdk-loginterceptor` artifacts (excluded from Central publishing config).

---

## CI/CD

GitLab CI (`.gitlab-ci.yml`) includes `smp/iac/pipeline-common` (`common.yml` v4.5.0). Pipelines run on non–merge-request events only.

| Stage | Job | Notes |
|-------|-----|-------|
| `code_quality` | `sonar-validate` | Extends `.sonar-base`; Sonar key `ldsdk_ldsdk-java`; JaCoCo from `sdk-test/target/site/jacoco-aggregate/jacoco.xml` |
| `code_quality` | `whitesource-check-policies` | Mend/WhiteSource; `allow_failure: true`; skipped on master via `.rules-except-master` |
| `code_quality` | `whitesource-update` | Master branch only (`.rules-only-master`) |
| `publish` | `publish-javadoc` | **Manual** on protected branches; generates Javadoc directly into `docs/` via `-DsiteJavadocOutputDirectory`, commits to GitLab `main` |
| `publish` | `publish-maven-central` | **Manual** on protected branches; Corretto 21 image; GPG sign + `mvn deploy -DskipSiteJavadoc=false` with `.m2/settings.xml` |

**Sonar exclusions**: `**/livedata/sdk/example/**`, `**/livedata/sdk/test/util/**`, `**/livedata/sdk/loginterceptor/**`

**Deploy target**: Maven Central (`com.sportradar.livedata.sdk:sdk` with optional `fatjar-shaded` classifier). No application cluster deployment.

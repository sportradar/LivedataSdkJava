### Livedata SDK changelog
Livedata SDK is a client library that enables easier integration with the Livescout XML feed. SDK exposes XML feed service interface in a more user-friendly way and isolates the client from having to do XML feed parsing, proper connection handling, error recovery, event queuing and dispatching. 
It also makes a client solution more stable and robust when it comes to feed handling, especially with the release of new and updated XML feed version.* ScoutFeedType extended with new type - PARTIAL

**2.0.14 (2025-4-30)**
* Common-IAM login added. Credentials login used only if Common-IAM login fails.
* configurations refactored
* sdk.livescout.enabled is not needed anymore
* LiveScoutFeed.getMatchList sportIds type changed from Collection<Long> to Collection<Integer>

**2.0.13-2 (2025-3-30)**
* FormatType enum extended with new value - THREE_POINT_CONVERSION_YARD_LINE

**2.0.13-1 (2025-3-30)**
* Removed name in LatencyLevel enum
* EntityType enum removed
* Field type removed fromScoutEventEntity
* Refactoring in core classes: 
  - got rid of LiveScoutInjectionModule by moving logic into LiveScoutInjectionModule
  - some logic moved from JaxbLiveScoutEntityFactoryHelper to JaxbLiveScoutEntityFactory
  - added logs to file in parsing methods
  - LoggingInputStream and LoggingInputStreamFactory removed

**2.0.13 (2025-3-27)**
* New LatencyLevel enum added
* MatchHeaderEntity extended with new properties:
  - isRts
  - expectedLatencyLevel
* ScoutEventEntity extended with pointInGameNumber property

**2.0.12 (2025-3-25)**
* finalconfidence property for ScoutEventEntity is changed from string to integer
* PlayerStatisticsEntity extended with new properties:
  - homePlayerStatsSet1
  - homePlayerStatsSet2
  - homePlayerStatsSet3
  - homePlayerStatsSet4
  - homePlayerStatsSet5
  - awayPlayerStatsSet1
  - awayPlayerStatsSet2
  - awayPlayerStatsSet3
  - awayPlayerStatsSet4
  - awayPlayerStatsSet5
* StaticsEntity extended with new TeamPlayerStatsEntity

**2.0.11 (2024-10-18)**
* MatchUpdateEntity fields became nullable:
  - subteams
  - score
  - scores
  - events
* MatchUpdateEntity extended with new properties:
  - tryCounts
  - fouls
* ScoutEventEntity extended with finalConfidence property
* PlayerEntity extended with specificContracts property

**2.0.10 (2024-08-29)**
* New entity - TrackEntity under ScoutEventEntity that stores x,y,z coordinates
* unsubscribe method sends batch request now
* Flashconfig and Eventconfig has been removed from login
* ScoutEventEntity extended with new properties: playerId and ballEventType

**2.0.9 (2024-06-10)**
* ScoutEventEntity extended with new properties:
  - Statistics
  - OnDeck
  - InHole
  - NumberOfShots
  - PointOutcome
  - Situation
  - ErrorType
  - ShotSequence
  - PrimaryShotType
  - SecondaryShotType
  - LastStroke
  - ExtraInfoTennis
  - SupervisorAction
* New entities added:
  - Statistics
  - BattersStatsTotal
  - PitchersStatsTotal
  - TeamStats
  - PlayerStats
  - Stats
* New TeamStatsType enum added

**2.0.8-1 (2024-03-08)**
* FormatType enum extended with new value - ONE_POINT_CONVERSION_YARD_LINE

**2.0.8 (2024-02-26)**
* New entity - TeamStatisticsEntity under ScoutEventEntity that stores team statistics for TEAM_MATCH_STATS(1743) event:
  - homeTeamStats
  - awayTeamStats
  - homeTeamStatsTotal
  - awayTeamStatsTotal
  - homeTeamStatsP1
  - awayTeamStatsP1
  - homeTeamStatsP2
  - awayTeamStatsP2
  - homeTeamStatsP3
  - awayTeamStatsP3
  - homeTeamStatsP4
  - awayTeamStatsP4
  - homeTeamStatsOt
  - awayTeamStatsOt
  - homeTeamStatsI1
  - awayTeamStatsI1
  - homeTeamStatsI2
  - awayTeamStatsI2
  - homeTeamStatsI3
  - awayTeamStatsI3
  - homeTeamStatsI4
  - awayTeamStatsI4
  - homeTeamStatsI5
  - awayTeamStatsI5
  - homeTeamStatsI6
  - awayTeamStatsI6
  - homeTeamStatsI7
  - awayTeamStatsI7
  - homeTeamStatsI8
  - awayTeamStatsI8
  - homeTeamStatsI9
  - awayTeamStatsI9
  - homeTeamStatsIe
  - awayTeamStatsIe
* New entity - PlayerStatisticsEntity under ScoutEventEntity that stores player statistics for PLAYER_MATCH_STATS(1714) event
  - homePlayerStatsTotal
  - awayPlayerStatsTotal
  - homePlayerStatsP1
  - awayPlayerStatsP1
  - homePlayerStatsP2
  - awayPlayerStatsP2
  - homePlayerStatsP3
  - awayPlayerStatsP3
  - homePlayerStatsP4
  - awayPlayerStatsP4
  - homePlayerStatsOt
  - awayPlayerStatsOt
  - homePitchersStatsTotal
  - awayPitchersStatsTotal
  - homeBattersStatsTotal
  - awayBattersStatsTotal
* As player pitching statistics were moved to new class it is now reachable by event.getPlayerStatistics().getAwayPlayerStatsP2(), same for others
* As team pitching statistics were moved to new class it is now reachable by event.getTeamStatistics().getAwayTeamStatsP2(), same for others
* Reason and error code added to debug login failed message
* Method name fixed in ScoutEventEntity: gethomePlayers -> getHomePlayers
* ScoutEventEntity extended with new property - refsTime
* LineupsEntity extended with new property - preliminary

**2.0.7 (2024-01-10)**
* LiveScoutFeedListener extended with new method - onFullPaginatedMatchUpdateReceived
* New entity - PaginationEntity(uuid, page, totalPages)
* MatchHeaderEntity extended with new property - pagination
* ScoutFeedType extended with new enum value - FULL_PAGINATED
* ScoutEventEntity extended with new property - scorerNotConfirmed

**2.0.6-3 (2023-12-14)**
* logback-core updated to avoid CVE-2023-6481

**2.0.6-2 (2023-11-13)**
* Dependencies updated

**2.0.6-1 (2023-11-09)**
* LiveScoutFeedListener not used method removed - onPartialMatchUpdateReceived
* Maven plugins versions updated

**2.0.6 (2023-11-08)**
* MatchHeaderEntity extended with new property: region
* MatchUpdateEntity subteam -> subteams
* ScoutEventEntity extended with new properties:
  - homePlayerStats
  - awayPlayerStats

**2.0.5 (2023-09-20)**
* FormatType extended with new type - USES_OVERS
* MatchHeaderEntity getters names changed: setIsCancelled -> isCancelled, setIsTeamMatch -> isTeamMatch
* ScoutEventEntity extended with new properties: 
  - extraInfoSoccer
  - extraInfoWaterPolo
  - shotProjectedGoalRate
  - shotRatingCategory
  - shotSpeed
  - shotType
* ScoutEventEntity inningsScore will be null instead of empty collection
* EntityBase replaced with LiveScoutEntityBase
* LineupsEntity managers and teamOfficials fixed
* Lombok has been added: setters could be changed, getters remains unchanged

**2.0.4 (2023-06-15)**
* MatchHeaderEntity extended with new property - teamsReversed.
* ScoutEventEntity extended with new properties: strokeType, spin

**2.0.3 (2023-05-18)**
* LiveData update for Snooker.
* ScoutEvent extended with new properties: 
  - pointsUntilSnookerNeeded
  - pointsRemaining
  - reds
  - maxBreakFrame
  - maxBreakMatch
  - totalPitchCount
  - homeRunsHome
  - homeRunsAway
  - nextBatter
  - possibleBreak
  - firstBasePlayer
  - secondBasePlayer
  - thirdBasePlayer

**2.0.2 (2023-03-14)**
* LiveData update for MLB.
* ScoutEvent extended with new properties - FirstBasePlayer, SecondBasePlayer, ThirdBasePlayer, HomeRunsHome, HomeRunsAway, PrevPitcher, NextBatter, TotalPitchCount.

**2.0.1 (2023-01-26)**
* Max amount of matches per subscribtion added. If subscribtion request will exceed that amount, it will be split in multiple requests.
* MatchUpdateEntity extended with new property - subteam
* MatchHeaderEntity extended with new property - vbpClassification
* ScoutEventEntity extended with new property - pitchingSubstitution
* ScoutOddsFieldEntity, ScoutOddsEntity, OddsSuggestionsEntity has been removed

**2.0.0 (2022-11-17)**
* Based on Bookmaker SDK v1.24.0.0
* Supports only Livescout feed. Others feeds support was removed
* Packages were changed from **com.sportradar.sdk** to **com.sportradar.livedata.sdk**
* Java updated from 7 to 11.
* All libraries updated to the latest versions, which were available at 2022-10-01

### Bookmaker SDK changelog

**1.24.0.0 (2022-09-22)**
* LiveData update for Ice Hockey
* ScoutEvent extended with new properties - positionPlayerPitching, freeKickReason.
* Suspensions was replaced with new entity with powerplay under MatchUpdate.
* MatchHeader extended with new properties - team1Division, team2Division.

**1.23.0.0 (2022-08-25)**
* LiveData update for Basketball
* ScoutEvent extended with new properties - Spot, HomePlayers, AwayPlayers, HappenedAt, ScoreTypeQualifier, ShotDistance, TippedTo, FoulTypeDescriptor, FoulTypeQualifier

**1.22.0.0 (2022-07-14)**
* BM SDK config to use new endpoint for livedata replay server
* Sport id and match id filters added to match list request

**1.21.1.0 (2022-03-24)**
* BM SDK config to use new endpoint for livedata replay server
* Sport id and match id filters added to match list request
* istestmatch property added to MatchUpdate
* transferbookmakerid and transferuserid added to Login

**1.21.0.0 (2022-02-16)**
* LiveData update for Basketball
* ScoutEvent extended with new methods for properties - HomePlayerStatsTotal, AwayPlayerStatsTotal, HomePlayerStatsP1,
* AwayPlayerStatsP1, HomePlayerStatsP2, AwayPlayerStatsP2, HomePlayerStatsP3, AwayPlayerStatsP3, HomePlayerStatsP4, AwayPlayerStatsP4, HomePlayerStatsOt, AwayPlayerStatsOt, HomeTimeOnCourt, AwayTimeOnCourt
* LiveData update for Baseball
* ScoutEvent extended with new methods for properties - AtBatNumber, AtBatPitchNumber, BatBallDistance, BatBallX,
* BatBallY, ExtraInfoKabaddi, FieldingPlayers, PreliminaryMatchStatistics, ActualMatchStatistics, HomeTeamStatsTotal, AwayTeamStatsTotal, HomeTeamStatsP1, AwayTeamStatsP1, HomeTeamStatsP2, AwayTeamStatsP2, HomeTeamStatsP3, AwayTeamStatsP3, HomeTeamStatsP4, AwayTeamStatsP4, HomeTeamStatsOt, AwayTeamStatsOt
* LiveData - default production address changed to livedata.betradar.com

**1.20.0.0 (2021-12-10)**
* LiveData: added support for green cards (field hockey)

**1.19.0.0 (2021-11-26)**
* Changed log level from error to warn when received xml has unexpected character(s) between nodes (processing continues as before)
* Replaced dependent library reflection-maven (obsolete) with classpath
* Fix: common folder did not clean up (settings was ignored)

**1.18.0.0 (2021-10-04)**
* Live Data - new data points added to Ice Hockey (added Players Time on Ice event, added Attacking Players event, added Premium Data Availability event)
* Added getUnavailablePlayersHome, getUnavailablePlayersAway to ScoutEventEntity
* Added getHomeState, getAwayState and getVenue to MatchHeaderEntity

**1.17.0.0 (2021-08-18)**
* Live Data: American Football - New data points added

**1.16.0.0 (2021-05-17)**
* LiveData: Added MatchUpdateEntity.getScores() to get list of scores (may contain sub-score)

**1.15.0.0 (2020-11-19)**
* LiveData update for NFL - new method ScoutEventEntity.getDriveInfoStatus

**1.14.0.0 (2020-09-17)**
* LiveData: Added attributes for NFL and CS:GO

**1.13.0.0 (2020-08-11)**
* LiveOdds: Added new event status for MLB
* LiveData: Added FormatType.RegularInnings
* LiveData: Added support for new MLB match status

**1.12.0.0 (2020-06-22)**
* LiveData update for NFL Premium product
* Added MatchProperties and MatchTeams to MatchUpdate
* Added attributes for NFL Premium update - extended ScoutEventEntity

**1.11.1.0 (2020-03-03)**
* Fix: pitcherType was loaded from eventType

**1.11.0.0 (2020-02-11)**
* LiveData MatchHeaderEntity - added methods for ExtMatchId, Var, TeamMatch, TeamMatchId, IsCancelled
* LiveData ScoutHeaderEntity - added methods for Uuid, PitchType, PitchSpeed, BattingAverages, BatBallSpeed, BatBallAngle, BatBallDirection, Structure, MonsterType, DragonType, WardsPlaced, ChampionDamage
* Changing default pull intervals for LCoO to initial delay of 7s and regular delay of 12s
* Fix: loading CoveredFrom enum from feed message
* Fix: guard against null values in OutrightStatus.isLiteralValueEqual

**1.10.0.0 (2019-03-13)**
* LiveData update:
* added method getOrder to the PlayerEntity
* added method getMatchStatus to ScoutEventEntity
* added method getSportId to MatchHeaderEntity

**1.9.0.0 (2019-01-24)**
* Added NumberOfWinners property to the EventInfoEntity

**1.8.0.0 (2018-10-17)**
* Fix: updates LiveOdds schema to support zero TvChannels
* Updated schemas for LiveData and LiveOdds  update 2018.5
* Added AFTER_GOLDEN_SET and AWAITING_GOLDEN_SET to match status enum

**1.7.0.0 (2018-05-18)**
* Added Lcoo invalid message log entry
* In MatchHeader type of property Delivery changed from Team to Integer to support cricket values
* Added value 1020-SurfaceType to EventType enum

**1.6.20.0 (2018-03-26)**
* New FeedEventType.AUTHENTICATED
* New LiveScout properties Goals, Behinds

**1.6.19.0 (2017-12-22)**
* New LiveScout properties exposure

**1.6.18.0 (2017-09-18)**
* Enum EventType marked deprecated
* Property type on ScoutEventEntity marked deprecated
* Added typeId property to ScoutEventEntity
* Fixed populating virtualGameId for virtual sports

**1.6.17.0 (2017-08-29)**
* Added EXTREME value in WeatherConditions enum
* Added properties TouchdownType and ConversionType to ScoutEventEntity

**1.6.16.0 (2017-07-31)**
* Logback rolling policy file name pattern fix
* Lcoo: HttpClient system properties support
* Scouttest port change to 2047
* Updated OddsCreator wsdl  added method getTennisMatchInfo

**1.6.15.2 (2017-06-14)**
* Added support for HttpClient system properties LcOo

**1.6.15.1 (2017-06-12)**
* Logback configuration hotfix

**1.6.15.0 (2017-05-30)**
* LiveOdds update
    - added statusId property on the EventHeaderEntity
* LiveScout update
    - added coverageStatusId property on the EventHeaderEntity
    - added support for NetWorth and Gold on the MatchUpdateEntity
    - fixed the exposure of innings, they are now exposed as a List
* Minor shading improvement
* Example updated with LiveOddsWithOutrights VFC

**1.6.14.0 (2017-04-25)**
* Upgraded logback library to version 1.2.3
3rd party libraries are shaded within jar

**1.6.13.0 (2017-03-28)**
* Fixed settings of VirtualGameId and RaceDayNumber in virtual sports
* Added UniqueId to home and away team property

**1.6.12.0 (2017-03-21)**
* Added HomeCompetitors and AwayCompetitors to MatchInfo entity

**1.6.11.0 (2017-01-31)**
* To LiveOdds feed added CoveredFrom property  indicates whether the match is being covered from a television feed or the scout is at the match venue

**1.6.10.0 (2017-01-16)**
* LCoO dispatch correctly even with multiple dispatcher threads

**1.6.9.0 (2017-01-03)**
* Dispatch outright status pack:true correctly
* Workaround for wrong odds type in translation message for virtual feeds
* Added DISPATCHER_FULL value to FeedEventType enum for OnFeedEvent
* Read dispatcher settings also for LCoO

**1.6.8.0 (2016-10-05)**
* LiveOdds XSD has changed

**1.6.7.0 (2016-10-04)**
* LiveOdds/LiveScout 2016.5 release changes

**1.6.6.0 (2016-09-15)**
* LCoO schema changed, void factor is now String
* Increased default max message size from 10 to 20MB

**1.6.5.0 (2016-07-26)**
* LiveOdds/LiveScout 2016.4 release changes

**1.6.4.0 (2016-07-06)**
* LCoO fetch URL changed from www.betradar.com to getxml.betradar.com
* LiveOddsTestManager endAll method now sends Endall instead of EndAll

**1.6.3.0 (2016-06-23)**
* VFC clearedScore element has moved from Odds to Match element

**1.6.2.0 (2016-05-26)**
* LiveScout XSD was updated, made according changes in the SDK

**1.6.1.0 (2016-05-23)**
* LiveOdds/LiveScout 2016.3 release changes
* OutrightStatus cancelled -> canceled
* VFC set match score

**1.6.0.0 (2016-05-05)**
* Virtual Football Cup support

**1.5.4.0 (2016-04-13)**
* LiveOdds/LiveScout 2016.2 release changes
* LCoO added getAAMSCalendarID
* With introduction of new sport there are over 100 new score types so we changed MatchUpdateEntity.getScore  from Map<ScoreType, HomeAway<Double>> to Map<String, HomeAway<Double>>
* Duplicate selections will only be logged, no parse error
* Changed default setting of disconnect_on_parse_error to false

**1.5.3.0 (2016-03-23)**
* LiveOdds connection parameters have changed. liveodds.betradar.com:1981/1980 -> liveplex.betradar.com:1961/1960. Check your firewall rules
* Set match request limits to correct numbers and other rate limiter improvements

**1.5.2.0 (2016-03-16)**
* Error manager could get blocked. Now it will instead retry on next alive.
* Added support for multiple matches per current request
* Extended Hand enum as Pitcher hand can be L or R

**1.5.1.0 (2016-02-15)**
* LiveOdds/LiveScout 2016.1 release changes
* LiveScout use matchsubscription XML element to subscribe
* Client requests will now be blocked if request limits were exceeded

**1.5.0.0 (2015-12-08)**
* LiveOdds/LiveScout 2015.6 release changes
* LCoO propagate parse and connection problems to the user over onFeedEvent

**1.4.7.0 (2015-12-03)**
* Fixed an issue with non SSL connections

**1.4.6.0 (2015-11-10)**
* VHC now uses vsportsodds.betradar.com url as default
* cancelbet was added to VHC and VDR schema

**1.4.5.0  .2015-10-19)**
* LiveOdds/LiveScout 2015.5 release changes
* Feeds will now restart on any parse error by default

**1.4.4.0 (2015-09-25)**
* Fixed error manager to invalidate all data on disconnect
* Fixed LiveOddsTestManager startScenario method
* LiveOddsTestManager changeXmlConfig support added

**1.4.3.0 (2015-08-19)**
* LiveOdds/LiveScout 2015.4 release changes

**1.4.2.0 (2015-08-05)**
* Extended LiveScout EventType with new events
* Fixed issue where null logger level could produce NPE

**1.4.1.0 (2015-07-16)**
* Bugfix: SDK failed to start if client was using non logback SLF4J implementation

**1.4.0.0 (2015-07-06)**
* Virtual Basketball League support
* Bugfix : LiveOddsTestManager startScenario had wrong input check
* Bugfix : Client unhandled exception in dispatching package events killed dispatch thread

**1.3.6.0 (2015-06-16)**
* LiveOdds/LiveScout 2015.3 release changes
* Bugfix : BetCancelUndo start and end time were not being set
* Bugfix : LCoO failed sport parsing could stop fetching thread
* ScoutMatchStatus now has terminal states exposed to the client
* LCoO new configuration options : connection timeout, socket timeout, keep alive

**1.3.5.0 (2015-06-04)**
* LiveFeed interface now has getConnectionParams method which returns underlying connection information
* Fixed a bug where uncaught client exception in onOpened could cause spamming of CT messages
* Fixed LiveScout test manager
* Fixed LiveScout artificial betstop dispatching
* onOpened, onClosed, onInitialized, onFeedEvent are now dispatched by dispatcher threads
* Removed BEFORE_DISCONNECT event from FeedEventType
* Dispatcher queue errors are now also logged to execution loggers

**1.3.4.0 (2015-05-18)**
* LiveOdds schema was missing "awaiting_sd" status
* LiveScout enum EventType updated with more types
* Bug fix: SDK logging level could be null
* Bug fix: LCoO FeedType tournament added
* Changed the logging format of invalid entities
* Correctly inform the user if DOCTYPE is found for LCoO feed

**1.3.3.0 (2015-04-29)**
* Added hashCode and equals to some entities
* VHC staging hostname fix
* No waiting on first connection try
* Bug fix BetClear odds were not being translated
* LCoO FeedType added to distinguish between full and delta
* ScoreType enum value fix

**1.3.2.0 (2015-04-13)**
* Log all client method calls
* LCoO fixture timezone in now configurable

**1.3.1.0 (2015-03-31)**
* Updated OddsCreator wsdl
* Added LCoO StatusInfoEntity
* Fixed LiveOddsTracker to handle daylight saving time clock changes

**1.3.0.0 (2015-03-19)**
* Fixed Serializable
* Extended LiveOddsTestManager
* Removed onScoutInfoReceived from LiveScoutFeedListener

**1.2.4.0 (2015-03-06)**
* Added default constructors to support Serializable
* Fixed closing of feeds, it should now dispatch BetStops

**1.2.3.0 (2015-02-10)**
* Translation are matched by typeId only if translation_fallback_to_typeId is set to true
* Made changes for 2015.1 LiveOdds/LiveScout release

**1.2.2.0 (2015-01-29)**
* Translation are matched by typeId, if matching by type fails
* LiveScout supports requesting server time
* LiveOddsTestManager added reset xml config request to force update check

**1.2.1.0 (2015-01-08)**
* LiveOdds bug fix: ScoreEntryEntity Time was not being set
* LCoO updated to latest schema
* All entities implement Serializable

**1.2.0.0 (2014-12-04)**
* Minor breaking change, LiveScout typos fixes, moved LocalizedString and TypeValueTuple
* Jar has now some dependencies shaded to prevent conflicts
* LiveOdds/LiveScout release 2014.6 changes
* Javadoc fixes, adding SDK page as javadoc source should now work
* SDK now has requested getVersion method

**1.1.9.0 (2014-11-12)**
* Possession can be -1, Team enum fixed
* Using Guice 4 beta 5 as it fixes https://github.com/google/guice/issues/62

**1.1.8.0 (2014-10-20)**
* Made changes for 2014.5 LiveOdds/LiveScout release
* Changes to error manager
* onInitialized is called after every connect

**1.1.7.0 (2014-09-30)**
* Updated LCoO to the latest schema

**1.1.6.0 (2014-09-22)**
* after_sd was missing from schema

**1.1.5.0 (2014-09-16)**
* Reverted two-phase commit back to simple one step pull

**1.1.4.0 (2014-09-15)**
* LCoO two-phase commit fix
* Increased LCoO logging
* EventInfoEntity was missing getEventEndDate

**1.1.3.0 (2014-09-02)**
* LiveScout onMatchData fix

**1.1.2.0 (2014-09-01)**
* Error manager rewrite
* LiveScout was missing onMatchData

**1.1.1.0 (2014-08-14)**
* Virtual Tennis Open support
* Made changes for 2014.4 LiveOdds/LiveScout release

**1.1.0.0 (2014-06-27)**
* Latest LO/LS version supported
* Virtual Dog Racing added
* Race related feed handling simplified  and interface changed a bit
* LivePlex & BetPal support added
* Life Cycle of Odds transactional modes  two-phase commit with automatic or manual commit
* Late bet stop option for LiveOdds protocols

**1.0.9.0 (2014-02-12)**
* Be able to use in-memory settings during initialization  instead of sdk.properties file on classpath
* VHC error recovery race-condition fix
* Workaround for a server issue: specialoddsvalue -1 is sent although it is actually null
* Documentation improvements

**1.0.8.0 (2014-02-03)**
* Logback had a SLF4J binding by default with interfered when clients wanted to use the SDK library with different logging frameworks  e.g. log4j
* Correlation id was not used for "scorecardsummary"
* There can be multiple "racedays" in VHC meta
* OddsCreator updated  supports volleyball

**1.0.7.0 (2014-01-27)**
* LiveOdds error recovery was not working under certain conditions  users are strongly encouraged to upgrade to this release
* You can now limit total number of stashed elements  sdk.global.stash_soft_limit and stash_hard_limit

**1.0.6.0 (2014-01-15)**
* VHC schema on server side has been changed  so a few entities were not filled-up correctly
* Fixed LCoO xml feed name

**1.0.5.0 (2014-01-13)**
* VHC translations improved
* Test manager for test feeds fixed

**1.0.4.0 (2014-01-06)**
* OddsCreator feed implemented
* Every feed has its own logger now
* VHC parsing improvements
* Support test feeds

**1.0.3.0 (2014-01-03)**
* Various bug fixes

**1.0.2.0 (2013-12-27)**
* Experimental LiveOdds VHC support added
* Improved system tests

**1.0.1.0 (2013-12-16)**
* Life Cycle Of Odds provider rewritten to use an incremental stream oriented parser  to decrease memory usage
* LiveOdds error manager logic fixed

**1.0.0.0 (2013-12-09)**
* Release of the first version
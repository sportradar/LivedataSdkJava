package com.sportradar.sdk.feed.livescout.enums;

import com.sportradar.sdk.common.interfaces.EntityEnum;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Event type enum
 * @deprecated
 * Please use the appropriate event typeId instead and map typeId to event type based on sport in according to feed documentation
 */
public enum EventType implements EntityEnum {

    /**
     * Unknown, check event id
     */
    UNKNOWN("-1"),
    /**
     * Temporary interruption
     */
    TEMPORARY_INTERRUPTION("15"),
    /**
     * Game on
     */
    GAME_ON("16"),
    /**
     * Goal
     */
    GOAL("30"),
    /**
     * Yellow card
     */
    YELLOW_CARD("40"),
    /**
     * Suspension
     */
    SUSPENSION("43"),
    /**
     * Yellow and red card
     */
    YELLOWRED_CARD("45"),
    /**
     * Blue card
     */
    BLUE_CARD("48"),
    /**
     * Red card
     */
    RED_CARD("50"),
    /**
     * Substitution
     */
    SUBSTITUTION("60"),
    /**
     * Injury time
     */
    INJURY_TIME("90"),
    /**
     * Ball possession
     */
    BALL_POSSESSION("110"),
    /**
     * Free kick count
     */
    FREE_KICK_COUNT("120"),
    /**
     * Goal kick count
     */
    GOAL_KICK_COUNT("121"),
    /**
     * Throw in count
     */
    THROW_IN_COUNT("122"),
    /**
     * Offside count
     */
    OFFSIDE_COUNT("123"),
    /**
     * Corner kick count
     */
    CORNER_KICK_COUNT("124"),
    /**
     * Shot on target count
     */
    SHOT_ON_TARGET_COUNT("125"),
    /**
     * Shot off target count
     */
    SHOT_OFF_TARGET_COUNT("126"),
    /**
     * Goalkeeper save count
     */
    GOALKEEPER_SAVE_COUNT("127"),
    /**
     * Foul at Basketball
     */
    FOUL("129"),
    /**
     * Fouls count at Soccer
     */
    FOULS_COUNT("129"),
    /**
     * Free kick
     */
    FREE_KICK("150"),
    /**
     * Goal kick
     */
    GOAL_KICK("151"),
    /**
     * Throw in
     */
    THROW_IN("152"),
    /**
     * Offside
     */
    OFFSIDE("153"),
    /**
     * Corner
     */
    CORNER("154"),
    /**
     * Shot on target
     */
    SHOT_ON_TARGET("155"),
    /**
     * Shot off target
     */
    SHOT_OFF_TARGET("156"),
    /**
     * Goalkeeper save
     */
    GOALKEEPER_SAVE("157"),
    /**
     * Injury
     */
    INJURY("158"),
    /**
     * 7 meter throw at Handball
     */
    SEVEN_METER_THROW("161"),
    /**
     * Penalty awarded at Futsal
     */
    PENALTY_AWARDED("161"),
    /**
     * Penalty shootout at Ice Hockey
     */
    PENALTY_SHOOTOUT("161"),
    /**
     * Weather conditions
     */
    WEATHER_CONDITIONS("164"),
    /**
     * Attendance
     */
    ATTENDANCE("165"),
    /**
     * Player back from injury
     */
    PLAYER_BACK_FROM_INJURY("168"),
    /**
     * Shot blocked count
     */
    SHOT_BLOCKED_COUNT("171"),
    /**
     * Shot blocked
     */
    SHOT_BLOCKED("172"),
    /**
     * 7 meter throw missed at Handball
     */
    SEVEN_METER_THROW_MISSED("666"),
    /**
     * Penalty missed at Soccer
     */
    PENALTY_MISSED("666"),
    /**
     * Penalty shootout missed at Ice Hockey
     */
    PENALTY_SHOOTOUT_MISSED("666"),
    /**
     * Penalty shootout event deep coverage
     */
    PENALTY_SHOOTOUT_EVENT_DEEP_COVERAGE("1002"),
    /**
     * Bet start
     */
    BET_START("1010"),
    /**
     * Bet stop
     */
    BET_STOP("1011"),
    /**
     * Kick-off team at Soccer
     */
    KICKOFF_TEAM("1012"),
    /**
     * Which team starts with possession at Ice Hockey
     */
    WHICH_TEAM_STARTS_WITH_POSSESSION("1012"),
    /**
     * Match status
     */
    MATCH_STATUS("1013"),
    /**
     * Pitch conditions
     */
    PITCH_CONDITIONS("1014"),
    /**
     * Free text
     */
    FREE_TEXT("1015"),
    /**
     * Possible corner
     */
    POSSIBLE_CORNER("1016"),
    /**
     * Cancelled corner
     */
    CANCELLED_CORNER("1017"),
    /**
     * Possible goal
     */
    POSSIBLE_GOAL("1018"),
    /**
     * Cancelled goal
     * Extra info: 0 or -1(not specified, 1(off side, 2(foul, 3(incorrect entry, 4(out of bounds, 5(play stopped
     */
    CANCELLED_GOAL("1019"),
    /**
     * Surface type
     */
    SURFACE_TYPE("1020"),
    /**
     * Which team serves first
     */
    WHICH_TEAM_SERVES_FIRST("1022"),
    /**
     * Match about to start
     */
    MATCH_ABOUT_TO_START("1024"),
    /**
     * Tennis score change
     */
    TENNIS_SCORE_CHANGE("1025"),
    /**
     * Cancelled point
     */
    CANCELLED_POINT("1028"),
    /**
     * Dangerous attack
     */
    DANGEROUS_ATTACK("1029"),
    /**
     * Ball safe
     */
    BALL_SAFE("1030"),
    /**
     * Ball in play at Volleyball and Beach Volleyball
     */
    BALL_IN_PLAY("1031"),
    /**
     * Ball in play / Service taken at Tennis
     */
    BALL_IN_PLAY_SERVICE_TAKEN("1031"),
    /**
     * Tennis service fault
     */
    TENNIS_SERVICE_FAULT("1032"),
    /**
     * Who won jump ball
     */
    WHO_WON_JUMP_BALL("1033"),
    /**
     * Rebound
     */
    REBOUND("1034"),
    /**
     * Timeout
     */
    TIMEOUT("1035"),
    /**
     * Time start/stop
     */
    TIME_STARTSTOP("1036"),
    /**
     * Basketball score change
     */
    BASKETBALL_SCORE_CHANGE("1037"),
    /**
     * Basketball score miss
     */
    BASKETBALL_SCORE_MISS("1038"),
    /**
     * Manual time adjustment
     */
    MANUAL_TIME_ADJUSTMENT("1039"),
    /**
     * Possible red card
     */
    POSSIBLE_RED_CARD("1040"),
    /**
     * Cancelled red card
     */
    CANCELLED_RED_CARD("1041"),
    /**
     * Possible 7 meter throw at Handball
     */
    POSSIBLE_7_METER_THROW("1042"),
    /**
     * Possible penalty at Soccer, Ice Hockey and Futsal
     */
    POSSIBLE_PENALTY("1042"),
    /**
     * Cancelled penalty
     */
    CANCELLED_PENALTY("1043"),
    /**
     * Deleted event alert
     */
    DELETED_EVENT_ALERT("1044"),
    /**
     * Volleyball score change
     */
    VOLLEYBALL_SCORE_CHANGE("1046"),
    /**
     * Timeout over
     */
    TIMEOUT_OVER("1047"),
    /**
     * Suspension over
     */
    SUSPENSION_OVER("1049"),
    /**
     * Possible empty net situation
     * Events (POSSIBLE_EMPTY_NET_SITUATION and EMPTY_NET_SITUATION) were switched on 2014-12-02
     */
    POSSIBLE_EMPTY_NET_SITUATION("1050"),
    /**
     * Empty net situation
     */
    EMPTY_NET_SITUATION("1051"),
    /**
     * Empty net situation over
     */
    EMPTY_NET_SITUATION_OVER("1052"),
    /**
     * Empty net situation cancelled
     */
    EMPTY_NET_SITUATION_CANCELLED("1053"),
    /**
     * Free throw
     */
    FREE_THROW("1054"),
    /**
     * Number of free throws
     */
    NUMBER_OF_FREE_THROWS("1056"),
    /**
     * Who serves first in tie break set
     */
    WHO_SERVES_FIRST_IN_TIE_BREAK_SET("1058"),
    /**
     * Direct foul
     */
    DIRECT_FOUL("1059"),
    /**
     * Direct free kick
     */
    DIRECT_FREE_KICK("1060"),
    /**
     * Service taken
     */
    SERVICE_TAKEN("1061"),
    /**
     * Ball position
     */
    BALL_POSITION("1062"),
    /**
     * Play resumes after goal
     */
    PLAY_RESUMES_AFTER_GOAL("1064"),
    /**
     * Current server volleyball
     */
    CURRENT_SERVE_VOLLEYBALL("1081"),
    /**
     * Enable/disable corner markets
     */
    ENABLE_DISABLE_CORNER_MARKETS("1082"),
    /**
     * Enable/disable booking markets
     */
    ENABLE_DISABLE_BOOKING_MARKETS("1083"),
    /**
     * Possible yellow card
     */
    POSSIBLE_YELLOW_CARD("1084"),
    /**
     * Cancelled yellow card
     */
    CANCELLED_YELLOW_CARD("1085"),
    /**
     * Scrum
     */
    SCRUM("1090"),
    /**
     * Early bet status
     */
    EARLY_BETSTATUS("1091"),
    /**
     * Scrum outcome
     */
    SCRUM_OUTCOME("1092"),
    /**
     * Missed rugby point
     */
    MISSED_RUGBY_POINT("1096"),
    /**
     * Line out
     */
    LINE_OUT("1097"),
    /**
     * Coverage status
     */
    COVERAGE_STATUS("1102"),
    /**
     * Penalty shootout starting team
     */
    PENALTY_SHOOTOUT_STARTING_TEAM("1104"),
    /**
     * Ball change frequency
     */
    BALL_CHANGE_FREQUENCY("1106"),
    /**
     * Chair umpire on court (Tennis)
     */
    CHAIR_UMPIRE_ON_COURT("1107"),
    /**
     * Break due to extreme weather conditions (Tennis)
     */
    BREAK_DUE_TO_EXTREME_WEATHER_CONDITIONS("1108"),
    /**
     * Break due to extreme weather conditions over (Tennis)
     */
    BREAK_DUE_TO_EXTREME_WEATHER_CONDITIONS_OVER("1109"),
    /**
     * Toilet break / change of attire (Tennis)
     */
    TOILET_BREAK_OR_CHANGE_OF_ATTIRE("1110"),
    /**
     * Toilet break / change of attire over (Tennis)
     */
    TOILET_BREAK_OR_CHANGE_OF_ATTIRE_OVER("1111"),
    /**
     * Match stop / suspension (Tennis)
     * Extra info:
     * 3(injury), 4(flood light failure), 5(persons entering field of play), 6 (crowd control issue),
     * 7(water break), 8(diciplinary issue), 9(gone to TMO (video referee))
     */
    MATCH_STOP_OR_SUSPENSION("1112"),
    /**
     * Match stop / suspension over (Tennis)
     */
    MATCH_STOP_OR_SUSPENSION_OVER("1113"),
    /**
     * Code violation (Tennis)
     */
    CODE_VIOLATION("1115"),
    /**
     * Time violation (Tennis)
     */
    TIME_VIOLATION("1116"),
    /**
     * Trainer called (Tennis)
     */
    TRAINER_CALLED("1118"),
    /**
     * Trainer called finished (Tennis)
     */
    TRAINER_CALLED_FINISHED("1119"),
    /**
     * Who won coin toss (Tennis)
     */
    WHO_WON_COIN_TOSS("1120"),
    /**
     * Who made first server decision (Tennis)
     */
    WHO_MADE_FIRST_SERVER_DECISION("1121"),
    /**
     * Which player serves first within a doubles team (Tennis)
     */
    WHICH_PLAYER_SERVES_FIRST_WITHIN_A_DOUBLES_TEAM("1122"),
    /**
     * Match called (Tennis)
     */
    MATCH_CALLED("1124"),
    /**
     * Which player receives first within a doubles team (Tennis)
     */
    WHICH_PLAYER_RECEIVES_FIRST_WITHIN_A_DOUBLES_TEAM("1125"),
    /**
     * Attack (Tennis)
     */
    ATTACK("1126"),
    /**
     * Replay of point (Tennis)
     */
    REPLAY_OF_POINT("1127"),
    /**
     * Which team starts serving on the left hand side of the umpire (Tennis)
     */
    WHICH_TEAM_STARTS_SERVING_ON_THE_LEFT_HAND_SIDE_OF_THE_UMPIRE("1128"),
    /**
     * Darts score change
     */
    DARTS_SCORE_CHANGE("1130"),
    /**
     * Single dart throw
     */
    SINGLE_DART_THROW("1132"),
    /**
     * Best of legs
     */
    BEST_OF_LEGS("1135"),
    /**
     * Best of frames
     */
    BEST_OF_FRAMES("1136"),
    /**
     * Snooker score change
     */
    SNOOKER_SCORE_CHANGE("1137"),
    /**
     * Unscheduled break
     */
    UNSCHEDULED_BREAK("1139"),
    /**
     * Badminton score change
     */
    BADMINTON_SCORE_CHANGE("1141"),
    /**
     * Black card
     */
    BLACKCARD("1142"),
    /**
     * Ball recycled
     */
    BALL_RECYCLED("1143"),
    /**
     * Ball kicked
     */
    BALL_KICKED("1144"),
    /**
     * New phase
     */
    NEW_PHASE("1145"),
    /**
     * Scrum reset
     */
    SCRUM_RESET("1146"),
    /**
     * Line out won
     */
    LINE_OUT_WON("1147"),
    /**
     * Tap and go
     */
    TAP_AND_GO("1148"),
    /**
     * Reset phase count
     */
    RESET_PHASE_COUNT("1149"),
    /**
     * Kick to touch
     */
    KICK_TO_TOUCH("1150"),
    /**
     * Twenty two drop out
     */
    TWENTYTWO_DROP_OUT("1151"),
    /**
     * Temperature
     */
    TEMPERATURE("1152"),
    /**
     * Wind
     */
    WIND("1153"),
    /**
     * Try
     */
    TRY("1154"),
    /**
     * Penalty try
     */
    PENALTY_TRY("1155"),
    /**
     * Conversion
     */
    CONVERSION("1156"),
    /**
     * Penalty points
     */
    PENALTY_POINTS("1157"),
    /**
     * Drop goal
     */
    DROP_GOAL("1158"),
    /**
     * Drop goal from mark
     */
    DROP_GOAL_FROM_MARK("1159"),
    /**
     * Table tennis score change
     */
    TABLE_TENNIS_SCORE_CHANGE("1161"),
    /**
     * Turnover
     */
    TURNOVER("1162"),
    /**
     * Wind advantage
     */
    WIND_ADVANTAGE("1163"),
    /**
     * Going for kick at goal
     */
    GOING_FOR_KICK_AT_GOAL("1164"),
    /**
     * Television match official assists
     */
    TELEVISION_MATCH_OFFICIAL_ASSISTS("1165"),
    /**
     * Drop goal attempt
     */
    DROP_GOAL_ATTEMPT("1166"),
    /**
     * Denied try
     */
    DENIED_TRY("1167"),
    /**
     * Denied drop goal
     */
    DENIED_DROP_GOAL("1168"),
    /**
     * Ball pot
     */
    BALL_POT("1169"),
    /**
     * Free ball
     */
    FREE_BALL("1170"),
    /**
     * Snooker foul
     */
    SNOOKER_FOUL("1171"),
    /**
     * Rerack
     */
    RERACK("1173"),
    /**
     * Remove ball
     */
    REMOVE_BALL("1174"),
    /**
     * Add ball
     */
    ADD_BALL("1175"),
    /**
     * Expedite mode
     */
    EXPEDITE_MODE("1176"),
    /**
     * Table tennis violation
     */
    TABLE_TENNIS_VIOLATION("1177"),
    /**
     * Players walk on
     */
    PLAYERS_WALK_ON("1179"),
    /**
     * Table ready
     */
    TABLE_READY("1186"),
    /**
     * Play about to start
     */
    PLAY_ABOUT_TO_START("1187"),
    /**
     * Possible try
     */
    POSSIBLE_TRY("1195"),
    /**
     * Penalty advantage (used in Rugby)
     */
    PENALTY_ADVANTAGE("1196"),
    /**
     * Drop out (used in Rugby)
     */
    DROP_OUT("1200"),
    /**
     * 20m restart (used in Rugby)
     */
    TWENTY_M_RESTART("1201"),
    /**
     * 40-20 (used in Rugby)
     */
    FORTY_TWENTY("1202"),
    /**
     * Free ball awarded (snooker)
     */
    FREE_BALL_AWARDED("1203"),
    /**
     * Maul
     */
    MAUL("1204"),
    /**
     * Conversion position
     */
    CONVERSION_POSITION("1205"),
    /**
     * Linebreak
     */
    LINEBREAK("1206"),
    /**
     * Penalty reversed
     */
    PENALTY_REVERSED("1207"),
    /**
     * Toss
     */
    TOSS("1300"),
    /**
     * Session started
     */
    SESSION_STARTED("1301"),
    /**
     * Session finished
     */
    SESSION_FINISHED("1302"),
    /**
     * Innings started
     */
    INNINGS_STARTED("1303"),
    /**
     * Innings finished
     */
    INNINGS_FINISHED("1304"),
    /**
     * Over started
     */
    OVER_STARTED("1305"),
    /**
     * Over finished
     */
    OVER_FINISHED("1306"),
    /**
     * Bowler running in
     */
    BOWLER_RUNNING_IN("1307"),
    /**
     * Ball finished
     */
    BALL_FINISHED("1308"),
    /**
     * Pitch map
     */
    PITCH_MAP("1309"),
    /**
     * Ball hit
     */
    BALL_HIT("1310"),
    /**
     * Dot ball
     */
    DOT_BALL("1311"),
    /**
     * Boundary
     */
    BOUNDARY("1312"),
    /**
     * Runs
     */
    RUNS("1313"),
    /**
     * One short
     */
    ONE_SHORT("1314"),
    /**
     * Extras no ball
     */
    EXTRAS_NO_BALL("1315"),
    /**
     * Extras wide
     */
    EXTRAS_WIDE("1316"),
    /**
     * Extras bye
     */
    EXTRAS_BYE("1317"),
    /**
     * Extras bye bye
     */
    EXTRAS_BYE_BYE("1318"),
    /**
     * Extras penalty runs
     */
    EXTRAS_PENALTY_RUNS("1319"),
    /**
     * Free hit
     */
    FREE_HIT("1320"),
    /**
     * Dismissal retired
     */
    DISMISSAL_RETIRED("1321"),
    /**
     * Dismissal bowled
     */
    DISMISSAL_BOWLED("1322"),
    /**
     * Dismissal timed out
     */
    DISMISSAL_TIMED_OUT("1323"),
    /**
     * Dismissal caught
     */
    DISMISSAL_CAUGHT("1324"),
    /**
     * Dismissal handled by the ball
     */
    DISMISSAL_HANDLED_THE_BALL("1325"),
    /**
     * Dismissal hit the ball twice
     */
    DISMISSAL_HIT_THE_BALL_TWICE("1326"),
    /**
     * Dismissal hit wicket
     */
    DISMISSAL_HIT_WICKET("1327"),
    /**
     * Dismissal lbw
     */
    DISMISSAL_LBW("1328"),
    /**
     * Dismissal obstructing the field
     */
    DISMISSAL_OBSTRUCTING_THE_FIELD("1329"),
    /**
     * Dismissal run out
     */
    DISMISSAL_RUN_OUT("1330"),
    /**
     * Dismissal stumped
     */
    DISMISSAL_STUMPED("1331"),
    /**
     * Dead ball
     */
    DEAD_BALL("1332"),
    /**
     * Possible wicket
     */
    POSSIBLE_WICKET("1333"),
    /**
     * Possible boundary
     */
    POSSIBLE_BOUNDARY("1334"),
    /**
     * Appeal
     */
    APPEAL("1335"),
    /**
     * Third umpire
     */
    THIRD_UMPIRE("1336"),
    /**
     * Decision review
     */
    DECISION_REVIEW("1337"),
    /**
     * Decision review result
     */
    DECISION_REVIEW_RESULT("1338"),
    /**
     * Batsman coming on
     */
    BATSMAN_COMING_ON("1339"),
    /**
     * Bowler change
     */
    BOWLER_CHANGE("1340"),
    /**
     * Retired hurt
     */
    RETIRED_HURT("1341"),
    /**
     * Follow on
     */
    FOLLOW_ON("1342"),
    /**
     * New ball
     */
    NEW_BALL("1343"),
    /**
     * Duckworth lewis
     */
    DUCKWORTH_LEWIS("1344"),
    /**
     * Correct Batsman data (Cricket)
     */
    CORRECT_BATSMAN_DATA("1345"),
    /**
     * Correct bowler data (Cricket)
     */
    CORRECT_BOWLER_DATA("1346"),
    /**
     * Correct team data (Cricket)
     */
    CORRECT_TEAM_DATA("1347"),
    /**
     * Correct batsman on strike (Cricket)
     */
    CORRECT_BATSMAN_ON_STRIKE("1348"),
    /**
     * Correct ball data (Cricket)
     */
    CORRECT_BALL_DATA("1349"),
    /**
     * Reduced overs match (Cricket)
     */
    REDUCED_OVERS_MATCH("1350"),
    /**
     * Power play started (Cricket)
     */
    POWER_PLAY_STARTED("1351"),
    /**
     * Power play finished (Cricket)
     */
    POWER_PLAY_FINISHED("1352"),
    /**
     * PPlay abandoned for the day
     */
    PLAY_ABANDONED_FOR_THE_DAY("1353"),
    /**
     * Defensive foul (Handball)
     */
    DEFENSIVE_FOUL("1411"),
    /**
     * Steal (Handball)
     */
    STEAL("1412"),
    /**
     * Technical ball handling fault (TF) (Handball)
     */
    TECHNICAL_FOUL("1413"),
    /**
     * Technical rule fault (TRF) (Handball)
     */
    TECHNICAL_FAULT("1414"),
    /**
     * Exclusion (Handball)
     */
    EXCLUSION("1415"),
    /**
     * Possible suspension (Handball)
     */
    POSSIBLE_SUSPENSION("1416"),
    /**
     * Possible exclusion (Handball)
     */
    POSSIBLE_EXCLUSION("1417"),
    /**
     * Cancelled suspension (Handball)
     */
    CANCELLED_SUSPENSION("1418"),
    /**
     * Safety
     */
    SAFETY("1421"),
    /**
     * Extra point
     */
    EXTRA_POINT("1423"),
    /**
     * Possible field goal
     */
    POSSIBLE_FIELD_GOAL("1424"),
    /**
     * Canceled field goal
     */
    CANCELED_FIELD_GOAL("1425"),
    /**
     * Field goal result
     */
    FIELD_GOAL_RESULT("1426"),
    /**
     * Turnover af
     */
    TURNOVER_AF("1427"),
    /**
     * Fumble
     */
    FUMBLE("1428"),
    /**
     * Cancelled exclusion (Handball)
     */
    CANCELLED_EXCLUSION("1419"),
    /**
     * Interception
     */
    INTERCEPTION("1429"),
    /**
     * Play start af
     */
    PLAY_START_AF("1430"),
    /**
     * Play over af
     */
    PLAY_OVER_AF("1431"),
    /**
     * Punt result
     */
    PUNT_RESULT("1432"),
    /**
     * Challenge af
     */
    CHALLENGE_AF("1433"),
    /**
     * Possible challenge
     */
    POSSIBLE_CHALLENGE("1434"),
    /**
     * Penalty
     */
    PENALTY("1435"),
    /**
     * Possible touchdown
     */
    POSSIBLE_TOUCHDOWN("1436"),
    /**
     * TV Timeout (Ice Hockey)
     */
    TV_TIMEOUT("1437"),
    /**
     * Tv Timeout over (Ice Hockey)
     */
    TV_TIMEOUT_OVER("1438"),
    /**
     * Canceled touchdown
     */
    CANCELED_TOUCHDOWN("1439"),
    /**
     * Pass
     */
    PASS("1440"),
    /**
     * Rush
     */
    RUSH("1441"),
    /**
     * Sack
     */
    SACK("1442"),
    /**
     * Delayed penalty (Ice Hockey)
     */
    CANCELED_CHALLENGE("1443"),
    /**
     * Touchdown
     */
    TOUCHDOWN("1444"),
    /**
     * American football kickoff
     */
    AMERICAN_FOOTBALL_KICKOFF("1445"),
    /**
     * Fumble recovered
     */
    FUMBLE_RECOVERED("1446"),
    /**
     * Possible turnover
     */
    POSSIBLE_TURNOVER("1447"),
    /**
     * Canceled turnover
     */
    CANCELED_TURNOVER("1448"),
    /**
     * Possible 2 point conversion
     */
    POSSIBLE_2_POINT_CONVERSION("1449"),
    /**
     * Canceled 2 point conversion
     */
    CANCELED_2_POINT_CONVERSION("1450"),
    /**
     * Possible extra point
     */
    POSSIBLE_EXTRA_POINT("1451"),
    /**
     * Canceled extra point
     */
    CANCELED_EXTRA_POINT("1452"),
    /**
     * Possible safety
     */
    POSSIBLE_SAFETY("1453"),
    /**
     * Canceled safety
     */
    CANCELED_SAFETY("1454"),
    /**
     * Possible punt
     */
    POSSIBLE_PUNT("1455"),
    /**
     * Canceled punt
     */
    CANCELED_PUNT("1456"),
    /**
     * Two point conversion
     */
    TWO_POINT_CONVERSION("1457"),
    /**
     * Video review start
     */
    VIDEO_REVIEW_START("1458"),
    /**
     * Video review over
     */
    VIDEO_REVIEW_OVER("1459"),
    /**
     * Rule set
     */
    RULE_SET("1461"),
    /**
     * Score change
     */
    SCORE_CHANGE("1500"),
    /**
     * Bowler starts
     */
    BOWLER_STARTS("1501"),
    /**
     * Jack roll
     */
    JACK_ROLL("1502"),
    /**
     * Redeliver jack
     */
    REDELIVER_JACK("1503"),
    /**
     * Bowl
     */
    BOWL("1504"),
    /**
     * Warning
     */
    WARNING("1505"),
    /**
     * Restart end
     */
    RESTART_END("1506"),
    /**
     * Dead end
     */
    DEAD_END("1507"),
    /**
     * Dead jack
     */
    DEAD_JACK("1508"),
    /**
     * Dead bowl
     */
    DEAD_BOWL("1509"),
    /**
     * Replay bowl
     */
    REPLAY_BOWL("1510"),
    /**
     * Disqualification
     */
    DISQUALIFICATION("1511"),
    /**
     * End start
     */
    END_START("1512"),
    /**
     * Trial end
     */
    TRIAL_END("1513"),
    /**
     * Start delayed
     */
    START_DELAYED("1514"),
    /**
     * Match format
     */
    MATCH_FORMAT("1515"),
    /**
     * First set awarded
     */
    FIRST_SET_AWARDED("1516"),
    /**
     * Delayed penalty
     */
    DELAYED_PENALTY("1521"),
    /**
     * Score adjustment (Handball, Basketball and Volleyball)
     */
    SCORE_ADJUSTMENT("1550"),
    /**
     * Two point miss count (Basketball)
     */
    TWO_POINT_MISS_COUNT("1600"),
    /**
     * Three point miss count (Basketball)
     */
    THREE_POINT_MISS_COUNT("1601"),
    /**
     * Rebound count (Basketball)
     */
    REBOUND_COUNT("1602"),
    /**
     * Play start (Basketball)
     */
    PLAY_START("1603"),
    /**
     * Play over (Basketball)
     */
    PLAY_OVER("1604"),
    /**
     * Play cancelled (Basketball)
     */
    PLAY_CANCELLED("1605"),
    /**
     * Pass count (American Football)
     */
    PASS_COUNT("1606"),
    /**
     * Rush count (American Football)
     */
    RUSH_COUNT("1607"),
    /**
     * Penalty count (American Football)
     */
    PENALTY_COUNT("1608"),
    /**
     * Play start count (American Football)
     */
    PLAY_START_COUNT("1609"),
    /**
     * Fumble count (American Football)
     */
    FUMBLE_COUNT("1610"),
    /**
     * Fumble recovered count (American Football)
     */
    FUMBLE_RECOVERED_COUNT("1611"),
    /**
     * Turnover count (American Football)
     */
    TURNOVER_COUNT("1612"),
    /**
     * Turnover count (American Football)
     */
    INTERCEPTION_COUNT("1613"),
    /**
     * Interception count (American Football)
     */
    CHALLENGE_COUNT("1614"),
    /**
     * Drives count (American Football)
     */
    DRIVES_COUNT("1633"),
    /**
     * Challenge decision
     */
    CHALLENGE_DECISION("1656"),
    /**
     * Use challenges official reviews
     */
    USE_CHALLENGES_OFFICIAL_REVIEWS("1657"),
    /**
     * Overrule
     */
    OVERRULE("1658"),
    /**
     * Challenge
     */
    CHALLENGE("1659"),
    /**
     * Challenge not confirmed
     */
    CHALLENGE_NOT_CONFIRMED("1660"),
    /**
     * Passive play
     */
    PASSIVE_PLAY("1700"),
    /**
     * Passive play called (Handball)
     */
    PASSIVE_PLAY_CALLED("1701"),
    /**
     * Passive play canceled
     */
    PASSIVE_PLAY_CANCELED("1702"),
    /**
     * Empty net
     */
    SEVENTH_PLAYER_SUB("1703"),
    /**
     * Active goal keeper
     */
    ACTIVE_GOAL_KEEPER("1704"),
    /**
     * Play start bb
     */
    PLAY_START_BB("1715"),
    /**
     * Play over bb
     */
    PLAY_OVER_BB("1716"),
    /**
     * Runners in motion
     */
    RUNNERS_IN_MOTION("1717"),
    /**
     * Checked runner
     */
    CHECKED_RUNNER("1718"),

    /**
     * Runner advances to base x
     */
    RUNNER_ADVANCES_TO_BASE_X("1719"),
    /**
     * Run scored
     */
    RUN_SCORED("1720"),
    /**
     * Runner out
     */
    RUNNER_OUT("1721"),
    /**
     * Strike
     */
    STRIKE("1722"),
    /**
     * Ball
     */
    BALL("1723"),
    /**
     * Foul ball
     */
    FOUL_BALL("1724"),
    /**
     * Who throws the first pitch
     */
    WHO_THROWS_THE_FIRST_PITCH("1725"),
    /**
     * Batter out
     */
    BATTER_OUT("1726"),
    /**
     * Batter advanced to base x
     */
    BATTER_ADVANCES_TO_BASE_X("1727"),
    /**
     * Balk
     */
    BALK("1728"),


    /**
     * Steal basketball
     */
    STEAL_BASKETBALL("1733"),


    /**
     * Turnover
     */
    TURNOVER_BASKETBALL("1734"),


    /**
     * Block
     */
    BLOCK("1735"),


    /**
     * Big play
     */
    BIG_PLAY("1736"),


    /**
     * Possible defensive return
     */
    POSSIBLE_DEFENSIVE_RETURN("1737"),


    /**
     * Defensive return
     */
    DEFENSIVE_RETURN("1738"),


    /**
     * Defensive return not confirmed
     */
    DEFENSIVE_RETURN_NOT_CONFIRMED("1739"),


    /**
     * Possible fourth down attempt
     */
    POSSIBLE_FOURTH_DOWN_ATTEMPT("1740"),


    /**
     * Score event correction
     */
    SCORE_EVENT_CORRECTION("1742"),

    /**
     * Possible onside kick
     */
    POSSIBLE_ONSIDE_KICK("1747"),

    /**
     * This event gets sent if the drive information changes
     */
    DRIVE_INFORMATION_CHANGED("1767"),

    /**
     * Player hits/body checks an opposing player
     */
    Hit("1768"),


    /**
     * Ice conditions
     */
    ICE_CONDITIONS("1769"),


    /**
     * Play is stopped during the game. A stoppage is always followed by a faceoff
     */
    Stoppage("1770"),

    /**
     * Faceoff for puck possession
     */
    FACEOFF("1771"),

    /**
     * sed when an attack is over, i.e. the puck is not in opponent's third
     */
    PUCK_SAFE("1772"),


    /**
     * CS map started
     */
    CS_MAP_STARTED("1814"),

    /**
     * CS map ended
     */
    CS_MAP_ENDED("1816"),

    /**
     * CS round started
     */
    CS_ROUND_STARTED("1818"),


    /**
     * CS map ended
     */
    CS_ROUND_ENDED("1820"),

    /**
     * CS kill
     */
    CS_KILL("1822"),

    /**
     * CS assist
     */
    CS_ASSIST("1824"),

    /**
     * CS map picked
     */
    CS_MAP_PICKED("1826"),

    /**
     * CS Round rollback
     */
    CS_ROUND_ROLLBACK("1827"),

    /**
     * CS CT start
     */
    CS_CT_START("1828"),

    /**
     * Baseball status correction
     */
    BASEBALL_STATS_CORRECTION("1832"),

    /**
     * Possible drop goal
     */
    POSSIBLE_DROP_GOAL("1833"),

    /**
     * Sent whenever a point is scored
     */
    SQUASH_FULL_SCORE("1834"),

    /**
     * Holds player serving and from which side of the court
     */
    SQUASH_CURRENT_SERVE("1835"),

    /**
     * Squash Decision Outcome
     */
    SQUASH_DECISION_OUTCOME("1836"),

    /**
     * Penalty Comp Goal
     */
    PENALTY_COMP_GOAL("1837"),


    /**
     * Sent when the map has started
     */
    MOBA_MAP_STARTED("1840"),

    /**
     * Sent when the map has ended
     */
    MOBA_MAP_ENDED("1842"),

    /**
     * Information about what hero is picked / banned by what team. Additional information for this event gets added in the attributes HeroId and HeroName
     */
    MOBA_HERO_PICKED_OR_BANNED("1844"),

    /**
     * Which player plays which hero. Additional information for this event gets added in the attributes HeroId and HeroName
     */
    MOBA_PLAYER_HERO("1845"),

    /**
     * Which team starts the picking phase
     */
    MOBA_START_PICKING("1846"),

    /**
     * Tells how many heroes are alive. Additional information information for this event gets added in the attributes HeroesAliveAway and HeroesAliveHome
     */
    MOBA_TEAM_BALANCE_CHANGE("1848"),

    /**
     * Tells which team plays as Dire in the given map
     */
    DOTA_DIRE_START("1850"),
    /**
     * Information about the kills made by either of the teams. Additional information information for this event gets added in the attributes KillsHome and KillsAway
     */
    DOTA_KILLS("1852"),
    /**
     * Info about the players
     */
    DOTA_PLAYER_INFORMATION("1856"),
    /**
     * Describes which tower is taken over by what team. Does not consider denies
     */
    DOTA_STRUCTURE_TAKEDOWN("1858"),
    /**
     * Sent when an item is added or removed from the inventory of a player
     */
    DOTA_ITEM_CHANGE("1860"),
    /**
     * Sent when the first kill in a map has happened
     */
    DOTA_FIRST_BLOOD("1862"),
    /**
     * Sent when the first Roshan has been killed and a player has obtained the Aegis of the Immortal
     */
    DOTA_FIRST_AEGIS("1864"),
    /**
     * Sent when the player is about to take a penalty
     */
    TAKE_PENALTY("1884"),
    /**
     * Position of the puck on the rink in x/y coordinates
     */
    PUCK_POSITION("1885"),
    /**
     * Sent when the bomb has been planted
     */
    CS_BOMB_PLANTED("1900"),
    /**
     * Sent when an attempt to defuse the bomb has started
     */
    CS_BOMB_DEFUSE_START("1901"),
    /**
     * The money balance for a team
     */
    CS_TEAM_ECONOMY("1902"),
    /**
     * Sent when a player is killed by a member from the same team
     */
    CS_TEAM_KILL("1903"),
    /**
     * Sent when a player has committed suicide
     */
    CS_SUICIDE("1904"),
    /**
     * Sent when video review is not confirmed
     */
    CANCELED_VIDEO_REVIEW("1921"),

    /**
     * Video assistant referee
     */
    VIDEO_ASSISTANT_REFEREE("2064"),

    /**
     * Video assistant referee over
     */
    VIDEO_ASSISTANT_REFEREE_OVER("2065"),

    /**
     * Possible video assistant referee
     */
    POSSIBLE_VIDEO_ASSISTANT_REFEREE("2066"),

    /**
     * Canceled video assistant referee
     */
    CANCELED_VIDEO_ASSISTANT_REFEREE("2067")
    ;

    private static Map<String, EventType> literalMap;

    static {
        Map<String, EventType> preMap = new HashMap<>();
        for (EventType eventType : EventType.values()) {
            preMap.put(eventType.getTypeId(), eventType);
        }
        literalMap = Collections.unmodifiableMap(preMap);
    }

    private String literalValue;

    EventType(String literalValue) {
        this.literalValue = literalValue;
    }

    /**
     * Gets enum from string value
     *
     * @param value string representation of enum
     * @return EventType enum
     */
    public static EventType getEventTypeFromLiteralValue(String value) {
        EventType res = literalMap.get(value);
        if (res == null) {
            res = EventType.UNKNOWN;
            res.literalValue = value;
        }
        return res;
    }

    /**
     * Gets type id
     *
     * @return type id
     */
    public String getTypeId() {
        return literalValue;
    }

    @Override
    public boolean isLiteralValueEqual(String value) {
        return literalValue.equals(value);
    }

}

package com.sportradar.sdk.feed.livescout.enums;

import com.sportradar.sdk.common.classes.EntityEnumHelper;
import com.sportradar.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.sdk.common.interfaces.EntityEnum;

/**
 * Match status
 */
public enum ScoutMatchStatus implements EntityEnum {
    /**
     * Not started yet
     */
    NOT_STARTED("NOT_STARTED"),
    /**
     * 1st period of the match
     */
    FIRST_PERIOD("FIRST_PERIOD"),
    /**
     * 2nd period of the match
     */
    SECOND_PERIOD("SECOND_PERIOD"),
    /**
     * 3rd period of the match
     */
    THIRD_PERIOD("THIRD_PERIOD"),
    /**
     * 4th period of the match
     */
    FOURTH_PERIOD("FOURTH_PERIOD"),
    /**
     * 5th period of the match
     */
    FIFTH_PERIOD("FIFTH_PERIOD"),
    /**
     * 1st half of the match
     */
    FIRST_HALF("FIRST_HALF"),
    /**
     * 2nd half of the match
     */
    SECOND_HALF("SECOND_HALF"),
    /**
     * 1st set
     */
    FIRST_SET("FIRST_SET"),
    /**
     * 2nd set
     */
    SECOND_SET("SECOND_SET"),
    /**
     * 3rd set
     */
    THIRD_SET("THIRD_SET"),
    /**
     * 4th set
     */
    FOURTH_SET("FOURTH_SET"),
    /**
     * Golden set (volleyball and beach volleyball)
     */
    GOLDEN_SET("GOLDEN_SET"),
    /**
     * Awaiting golden set (volleyball and beach volleyball)
     */
    AWAITING_GOLDEN_SET("AWAITING_GOLDEN_SET"),
    /**
     * After golden set (volleyball and beach volleyball)
     */
    AFTER_GOLDEN_SET("AFTER_GOLDEN_SET"),
    /**
     * 5th set
     */
    FIFTH_SET("FIFTH_SET"),
    /**
     * 6th set
     */
    SIXTH_SET("SIXTH_SET"),
    /**
     * 7th set
     */
    SEVENTH_SET("SEVENTH_SET"),
    /**
     * 1st quarter
     */
    FIRST_QUARTER("FIRST_QUARTER"),
    /**
     * 2nd quarter
     */
    SECOND_QUARTER("SECOND_QUARTER"),
    /**
     * 3rd quarter
     */
    THIRD_QUARTER("THIRD_QUARTER"),
    /**
     * 4th quarter
     */
    FOURTH_QUARTER("FOURTH_QUARTER"),
    /**
     * Match started
     */
    STARTED("STARTED"),
    /**
     * Match paused
     */
    PAUSE("PAUSE"),
    /**
     * Half time
     */
    HALFTIME("HALFTIME"),
    /**
     * Match overtime
     */
    OVERTIME("OVERTIME"),
    /**
     * 1st half of overtime
     */
    FIRST_HALF_OT("FIRST_HALF_OT"),
    /**
     * 2nd half of overtime
     */
    SECOND_HALF_OT("SECOND_HALF_OT"),
    /**
     * Penalty shooting
     */
    PENALTY_SHOOTING("PENALTY_SHOOTING"),
    /**
     * The match has been postponed and will be played later.
     * <p>
     * Note : Signals a significant delay (will not be played on the same day),
     * in which case a new match (with a different match ID) will be created.
     * </p>
     */
    POSTPONED("POSTPONED"),
    /**
     * Player 1 defaulted (e.g. by being disqualified or failing to attend the match)
     */
    DEFAULTED1("DEFAULTED1"),
    /**
     * Player 2 defaulted (e.g. by being disqualified or failing to attend the match)
     */
    DEFAULTED2("DEFAULTED2"),
    /**
     * The match start is delayed
     */
    DELAYED("DELAYED"),
    /**
     * The match has been cancelled
     */
    CANCELLED("CANCELLED"),
    /**
     * The match has been interrupted
     * <p>
     * Note : Interrupted after start, will continue later.
     * If the match cannot be continued, it will then be abandoned.
     * </p>
     */
    INTERRUPTED("INTERRUPTED"),
    /**
     * The match has been abandoned
     */
    ABANDONED("ABANDONED"),
    /**
     * A walkover has been awarded
     */
    WALKOVER("WALKOVER"),
    /**
     * Player retired
     */
    RETIRED("RETIRED"),
    /**
     * Player 1 won by walkover
     */
    WALKOVER1("WALKOVER1"),
    /**
     * Player 2 won by walkover
     */
    WALKOVER2("WALKOVER2"),
    /**
     * Player 1 retired
     */
    RETIRED1("RETIRED1"),
    /**
     * Player 2 retired
     */
    RETIRED2("RETIRED2"),
    /**
     * The match has ended
     */
    ENDED("ENDED"),
    /**
     * Waiting for overtime to start
     */
    AWAITING_OT("AWAITING_OT"),
    /**
     * Pause between 1st and 2nd overtime period
     */
    OT_HALFTIME("OT_HALFTIME"),
    /**
     * Waiting for penalty shooting to start
     */
    AWAITING_PENALTIES("AWAITING_PENALTIES"),
    /**
     * Overtime is finished
     */
    AFTER_OT("AFTER_OT"),
    /**
     * Penalties are finished
     */
    AFTER_PENALTIES("AFTER_PENALTIES"),
    /**
     * 1st pause
     */
    FIRST_PAUSE("FIRST_PAUSE"),
    /**
     * 2nd pause
     */
    SECOND_PAUSE("SECOND_PAUSE"),
    /**
     * 3rd pause
     */
    THIRD_PAUSE("THIRD_PAUSE"),
    /**
     * 4th pause
     */
    FOURTH_PAUSE("FOURTH_PAUSE"),
    /**
     * 5th pause
     */
    FIFTH_PAUSE("FIFTH_PAUSE"),
    /**
     * 6th pause
     */
    SIXTH_PAUSE("SIXTH_PAUSE"),
    /**
     * Sudden death
     */
    SUDDEN_DEATH("SUDDEN_DEATH"),
    /**
     * Awaiting sudden death
     */
    AWAITING_SD("AWAITING_SD"),
    /**
     * After sudden death
     */
    AFTER_SD("AFTER_SD"),
    /**
     * In progress
     */
    IN_PROGRESS("IN_PROGRESS"),

    /**
     * Session break
     */
    SESSION_BREAK("SESSION_BREAK"),

    /**
     * First innings, home team
     */
    FIRST_INNINGS_HOME_TEAM("FIRST_INNINGS_HOME_TEAM"),

    /**
     * First innings, away team
     */
    FIRST_INNINGS_AWAY_TEAM("FIRST_INNINGS_AWAY_TEAM"),

    /**
     * Second innings, home team
     */
    SECOND_INNINGS_HOME_TEAM("SECOND_INNINGS_HOME_TEAM"),

    /**
     * Second innings, away team
     */
    SECOND_INNINGS_AWAY_TEAM("SECOND_INNINGS_AWAY_TEAM"),

    /**
     * Awaiting super over
     */
    AWAITING_SUPER_OVER("AWAITING_SUPER_OVER"),

    /**
     * Super over, home team
     */
    SUPER_OVER_HOME_TEAM("SUPER_OVER_HOME_TEAM"),

    /**
     * Super over, away team
     */
    SUPER_OVER_AWAY_TEAM("SUPER_OVER_AWAY_TEAM"),

    /**
     * After super over
     */
    AFTER_SUPER_OVER("AFTER_SUPER_OVER"),

    /**
     * Innings break
     */
    INNINGS_BREAK("INNINGS_BREAK"),

    /**
     * Super over break
     */
    SUPER_OVER_BREAK("SUPER_OVER_BREAK"),

    /**
     * Lunch break
     */
    LUNCH_BREAK("LUNCH_BREAK"),

    /**
     * Tea break
     */
    TEA_BREAK("TEA_BREAK"),

    /**
     * Stumps
     */
    STUMPS("STUMPS"),

    /**
     * First inning top
     */
    FIRST_INNING_TOP("FIRST_INNING_TOP"),

    /**
     * Break top 1 bottom 1
     */
    BREAK_TOP1_BOTTOM1("BREAK_TOP1_BOTTOM1"),

    /**
     * First inning bottom
     */
    FIRST_INNING_BOTTOM("FIRST_INNING_BOTTOM"),

    /**
     * Break top 2 bottom 1
     */
    BREAK_TOP2_BOTTOM1("BREAK_TOP2_BOTTOM1"),

    /**
     * Second inning top
     */
    SECOND_INNING_TOP("SECOND_INNING_TOP"),

    /**
     * Break top 2 bottom 2
     */
    BREAK_TOP2_BOTTOM2("BREAK_TOP2_BOTTOM2"),

    /**
     * Second inning bottom
     */
    SECOND_INNING_BOTTOM("SECOND_INNING_BOTTOM"),

    /**
     * Break top 3 bottom 2
     */
    BREAK_TOP3_BOTTOM2("BREAK_TOP3_BOTTOM2"),

    /**
     * Third inning top
     */
    THIRD_INNING_TOP("THIRD_INNING_TOP"),

    /**
     * Break top 3 bottom 3
     */
    BREAK_TOP3_BOTTOM3("BREAK_TOP3_BOTTOM3"),

    /**
     * Third inning bottom
     */
    THIRD_INNING_BOTTOM("THIRD_INNING_BOTTOM"),

    /**
     * Break top 4 bottom 3
     */
    BREAK_TOP4_BOTTOM3("BREAK_TOP4_BOTTOM3"),

    /**
     * Fourth inning top
     */
    FOURTH_INNING_TOP("FOURTH_INNING_TOP"),

    /**
     * Break top 4 bottom 4
     */
    BREAK_TOP4_BOTTOM4("BREAK_TOP4_BOTTOM4"),

    /**
     * Fourth inning bottom
     */
    FOURTH_INNING_BOTTOM("FOURTH_INNING_BOTTOM"),

    /**
     * Break top 5 bottom 4
     */
    BREAK_TOP5_BOTTOM4("BREAK_TOP5_BOTTOM4"),

    /**
     * Fifth inning top
     */
    FIFTH_INNING_TOP("FIFTH_INNING_TOP"),

    /**
     * Break top 5 bottom 5
     */
    BREAK_TOP5_BOTTOM5("BREAK_TOP5_BOTTOM5"),

    /**
     * Fifth inning bottom
     */
    FIFTH_INNING_BOTTOM("FIFTH_INNING_BOTTOM"),

    /**
     * Break top 6 bottom 5
     */
    BREAK_TOP6_BOTTOM5("BREAK_TOP6_BOTTOM5"),

    /**
     * Sixth inning top
     */
    SIXTH_INNING_TOP("SIXTH_INNING_TOP"),

    /**
     * Break top 6 bottom 6
     */
    BREAK_TOP6_BOTTOM6("BREAK_TOP6_BOTTOM6"),

    /**
     * Sixth inning bottom
     */
    SIXTH_INNING_BOTTOM("SIXTH_INNING_BOTTOM"),

    /**
     * Break top 7 bottom 6
     */
    BREAK_TOP7_BOTTOM6("BREAK_TOP7_BOTTOM6"),

    /**
     * Seventh inning top
     */
    SEVENTH_INNING_TOP("SEVENTH_INNING_TOP"),

    /**
     * Break top 7 bottom 7
     */
    BREAK_TOP7_BOTTOM7("BREAK_TOP7_BOTTOM7"),

    /**
     * Seventh inning bottom
     */
    SEVENTH_INNING_BOTTOM("SEVENTH_INNING_BOTTOM"),

    /**
     * Break top 8 bottom 7
     */
    BREAK_TOP8_BOTTOM7("BREAK_TOP8_BOTTOM7"),

    /**
     * Eighth inning top
     */
    EIGHTH_INNING_TOP("EIGHTH_INNING_TOP"),

    /**
     * Break top 8 bottom 8
     */
    BREAK_TOP8_BOTTOM8("BREAK_TOP8_BOTTOM8"),

    /**
     * Eighth  inning bottom
     */
    EIGHTH_INNING_BOTTOM("EIGHTH_INNING_BOTTOM"),

    /**
     * Break top 9 bottom 8
     */
    BREAK_TOP9_BOTTOM8("BREAK_TOP9_BOTTOM8"),

    /**
     * Ninth inning top
     */
    NINTH_INNING_TOP("NINTH_INNING_TOP"),

    /**
     * Break top 9 bottom 9
     */
    BREAK_TOP9_BOTTOM9("BREAK_TOP9_BOTTOM9"),

    /**
     * Ninth inning bottom
     */
    NINTH_INNING_BOTTOM("NINTH_INNING_BOTTOM"),

    /**
     * Break top extra inning bottom 7
     */
    BREAK_TOP_EXTRA_INNING_BOTTOM7("BREAK_TOPEI_BOTTOM7"),

    /**
     * Break top extra inning bottom 9
     */
    BREAK_TOP_EXTRA_INNING_BOTTOM9("BREAK_TOPEI_BOTTOM9"),

    /**
     * Extra inning top
     */
    EXTRA_INNING_TOP("EXTRA_INNING_TOP"),

    /**
     * Break top extra inning bottom extra inning
     */
    BREAK_TOP_EXTRA_INNING_BOTTOM_EXTRA_INNING("BREAK_TOPEI_BOTTOMEI"),

    /**
     * Extra inning bottom
     */
    EXTRA_INNING_BOTTOM("EXTRA_INNING_BOTTOM"),

    /**
     * First map
     */
    FIRST_MAP("FIRST_MAP"),

    /**
     * Second map
     */
    SECOND_MAP("SECOND_MAP"),

    /**
     * Third map
     */
    THIRD_MAP("THIRD_MAP"),

    /**
     * Fourth map
     */
    FOURTH_MAP("FOURTH_MAP"),

    /**
     * Fifth map
     */
    FIFTH_MAP("FIFTH_MAP"),

    /**
     * Sixth map
     */
    SIXTH_MAP("SIXTH_MAP"),

    /**
     * Seventh map
     */
    SEVENTH_MAP("SEVENTH_MAP"),

    /**
     * First game
     */
    FIRST_GAME("FIRST_GAME"),

    /**
     * Second game
     */
    SECOND_GAME("SECOND_GAME"),

    /**
     * Third game
     */
    THIRD_GAME("THIRD_GAME"),

    /**
     * Fourth game
     */
    FOURTH_GAME("FOURTH_GAME"),

    /**
     * Fifth game
     */
    FIFTH_GAME("FIFTH_GAME")
    ;

    private String literalValue;

    ScoutMatchStatus(String literalValue) {
        this.literalValue = literalValue;
    }

    /**
     * Gets enum from string value
     *
     * @param value string representation of enum
     * @return ScoutMatchStatus enum
     * @throws UnknownEnumException if invalid value is passed
     */
    public static ScoutMatchStatus getScoutMatchStatusFromLiteralValue(String value) throws UnknownEnumException {
        ScoutMatchStatus result = EntityEnumHelper.getValueFromLiteralValue(ScoutMatchStatus.values(), value);
        if (result == null && value != null && value.isEmpty()) {
            throw new UnknownEnumException(ScoutMatchStatus.class.getSimpleName(), value);
        }
        return result;
    }

    @Override
    public boolean isLiteralValueEqual(String value) {
        return literalValue.equals(value);
    }

    /**
     * Terminal match states
     */
    public static final ScoutMatchStatus[] TERMINAL_MATCH_STATES = new ScoutMatchStatus[]{
            ScoutMatchStatus.ENDED,
            ScoutMatchStatus.ABANDONED,
            ScoutMatchStatus.CANCELLED,
            ScoutMatchStatus.POSTPONED,
            ScoutMatchStatus.RETIRED,
            ScoutMatchStatus.RETIRED1,
            ScoutMatchStatus.RETIRED2,
            ScoutMatchStatus.WALKOVER,
            ScoutMatchStatus.WALKOVER1,
            ScoutMatchStatus.WALKOVER2,
            ScoutMatchStatus.DEFAULTED1,
            ScoutMatchStatus.DEFAULTED2,
            ScoutMatchStatus.AFTER_PENALTIES,
            ScoutMatchStatus.AFTER_OT,
            ScoutMatchStatus.AFTER_SD
    };

    /**
     * Return whether the given status is terminal.
     *
     * @param status - the given match status
     * @return true when yes; false else
     */
    public static boolean isTerminalState(ScoutMatchStatus status) {
        for (ScoutMatchStatus one : TERMINAL_MATCH_STATES) {
            if (one.equals(status)) {
                return true;
            }
        }
        return false;
    }
}

package com.sportradar.sdk.feed.livescout.entities;


import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * Scout odds of specific type/subtype and corresponding odd fields.
 */
public class ScoutOddsEntity implements Serializable {

    private static final long serialVersionUID = -400772786295834798L;
    private Integer alsoOdds;
    private String description;
    private Integer guthMatchId;
    private boolean manualActive;
    private long matchId;
    private String specialOddsValue;
    private Integer subType;
    private int type;
    private DateTime validDate;
    private Map<String, ScoutOddsFieldEntity> values;


    /**
     * Not important, used for compatibility with other systems.
     *
     * @return also odds
     */
    public Integer getAlsoOdds() {
        return alsoOdds;
    }

    /**
     * Description of this odds type or a freetext description (e.g. "1st Half - Exact number of goals").
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Guth (legacy system) match id.
     *
     * @return guth match id
     */
    public Integer getGuthMatchId() {
        return guthMatchId;
    }

    /**
     * Match id
     *
     * @return match id
     */
    public long getMatchId() {
        return matchId;
    }

    /**
     * This property is set only for some odds types: total, handicap and freetext3way/2way/nway.
     * It indicates the total value, handicap value or freetext details respectively
     * (e.g. special odds value for "Free Text nway" is "1st Half - Exact number of goals 3=8.75, 2=3.7, 1=2.4, 0=2.85, 4+=20")
     *
     * @return special odds value
     */
    public String getSpecialOddsValue() {
        return specialOddsValue;
    }

    /**
     * Tells what kind of freetext odds this is (e.g. SubType="2" resolves to Description="Which team has kick off?").
     *
     * @return subtype
     */
    public Integer getSubType() {
        return subType;
    }

    /**
     * Odds type represented by a numeric id.
     *
     * @return type
     */
    public int getType() {
        return type;
    }

    /**
     * Time when odds were calculated.
     *
     * @return valid date
     */
    public DateTime getValidDate() {
        return validDate;
    }

    /**
     * Collection of odds field values (e.g. for 1x2: Home=2.5, Draw=2.9, Away=2.85).
     * Key is represented by an odds value Side.
     *
     * @return odds field values
     */
    public Map<String, ScoutOddsFieldEntity> getValues() {
        return values;
    }

    /**
     * Were odds manually activated?
     * <p>
     * Note : Probably not important, used for compatibility with other systems.
     * </p>
     *
     * @return flag is odds were manually activated
     */
    public boolean isManualActive() {
        return manualActive;
    }

    @Override
    public String toString() {
        return "ScoutOddsEntity{" +
                "alsoOdds=" + alsoOdds +
                ", description='" + description + '\'' +
                ", guthMatchId=" + guthMatchId +
                ", manualActive=" + manualActive +
                ", matchId=" + matchId +
                ", specialOddsValue='" + specialOddsValue + '\'' +
                ", subType=" + subType +
                ", type=" + type +
                ", validDate=" + validDate +
                ", values=" + values +
                '}';
    }

    protected void setAlsoOdds(Integer alsoOdds) {
        this.alsoOdds = alsoOdds;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected void setGuthMatchId(Integer guthMatchId) {
        this.guthMatchId = guthMatchId;
    }

    protected void setManualActive(boolean manualActive) {
        this.manualActive = manualActive;
    }

    protected void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    protected void setSpecialOddsValue(String specialOddsValue) {
        this.specialOddsValue = specialOddsValue;
    }

    protected void setSubType(Integer subType) {
        this.subType = subType;
    }

    protected void setType(int type) {
        this.type = type;
    }

    protected void setValidDate(DateTime validDate) {
        this.validDate = validDate;
    }

    protected void setValues(Map<String, ScoutOddsFieldEntity> values) {
        this.values = Collections.unmodifiableMap(values);
    }

}

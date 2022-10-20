package com.sportradar.sdk.feed.livescout.entities;


import java.io.Serializable;

/**
 * Scout odds field consisting of odds side and corresponding odds value
 * (e.g. description="Under 1.5" side="betunder" value="2.9").
 */
public class ScoutOddsFieldEntity implements Serializable {

    private static final long serialVersionUID = 3211024643099610321L;
    private String description;
    private String side;
    private String value;

    /**
     * For Serializable
     */
    protected ScoutOddsFieldEntity() {

    }

    /**
     * Gets description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets side
     *
     * @return side
     */
    public String getSide() {
        return side;
    }

    /**
     * Gets value
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ScoutOddsFieldEntity{" +
                "description='" + description + '\'' +
                ", side='" + side + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected void setSide(String side) {
        this.side = side;
    }

    protected void setValue(String value) {
        this.value = value;
    }

}

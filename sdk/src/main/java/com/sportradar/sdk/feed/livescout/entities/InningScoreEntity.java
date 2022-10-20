package com.sportradar.sdk.feed.livescout.entities;

import com.sportradar.sdk.feed.common.entities.HomeAway;

import java.io.Serializable;

/**
 * Inning score entry
 */
public class InningScoreEntity implements Serializable {

    private static final long serialVersionUID = -3402363020732970182L;
    private int inning;
    private HomeAway<Integer> score;

    /**
     * Gets inning number
     * @return inning number
     */
    public int getInning() {
        return inning;
    }

    /**
     * Gets scores in this inning
     * @return scores in this inning
     */
    public HomeAway<Integer> getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "InningScoreEntity{" +
                "inning=" + inning +
                ", score=" + score +
                '}';
    }

    protected void setInning(int inning) {
        this.inning = inning;
    }

    protected void setScore(HomeAway<Integer> score) {
        this.score = score;
    }
}

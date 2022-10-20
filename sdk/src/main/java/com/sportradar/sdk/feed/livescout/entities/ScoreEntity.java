package com.sportradar.sdk.feed.livescout.entities;

import com.sportradar.sdk.proto.dto.incoming.livescout.Score;

import java.io.Serializable;

public class ScoreEntity implements Serializable {
    private final static long serialVersionUID = 1L;

    private final String type;
    private final double team1;
    private final double team2;
    private final ScoreEntity subScore;

    public ScoreEntity(Score score) {
        this.type = score.getType();
        this.team1 = score.getT1();
        this.team2 = score.getT2();

        if(score.getScore() != null){
            subScore = new ScoreEntity(score.getScore());
        }
        else{
            subScore = null;
        }
    }

    public String getType() { return type; }

    public double getTeam1() { return team1; }

    public double getTeam2() { return team2; }

    public ScoreEntity getSubScore() { return subScore; }

    @Override
    public String toString() {
        return "ScoreEntity{" +
                "type=" + type +
                ", t1=" + team1 +
                ", t2=" + team2 +
                ", subScore=" + subScore +
                '}';
    }
}

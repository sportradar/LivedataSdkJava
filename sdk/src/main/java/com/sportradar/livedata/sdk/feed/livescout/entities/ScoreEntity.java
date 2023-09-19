package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Score;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

//It is better to leave getters as is for javadoc purpose.
@EqualsAndHashCode
@ToString
public class ScoreEntity implements Serializable {
    private final static long serialVersionUID = 1L;

    private final String type;
    private final double team1;
    private final double team2;
    private final ScoreEntity subScore;

    public ScoreEntity(Score score) {
        this(score.getType(), score.getT1(), score.getT2(),
                score.getScore() != null ? new ScoreEntity(score.getScore()) : null);
    }

    protected ScoreEntity(String type, double team1, double team2, ScoreEntity subScore) {
        this.type = type;
        this.team1 = team1;
        this.team2 = team2;
        this.subScore = subScore;
    }

    public String getType() { return type; }

    public double getTeam1() { return team1; }

    public double getTeam2() { return team2; }

    public ScoreEntity getSubScore() { return subScore; }
}

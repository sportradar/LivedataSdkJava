package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.common.classes.Nulls;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Event;
import lombok.*;

import static com.google.common.base.Preconditions.checkState;
import static com.sportradar.livedata.sdk.common.classes.Nulls.hasNulls;

@Getter
@EqualsAndHashCode
@ToString
public class TrackEntity {

    private Double x;

    private Double y;

    private Double z;

    protected TrackEntity(Double x, Double y, Double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void apply(Event source, ScoutEventEntity result) {
        checkState(!hasNulls(source, result));
        if (Nulls.hasNotNulls(source.getTrackx(), source.getTracky(), source.getTrackz())) {
            result.setTrack(new TrackEntity(source.getTrackx(), source.getTracky(), source.getTrackz()));
        }
    }
}

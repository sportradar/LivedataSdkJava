package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Event;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.sportradar.livedata.sdk.common.classes.Nulls.hasNotNulls;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TrackEntityTest {
    @ParameterizedTest(name = "apply_OK_Input_{0}_{1}_{2}_Test")
    @CsvSource({
            "1.1, 2.2, 3.3",
            "1.1,    ,    ",
            "   , 2.2,    ",
            "   ,    , 3.3",
            "   ,    ,    "
    })
    void apply_OK_Input_Test(Double x, Double y, Double z) {
        Event input = new Event();
        input.setTrackx(x);
        input.setTracky(y);
        input.setTrackz(z);


        ScoutEventEntity result = new ScoutEventEntity();
        TrackEntity.apply(input, result);

        TrackEntity expected = hasNotNulls(x, y, z) ? new TrackEntity(x, y, z) : null;
        assertThat(result.getTrack(), equalTo(expected));
    }

    @Test
    void apply_Null_Income_Test() {
        assertThrows(Exception.class, () -> {
            TrackEntity.apply(new Event(), null);
        });
    }

    @Test
    void apply_Null_Outcome_Test() {
        assertThrows(Exception.class, () -> {
            TrackEntity.apply(null, new ScoutEventEntity());
        });
    }
}

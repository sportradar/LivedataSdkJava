package com.sportradar.livedata.sdk.feed.livescout.entities;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Court
 */
//It is better to leave getters as is for javadoc purpose.
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class CourtEntity implements Serializable {
    private final static long serialVersionUID = 1L;
    private int id;
    private String name;
    private Integer courtSeq;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCourtSeq() {
        return courtSeq;
    }
}

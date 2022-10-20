package com.sportradar.sdk.feed.livescout.entities;

import java.io.Serializable;

/**
 * Court
 */
public class CourtEntity implements Serializable {
    private final static long serialVersionUID = 1L;
    private int id;
    private String name;
    private Integer courtSeq;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public Integer getCourtSeq() {
        return courtSeq;
    }

    protected void setCourtSeq(Integer courtSeq) {
        this.courtSeq = courtSeq;
    }

    @Override
    public String toString() {
        return "CourtEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", courtSeq=" + courtSeq +
                '}';
    }
}

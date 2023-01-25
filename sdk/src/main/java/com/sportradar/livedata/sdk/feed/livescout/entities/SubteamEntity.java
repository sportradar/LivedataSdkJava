package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Subteam;
import jakarta.xml.bind.annotation.XmlAttribute;

import java.io.Serializable;

public class SubteamEntity  implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private int parentId;


    SubteamEntity(Subteam subteam){
        this.id = subteam.getId();
        this.name = subteam.getName();
        this.parentId = subteam.getParent();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "SubteamEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                "}";
    }
}

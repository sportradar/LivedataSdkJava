package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.Subteam;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

//It is better to leave getters as is for javadoc purpose.
@EqualsAndHashCode
@ToString
public class SubteamEntity  implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private final String name;
    private final int parentId;


    SubteamEntity(Subteam subteam){
        this(subteam.getId(), subteam.getName(), subteam.getParent());
    }

    protected SubteamEntity(int id, String name, int parentId){
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getParentId() {
        return parentId;
    }
}

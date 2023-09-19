package com.sportradar.livedata.sdk.feed.common.entities;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Contains id and name (for example Tournament, Category, ...)
 */
//It is better to leave getters as is for javadoc purpose.
@EqualsAndHashCode
@ToString
public class IdNameTuple implements Serializable {

    private static final long serialVersionUID = 149281134715031203L;
    private Long id;
    private LocalizedString name;
    private Long uniqueId;

    /**
     * Initialize a new {@link IdNameTuple} instance.
     *
     * @param id    Entity id
     * @param value Entity value
     */
    public IdNameTuple(long id, String value) {
        this(id, value, null);
    }

    /**
     * Initialize a new {@link IdNameTuple} instance.
     *
     * @param id       Entity id
     * @param value    Entity value
     * @param uniqueId Entity unique id (i.e. team unique id)
     */
    public IdNameTuple(long id, String value, Long uniqueId) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.name = new LocalizedString(value != null ? value : "");
    }

    /**
     * For Serializable
     */
    protected IdNameTuple() {}

    /**
     * Entity id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets name
     * @return name
     */
    public LocalizedString getName() {
        return name;
    }

    /**
     * Specified the team unique id.
     * <p>
     * The teams have two id's; one named id and another named uniqueid. The id is for that team in that tournament,
     * and the uniqueid is for that team in all tournaments. Not all teams have the uniqueid attribute,
     * so it should only be used to identify that two teams in different tournaments are the same.
     * </p>
     * @return unique id
     */
    public Long getUniqueId() {
        return uniqueId;
    }
}

package com.sportradar.livedata.sdk.feed.livescout.entities;

import lombok.*;

import java.io.Serializable;

@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class PaginationEntity implements Serializable {
    private static final long serialVersionUID = 2985143044035983853L;

    private String uuid;
    private Integer page;
    private Integer totalPages;

    /**
     * Before splitting feed gets his own uuid. All partials after splitting will have same uuid.
     *
     * @return uuid of split feed.
     */
    public String getUuid() { return uuid; }

    /**
     * Returns page number of current feed partial.
     *
     * @return page of current partial.
     */
    public Integer getPage() { return page; }

    /**
     * Returns total amount of pages in splitted feed.
     *
     * @return total pages.
     */
    public Integer getTotalPages() { return totalPages; }
}

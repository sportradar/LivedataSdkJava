package com.sportradar.sdk.feed.livescout.entities;

import com.google.common.base.Preconditions;
import com.sportradar.sdk.proto.dto.incoming.livescout.Jersey;

import java.io.Serializable;

/**
 * A basic jersey representation
 */
public class JerseyEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String base;
    private final String horizontalStripes;
    private final int jerseyType;
    private final String number;
    private final String outline;
    private final String shirtType;
    private final int side;
    private final String sleeve;
    private final String split;
    private final String squares;
    private final String verticalStripes;

    JerseyEntity(Jersey jersey) {
        Preconditions.checkNotNull(jersey);

        base = jersey.getBase();
        horizontalStripes = jersey.getHorizontalStripes();
        jerseyType = jersey.getJerseyType();
        number = jersey.getNumber();
        outline = jersey.getOutline();
        shirtType = jersey.getShirtType();
        side = jersey.getSide();
        sleeve = jersey.getSleeve();
        split = jersey.getSplit();
        squares = jersey.getSquares();
        verticalStripes = jersey.getVerticalStripes();
    }

    /**
     * Returns the base jersey color
     *
     * @return the base jersey color
     */
    public String getBase() {
        return base;
    }

    /**
     * Returns the horizontal stripes color
     *
     * @return the horizontal stripes color
     */
    public String getHorizontalStripes() {
        return horizontalStripes;
    }

    /**
     * Returns the jersey type
     *
     * @return the jersey type
     */
    public int getJerseyType() {
        return jerseyType;
    }

    /**
     * Returns the jersey number
     *
     * @return the jsersey number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Returns the jersey outline color
     *
     * @return the jersey outline color
     */
    public String getOutline() {
        return outline;
    }

    /**
     * Returns the jersey shirty type
     *
     * @return the jersey shirty type
     */
    public String getShirtType() {
        return shirtType;
    }

    /**
     * Returns the jersey side color
     *
     * @return the jersey side color
     */
    public int getSide() {
        return side;
    }

    /**
     * Returns the jersey sleeve color
     *
     * @return the jersey sleeve color
     */
    public String getSleeve() {
        return sleeve;
    }

    /**
     * Returns the jersey split color
     *
     * @return the jersey split color
     */
    public String getSplit() {
        return split;
    }

    /**
     * Returns the jersey squares color
     *
     * @return the jersey squares color
     */
    public String getSquares() {
        return squares;
    }

    /**
     * Returns the vertical stripes color
     *
     * @return the vertical stripes color
     */
    public String getVerticalStripes() {
        return verticalStripes;
    }
}

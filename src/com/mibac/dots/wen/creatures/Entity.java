package com.mibac.dots.wen.creatures;

import java.awt.geom.Point2D.Double;
import java.io.Serializable;;

public class Entity implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Double position;

    public Double getPosition() {
        return position;
    }

    public void setPosition(Double position) {
        this.position = position;
    }

}

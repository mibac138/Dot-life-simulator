package com.mibac.dots.wen.creatures;

import java.awt.Color;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;;

public class Entity implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Double position;
    protected Color color;

    public Entity() {
        this.position = new Double(0, 0);
        this.color = new Color(0, 0, 0);
    }

    public Double getPosition() {
        return position;
    }

    public void setPosition(Double position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

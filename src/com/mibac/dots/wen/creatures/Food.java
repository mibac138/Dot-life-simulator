package com.mibac.dots.wen.creatures;

import java.awt.geom.Point2D;

public class Food extends Entity {
    private static final long serialVersionUID = 1L;
    private double value;

    public Food(Point2D.Double position, double value) {
        this.position = position;
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

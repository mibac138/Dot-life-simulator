package com.mibac.dots.wen.creatures;

import java.awt.Color;
import java.awt.geom.Point2D.Double;

public class Food extends Entity {
    private static final long serialVersionUID = 1L;
    private double value;

    public Food(Double position, double value) {
        this.position = position;
        this.value = value;
        this.color = Color.GREEN;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

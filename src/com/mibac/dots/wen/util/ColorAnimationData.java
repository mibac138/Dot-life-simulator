package com.mibac.dots.wen.util;

import java.awt.Color;

public class ColorAnimationData {
    private Color startColor;
    private Color targetColor;
    private long start;
    private long duration;

    public ColorAnimationData(Color startColor, Color targetColor, long start, long duration) {
        this.startColor = startColor;
        this.targetColor = targetColor;
        this.start = start;
        this.duration = duration;
    }

    public Color getStartColor() {
        return startColor;
    }

    public Color getTargetColor() {
        return targetColor;
    }

    public long getStartTime() {
        return start;
    }

    public long getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "ColorAnimationData [startColor=" + startColor.getRed() + "; "
                + startColor.getGreen() + "; " + startColor.getBlue() + ", targetColor="
                + targetColor.getRed() + "; " + targetColor.getGreen() + "; "
                + targetColor.getBlue() + ", start=" + start + ", duration=" + duration + "]";
    }
}

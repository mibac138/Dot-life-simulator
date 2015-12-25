package com.mibac.dots.wen.view.themes;

import java.awt.Color;

import javax.swing.UIManager;

public class DefaultTheme extends Theme {
    @Override
    public String getLookAndFeelClassName() {
        return UIManager.getSystemLookAndFeelClassName();
    }

    @Override
    public Color getBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    public Color getBorderColor() {
        return Color.BLACK;
    }

    @Override
    public Color getPathColor() {
        return Color.RED;
    }

    @Override
    public Color getVisionRangeColor() {
        return Color.RED;
    }

    @Override
    public Color getSelectedCreatureColor() {
        return Color.MAGENTA;
    }
}

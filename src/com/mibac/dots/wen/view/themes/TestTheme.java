package com.mibac.dots.wen.view.themes;

import java.awt.Color;

import javax.swing.UIManager;

public class TestTheme extends Theme {

    @Override
    public String getLookAndFeelClassName() {
        return UIManager.getCrossPlatformLookAndFeelClassName();
    }

    @Override
    public Color getBackgroundColor() {
        return Color.YELLOW;
    }

    @Override
    public Color getBorderColor() {
        return Color.RED;
    }

    @Override
    public Color getPathColor() {
        return Color.GREEN;
    }

    @Override
    public Color getVisionRangeColor() {
        return Color.BLACK;
    }

    @Override
    public Color getSelectedCreatureColor() {
        return Color.PINK;
    }
}

package com.mibac.dots.wen.view.themes;

import java.awt.Color;

import javax.swing.UIManager;

import com.mibac.dots.wen.creatures.Creature;
import com.mibac.dots.wen.creatures.Creature.Gender;
import com.mibac.dots.wen.creatures.Food;

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
    public Color getColor(Creature c) {
        return c.getGender() == Gender.MALE ? Color.BLUE : Color.PINK;
    }

    @Override
    public Color getColor(Food f) {
        return Color.GREEN;
    }

    @Override
    public Color getSelectedCreatureColor() {
        return Color.MAGENTA;
    }
}

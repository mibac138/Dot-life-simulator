package com.mibac.dots.wen.view.themes;

import java.awt.Color;

import com.mibac.dots.wen.creatures.Creature;
import com.mibac.dots.wen.creatures.Food;

public abstract class Theme {
    public abstract String getLookAndFeelClassName();

    public abstract Color getBackgroundColor();

    public abstract Color getBorderColor();

    public abstract Color getPathColor();

    public abstract Color getVisionRangeColor();

    public abstract Color getColor(Creature c);

    public abstract Color getColor(Food f);

    public abstract Color getSelectedCreatureColor();
}

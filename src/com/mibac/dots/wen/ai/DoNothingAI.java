package com.mibac.dots.wen.ai;

import java.util.Vector;

import com.mibac.dots.wen.creatures.Creature;
import com.mibac.dots.wen.creatures.Food;

public class DoNothingAI extends EntityAI {
    private static final long serialVersionUID = 1L;

    @Override
    public void init() {}

    @Override
    protected void think(Vector<Creature> creatures, Vector<Food> food) {}

    @Override
    protected boolean areCreaturesInRangeRequired() {
        return false;
    }

    @Override
    protected boolean isFoodInRangeRequired() {
        return false;
    }

}

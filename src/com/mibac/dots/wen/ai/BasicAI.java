package com.mibac.dots.wen.ai;


import static com.mibac.dots.wen.util.Debug.PRINT_AI;
import static com.mibac.dots.wen.util.Logger.log;

import java.awt.Color;
import java.util.Vector;

import com.mibac.dots.wen.creatures.Creature;
import com.mibac.dots.wen.creatures.Creature.Gender;
import com.mibac.dots.wen.creatures.Food;

public class BasicAI extends EntityAI {
    private static final long serialVersionUID = 1L;

    @Override
    public void init() {
        creature.setColor(creature.getGender() == Gender.MALE ? Color.BLUE : Color.PINK);
    }

    @Override
    protected void think(Vector<Creature> creatures, Vector<Food> food) {
        creature.setTarget(null);

        if (food.size() > 0) {
            creature.setTarget(food.get(0).getPosition());
            log("target found - " + creature.getTarget(), PRINT_AI);
        }

        log("target not found", PRINT_AI);
    }

    @Override
    protected boolean areCreaturesInRangeRequired() {
        return false;
    }

    @Override
    protected boolean isFoodInRangeRequired() {
        return true;
    }
}

package com.mibac.dots.wen.ai;

import static com.mibac.dots.wen.util.Debug.Type.PRINT_AI;
import static com.mibac.dots.wen.util.Logger.log;

import java.util.Vector;

import com.mibac.dots.wen.creatures.Creature;
import com.mibac.dots.wen.creatures.Food;

public class BasicAI extends EntityAI {

    @Override
    public void init() {}

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

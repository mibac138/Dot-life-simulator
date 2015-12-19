package com.mibac.dots.wen.ai;


import static com.mibac.dots.wen.util.Debug.PRINT_AI;
import static com.mibac.dots.wen.util.Logger.log;

import java.util.Vector;

import com.mibac.dots.wen.creatures.Creature;
import com.mibac.dots.wen.creatures.Food;

public class BetterAI extends EntityAI {
    private static final long serialVersionUID = 1L;

    @Override
    public void init() {}

    @Override
    protected void think(Vector<Creature> creatures, Vector<Food> food) { // Move random
        creature.setTarget(null);
        Food nearestFood = food.size() >= 1 ? food.get(0) : null;

        // Goto food
        if (nearestFood != null) {
            creature.setTarget(nearestFood.getPosition());
            log("found food -- following", PRINT_AI);

            if (creature.getEnergy() + nearestFood.getValue() <= creature.getMaxEnergy())
                log("eating", PRINT_AI);
            else {
                log("waiting", PRINT_AI);
                creature.setTarget(null);
            }
        }

        // Goto mate
        Creature nearestMate = getNearestMate(creatures);

        if (nearestMate != null) {
            creature.setTarget(nearestMate.getPosition());
            log("nevermind -- found mate -- following", PRINT_AI);
        }
    }

    @Override
    protected boolean areCreaturesInRangeRequired() {
        return true;
    }

    @Override
    protected boolean isFoodInRangeRequired() {
        return true;
    }

    private Creature getNearestMate(Vector<Creature> creatures) {
        Creature nearestMate = null;
        double nearestDistance = Double.MAX_VALUE;

        if (getCreature().isPregnant()
                || getCreature().getEnergy() < getCreature().getMatingEnergyNeeded()
                || getCreature().getBreedCooldownTime() > 0)
            return null;

        for (Creature c : creatures) {
            double distance = c.getPosition().distance(getCreature().getPosition());

            if (getCreature().getGender() != c.getGender() && distance < nearestDistance
                    && c.getEnergy() > c.getMatingEnergyNeeded() && !c.isPregnant()
                    && c.getBreedCooldownTime() <= 0) {

                nearestMate = c;
                nearestDistance = distance;
            }
        }

        return nearestMate;
    }
}

package com.mibac.dots.wen.ai;

import static com.mibac.dots.wen.util.Debug.Type.PRINT_AI;
import static com.mibac.dots.wen.util.Logger.log;

import java.util.Collections;
import java.util.Vector;

import com.mibac.dots.wen.creatures.Creature;
import com.mibac.dots.wen.creatures.Food;
import com.mibac.dots.wen.creatures.WorldModel;

public abstract class EntityAI {
    protected Creature creature;
    private WorldModel world;
    private long time;

    public final void update() {
        Vector<Creature> creatures = null;
        Vector<Food> food = null;

        if (areCreaturesInRangeRequired())
            creatures = getCreaturesInRange();
        if (isFoodInRangeRequired())
            food = getFoodInRange();

        long start = System.nanoTime();
        think(creatures, food);
        time = System.nanoTime() - start;
        log("update. Took " + time + " ns", PRINT_AI);
    }

    public abstract void init();

    protected abstract void think(Vector<Creature> creatures, Vector<Food> food);

    protected abstract boolean areCreaturesInRangeRequired();

    protected abstract boolean isFoodInRangeRequired();

    private Vector<Creature> getCreaturesInRange() {
        @SuppressWarnings("unchecked")
        Vector<Creature> creatures = (Vector<Creature>) world.getCreatures().clone();
        Collections
                .sort(creatures,
                        (c1, c2) -> Double.compare(
                                c1.getPosition().distance(creature.getPosition()),
                                c2.getPosition().distance(creature.getPosition())));

        creatures.removeIf(
                c -> c.getPosition().distance(creature.getPosition()) > creature.getVisionRange());
        return creatures;
    }

    private Vector<Food> getFoodInRange() {
        @SuppressWarnings("unchecked")
        Vector<Food> food = (Vector<Food>) world.getFood().clone();
        Collections
                .sort(food,
                        (c1, c2) -> Double.compare(
                                c1.getPosition().distance(creature.getPosition()),
                                c2.getPosition().distance(creature.getPosition())));

        food.removeIf(
                c -> c.getPosition().distance(creature.getPosition()) > creature.getVisionRange());
        return food;
    }

    public final long getThinkLength() {
        return time;
    }

    public WorldModel getWorldModel() {
        return world;
    }

    public EntityAI setWorldModel(WorldModel world) {
        this.world = world;
        return this;
    }

    public Creature getCreature() {
        return creature;
    }

    public EntityAI setCreature(Creature creature) {
        this.creature = creature;
        return this;
    }
}

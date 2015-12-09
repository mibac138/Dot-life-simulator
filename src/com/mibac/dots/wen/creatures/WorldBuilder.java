package com.mibac.dots.wen.creatures;

import java.awt.geom.Point2D.Double;
import java.util.Vector;

public class WorldBuilder {
    private Vector<Creature> creatures;
    private Vector<Food> food;
    private double mutationRate;
    private int width, height;
    private int speedFactor;
    private int maxSpeedFactor;
    private int maxFoodAmount;
    private short foodCreationRatio;

    public WorldBuilder(int width, int height) {
        this.creatures = new Vector<>();
        this.food = new Vector<>();
        this.mutationRate = 0.1;
        this.width = width;
        this.height = height;
        this.speedFactor = 1;
        this.maxSpeedFactor = 15;
        this.foodCreationRatio = 50;
        this.maxFoodAmount = 500;
    }

    public WorldBuilder addCreature(Creature creature) {
        creatures.add(creature);
        return this;
    }

    public WorldBuilder addFood(Food food) {
        this.food.add(food);
        return this;
    }

    public WorldBuilder generateRandomWorld(int creatureAmount, int foodAmount) {
        for (int i = 0; i < creatureAmount; i++)
            creatures.add(new Creature(new Double(Math.random() * width, Math.random() * height)));

        for (int i = 0; i < foodAmount; i++)
            food.add(new Food(new Double(Math.random() * width, Math.random() * height), 1));

        return this;
    }

    public WorldBuilder setSpeedFactor(int speedFactor) {
        this.speedFactor = speedFactor;
        return this;
    }

    public WorldBuilder setMaxSpeedFactor(int maxSpeedFactor) {
        this.maxSpeedFactor = maxSpeedFactor;
        return this;
    }

    public WorldBuilder setFoodCreationRatio(short foodCreationRatio) {
        this.foodCreationRatio = foodCreationRatio;
        return this;
    }

    public WorldBuilder setMaxFoodAmount(int maxFoodAmount) {
        this.maxFoodAmount = maxFoodAmount;
        return this;
    }

    public WorldBuilder setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
        return this;
    }

    public WorldModel build() {
        return new WorldModel(creatures, food, mutationRate, width, height, speedFactor,
                maxSpeedFactor, maxFoodAmount, foodCreationRatio);
    }
}

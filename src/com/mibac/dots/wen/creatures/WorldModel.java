package com.mibac.dots.wen.creatures;

import static com.mibac.dots.wen.util.Debug.Type.PRINT_OTHER;
import static com.mibac.dots.wen.util.Logger.log;

import java.util.Vector;

public class WorldModel {
    private Vector<Creature> creatures;
    private Vector<Food> food;
    private double mutationRate;
    private int width, height;
    private int selectedCreature;
    private int speedFactor;
    private int maxSpeedFactor;
    private int maxFoodAmount;
    private short foodCreationRatio;

    WorldModel(Vector<Creature> creatures, Vector<Food> food, double mutationRate, int width,
            int height, int speedFactor, int maxSpeedFactor, int maxFoodAmount,
            short foodCreationRatio) {
        this.creatures = new Vector<>();
        this.food = new Vector<>();
        this.mutationRate = mutationRate;
        this.width = width;
        this.height = height;
        this.selectedCreature = -1;
        this.speedFactor = speedFactor;
        this.maxSpeedFactor = maxSpeedFactor;
        this.maxFoodAmount = maxFoodAmount;
        this.foodCreationRatio = foodCreationRatio;

        creatures.stream().forEach(c -> addCreature(c));
        food.stream().forEach(f -> addFood(f));
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public int getMaxFoodAmount() {
        return maxFoodAmount;
    }

    public void setMaxFoodAmount(int maxFoodAmount) {
        this.maxFoodAmount = maxFoodAmount;
    }

    public short getFoodCreationRatio() {
        return foodCreationRatio;
    }

    public void setFoodCreationRatio(short foodCreationRatio) {
        this.foodCreationRatio = foodCreationRatio;
    }

    public void setMaxSpeedFactor(int maxSpeedFactor) {
        this.maxSpeedFactor = maxSpeedFactor;
    }

    public int getMaxSpeedFactor() {
        return maxSpeedFactor;
    }

    public int getSelectedCreatureIndex() {
        return selectedCreature;
    }

    public void setSelectedCreatureIndex(int index) {
        selectedCreature = index;
    }

    public Creature getSelectedCreature() {
        try {
            return creatures.get(selectedCreature);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public Vector<Creature> getCreatures() {
        return creatures;
    }

    public Vector<Food> getFood() {
        return food;
    }

    public void addCreature(Creature creature) {
        log("Adding new creature: " + creature, PRINT_OTHER);
        creature.getAI().setCreature(creature).setWorldModel(this).init();
        creatures.addElement((Creature) fixPosition(creature));
    }

    public void removeCreature(Creature creature) {
        creatures.remove(creature);
    }

    public void addFood(Food food) {
        if (this.food.size() < maxFoodAmount)
            this.food.addElement((Food) fixPosition(food));
    }

    public void addEntity(Entity entity) {
        if (entity instanceof Creature)
            addCreature((Creature) entity);
        else if (entity instanceof Food)
            addFood((Food) entity);
    }

    public void removeFood(Food food) {
        this.food.remove(food);
    }

    public int getSpeedFactor() {
        return speedFactor;
    }

    public void setSpeedFactor(int speedFactor) {
        this.speedFactor = speedFactor;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private Entity fixPosition(Entity entity) {
        if (entity.getPosition().getX() > width)
            entity.getPosition().x = width;
        if (entity.getPosition().getY() > height)
            entity.getPosition().y = height;

        return entity;
    }
}

package com.mibac.dots.wen.creatures;


import static com.mibac.dots.wen.util.Debug.PRINT_OTHER;
import static com.mibac.dots.wen.util.Logger.log;

import java.awt.geom.Point2D.Double;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

public class WorldModel implements Serializable {
    public static final String EXTENSION = ".dw";
    private transient File path;
    private static final long serialVersionUID = 1L;
    private Vector<Creature> creatures;
    private Vector<Food> food;
    private double goodMutationChance;
    private double mutationRate;
    private int width, height;
    private int selectedCreature;
    private int speedFactor;
    private int maxFoodAmount;
    private short foodCreationRatio;

    WorldModel(Vector<Creature> creatures, Vector<Food> food, double goodMutationChance,
            double mutationRate, int width, int height, int speedFactor, int maxFoodAmount,
            short foodCreationRatio) {
        this.creatures = new Vector<>();
        this.food = new Vector<>();
        this.goodMutationChance = goodMutationChance;
        this.mutationRate = mutationRate;
        this.width = width;
        this.height = height;
        this.selectedCreature = -1;
        this.speedFactor = speedFactor;
        this.maxFoodAmount = maxFoodAmount;
        this.foodCreationRatio = foodCreationRatio;

        creatures.stream().forEach(c -> addCreature(c));
        food.stream().forEach(f -> addFood(f));
    }

    public File getPath() {
        return path;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public double getGoodMutationChance() {
        return goodMutationChance;
    }

    public void setGoodMutationChance(double goodMutationChance) {
        this.goodMutationChance = goodMutationChance;
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

    public void removeEntity(Entity entity) {
        if (entity instanceof Creature)
            removeCreature((Creature) entity);
        else if (entity instanceof Food)
            removeFood((Food) entity);
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

    public void save(File directory) {
        try {
            if (!directory.getAbsolutePath().endsWith(EXTENSION))
                directory = new File(directory.getAbsoluteFile() + EXTENSION);
            if (!directory.exists())
                directory.createNewFile();

            FileOutputStream fos = new FileOutputStream(directory);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WorldModel load(File directory)
            throws IOException, ClassNotFoundException, InvalidClassException {
        if (!directory.getAbsolutePath().endsWith(EXTENSION))
            directory = new File(directory.getAbsolutePath() + EXTENSION);

        FileInputStream fis = new FileInputStream(directory);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        WorldModel model = (WorldModel) ois.readObject();
        ois.close();

        model.getCreatures().forEach(c -> c.getAI().setWorldModel(model).setCreature(c));

        return model;
    }

    private Entity fixPosition(Entity entity) {
        if (entity.getPosition().getX() > width)
            entity.getPosition().x = width;
        if (entity.getPosition().getY() > height)
            entity.getPosition().y = height;

        return entity;
    }

    public static class Builder {
        private Vector<Creature> creatures;
        private Vector<Food> food;
        private double goodMutationChance;
        private double mutationRate;
        private int width, height;
        private int speedFactor;
        private int maxFoodAmount;
        private short foodCreationRatio;

        public Builder(int width, int height) {
            this.creatures = new Vector<>();
            this.food = new Vector<>();
            this.goodMutationChance = 0.6;
            this.mutationRate = 0.1;
            this.width = width;
            this.height = height;
            this.speedFactor = 1;
            this.foodCreationRatio = 50;
            this.maxFoodAmount = 500;
        }

        public Builder addCreature(Creature creature) {
            creatures.add(creature);
            return this;
        }

        public Builder addFood(Food food) {
            this.food.add(food);
            return this;
        }

        public Builder generateRandomWorld(int creatureAmount, int foodAmount) {
            for (int i = 0; i < creatureAmount; i++)
                creatures.add(
                        new Creature(new Double(Math.random() * width, Math.random() * height)));

            for (int i = 0; i < foodAmount; i++)
                food.add(new Food(new Double(Math.random() * width, Math.random() * height), 1));

            return this;
        }

        public Builder setSpeedFactor(int speedFactor) {
            this.speedFactor = speedFactor;
            return this;
        }

        public Builder setFoodCreationRatio(short foodCreationRatio) {
            this.foodCreationRatio = foodCreationRatio;
            return this;
        }

        public Builder setMaxFoodAmount(int maxFoodAmount) {
            this.maxFoodAmount = maxFoodAmount;
            return this;
        }

        public Builder setMutationRate(double mutationRate) {
            this.mutationRate = mutationRate;
            return this;
        }

        public Builder setGoodMutationChance(double goodMutationChance) {
            this.goodMutationChance = goodMutationChance;
            return this;
        }

        public WorldModel build() {
            return new WorldModel(creatures, food, goodMutationChance, mutationRate, width, height,
                    speedFactor, maxFoodAmount, foodCreationRatio);
        }
    }
}

package com.mibac.dots.wen.controller;

import static com.mibac.dots.wen.util.Logger.BREED;
import static com.mibac.dots.wen.util.Logger.DEATH;
import static com.mibac.dots.wen.util.Logger.ENERGY;
import static com.mibac.dots.wen.util.Logger.MOVE;
import static com.mibac.dots.wen.util.Logger.log;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import com.mibac.dots.wen.creatures.Creature;
import com.mibac.dots.wen.creatures.Creature.Gender;
import com.mibac.dots.wen.creatures.Food;
import com.mibac.dots.wen.creatures.WorldModel;

public class WorldUpdater {
    private Vector<Creature> newBornList;
    private WorldModel model;

    public WorldUpdater(WorldModel model) {
        this.newBornList = new Vector<>();
        this.model = model;
    }

    public void update(double delta) {
        delta *= model.getSpeedFactor();
        for (Iterator<Creature> i = model.getCreatures().iterator(); i.hasNext();) {
            Creature creature = i.next();
            boolean a = true;
            handleMoving(creature, delta);
            handleMating(creature, delta);
            handleEating(creature, delta);

            if (creature.isPregnant())
                handlePregnancy(creature, delta);

            a = handleAgeing(creature, delta, i);
            if (a)
                a = handleFatigue(creature, delta, i);
        }

        for (Iterator<Creature> i = newBornList.iterator(); i.hasNext();) {
            model.addCreature(i.next());
            i.remove();
        }

        generateFood();
    }

    private void handlePregnancy(Creature creature, double delta) {
        if (creature.getBreedTime() > 0)
            creature.setBreedTime(creature.getBreedTime() - creature.getBreedSpeed() * delta);
        else {
            creature.setBreedTime(creature.getBreedLength());
            creature.setPregnant(false);
            Double motherPosition = creature.getPosition();

            log("Born:", BREED);

            ArrayList<Creature> newBorn = creature.getFetuses();
            for (Creature c : newBorn) {
                c.setPosition(new Double(motherPosition.getX(), motherPosition.getY()));
                newBornList.add(c);
                log("- " + c, BREED);
            }

            creature.clearFetuses();
        }
    }

    private void generateFood() {
        for (int i = 0; i < model.getSpeedFactor(); i++)
            if (model.getFood().size() >= model.getMaxFoodAmount())
                break;
            else if (Math.random() * 100 > model.getFoodCreationRatio())
                model.addFood(new Food(new Double(model.getWidth() * Math.random(),
                        model.getHeight() * Math.random()), 100 * Math.random()));
    }

    private void handleMoving(Creature creature, double delta) {
        if (creature.getTarget() != null) {
            double speed = creature.getSpeed() * delta;
            Double target = creature.getTarget();
            Double position = creature.getPosition();

            if (target.distance(position) > speed * 1.25) {
                double x = target.getX() - position.getX();
                double y = target.getY() - position.getY();
                double alpha = speed / Math.sqrt(x * x + y * y);

                creature.getPosition().x += x * alpha;
                creature.getPosition().y += y * alpha;
                log("x change: " + x * alpha + "\n       y change: " + y * alpha, MOVE);
            } else {
                creature.setPosition(creature.getTarget());
                log("target reached (speed = " + speed + ", position = " + position + ", target = "
                        + target + ")", MOVE);
                creature.setTarget(null);
            }
        } else
            log("creature's target is equal to null", MOVE);
    }

    private void handleMating(Creature creature, double delta) {
        if (creature.getBreedCooldownTime() > 0)
            creature.setBreedCooldownTime(creature.getBreedCooldownTime() - delta);

        if (creature.getEnergy() < creature.getMatingEnergyNeeded() || creature.isPregnant()
                || creature.getBreedCooldownTime() > 0)
            return;

        Iterator<Creature> secondIterator = model.getCreatures().iterator();

        while (secondIterator.hasNext()) {
            Creature secondCreature = secondIterator.next();

            if (creature.getPosition().distance(secondCreature.getPosition()) < creature.getSpeed()
                    * delta * 1.25
                    && secondCreature.getEnergy() > secondCreature.getMatingEnergyNeeded()
                    && creature.getGender() != secondCreature.getGender()
                    && secondCreature.getBreedCooldownTime() <= 0 && !secondCreature.isPregnant()) {

                Creature father;
                Creature mother;

                if (creature.getGender() == Gender.MALE) {
                    father = creature;
                    mother = secondCreature;
                } else {
                    father = secondCreature;
                    mother = creature;
                }

                father.setBreedCooldownTime(father.getBreedCooldown());
                father.setEnergy(father.getEnergy() - father.getMatingEnergyNeeded());
                mother.setBreedCooldownTime(mother.getBreedCooldown());
                mother.setEnergy(mother.getEnergy() - mother.getMatingEnergyNeeded());
                int fetusAmount =
                        (int) Math.round((mother.getBreedFactor() + father.getBreedFactor() / 4) // father
                                                                                                 // has
                                                                                                 // 1/4th
                                / 1.25 * Math.random());
                if (fetusAmount > 0) {
                    log("Got pregnant", BREED);
                    mother.setPregnant(true);

                    for (int i = 0; i < Math.round(mother.getBreedFactor() * Math.random()); i++)
                        mother.addFetus(createFetus(father, mother));
                }
            }
        }
    }

    private Creature createFetus(Creature father, Creature mother) {
        double maxEnergy =
                mutate(father.getMaxEnergy() + mother.getMaxEnergy(), 2, model.getMutationRate());
        double maxAge = mutate(father.getMaxAge() + mother.getMaxAge(), 2, model.getMutationRate());
        double speed = mutate(father.getSpeed() + mother.getSpeed(), 2, model.getMutationRate());
        double visionRange = mutate(father.getVisionRange() + mother.getVisionRange(), 2,
                model.getMutationRate());
        double matingEnergyNeeded =
                mutate(father.getMatingEnergyNeeded() + mother.getMatingEnergyNeeded(), 2,
                        model.getMutationRate());
        double breedLength = mutate(father.getBreedLength() + mother.getBreedLength(), 2,
                model.getMutationRate());
        double breedSpeed =
                mutate(father.getBreedSpeed() + mother.getBreedSpeed(), 2, model.getMutationRate());
        double breedCooldown = mutate(father.getBreedCooldown() + mother.getBreedCooldown(), 2,
                model.getMutationRate());
        double breedFactor = mutate(father.getBreedFactor() + mother.getBreedFactor(), 2,
                model.getMutationRate());
        Gender gender = Math.random() >= 0.5 ? Gender.MALE : Gender.FEMALE;

        return new Creature(null, mother.getAI(), gender, 0, maxAge, maxEnergy, maxEnergy, speed,
                visionRange, matingEnergyNeeded, breedLength, breedSpeed, breedCooldown,
                breedFactor);
    }

    private double mutate(double sum, int n, double mutationRate) {
        return sum / n * generateMutationFactor(mutationRate);
    }

    private double generateMutationFactor(double mutationRate) {
        if (Math.random() >= 0.5)
            return 1 + mutationRate;
        else
            return 1 - mutationRate;
    }

    private void handleEating(Creature creature, double delta) {
        for (Iterator<Food> i = model.getFood().iterator(); i.hasNext();) {
            Food food = i.next();

            if (creature.getPosition().distance(food.getPosition()) < creature.getSpeed() * delta
                    * 1.25) {
                creature.setEnergy(creature.getEnergy() + food.getValue());
                log("ate food with value of " + food.getValue(), ENERGY);
                i.remove();
            }
        }

    }

    private boolean handleAgeing(Creature creature, double delta, Iterator<Creature> iterator) {
        creature.setAge(creature.getAge() + delta);

        if (creature.getAge() >= creature.getMaxAge()) {
            iterator.remove();
            log(" age ", DEATH);
            return false;
        }

        return true;
    }

    private boolean handleFatigue(Creature creature, double delta, Iterator<Creature> iterator) {
        double fatigue = 2;

        fatigue += creature.getAI().getThinkLength() * 0.0025;

        if (creature.getTarget() != null)
            fatigue += creature.getSpeed();

        if (creature.isPregnant())
            fatigue += creature.getBreedSpeed() + creature.getFetuses().size() * 0.15;

        log("lost " + fatigue * delta, ENERGY);

        creature.setEnergy(creature.getEnergy() - fatigue * delta);

        if (creature.getEnergy() <= 0) {
            iterator.remove();
            log("energy", DEATH);
            return false;
        }

        return true;
    }
}

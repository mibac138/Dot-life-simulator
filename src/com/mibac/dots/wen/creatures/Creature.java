package com.mibac.dots.wen.creatures;

import java.awt.Color;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.HashMap;

import com.mibac.dots.wen.ai.BetterAI;
import com.mibac.dots.wen.ai.EntityAI;
import com.mibac.dots.wen.util.ColorAnimationData;

public class Creature extends Entity {
    private static final long serialVersionUID = 1L;
    public static final int MAX_FETUSES = 8;

    public enum Gender {
        MALE, FEMALE
    };

    private transient Double target;
    private transient Color targetColor;
    private transient ColorAnimationData animData;
    private EntityAI ai;
    private Gender gender;
    private HashMap<Creature, java.lang.Double> relations;

    private double age;
    private double maxAge;
    private double energy;
    private double maxEnergy;
    private double speed;
    private double visionRange;

    private double matingEnergyNeeded;
    private double breedLength;
    private double breedSpeed;
    private double breedTime;
    private double breedCooldown;
    private double breedCooldownTime;
    private double breedFactor;
    private ArrayList<Creature> fetuses;

    private boolean pregnant;

    public Creature(Double position) {
        this(position, new BetterAI(), Math.random() >= 0.5 ? Gender.MALE : Gender.FEMALE, 0, 500,
                1000, 1000, 24, 100, 100, 200, 5, 60, 1.75);
    }

    public Creature(Double position, EntityAI ai, Gender gender, double age, double maxAge,
            double energy, double maxEnergy, double speed, double visionRange,
            double matingEnergyNeeded, double breedLength, double breedSpeed, double breedCooldown,
            double breedFactor) {
        this.position = position;
        this.ai = ai;
        this.gender = gender;
        this.age = age;
        this.maxAge = maxAge;
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.speed = speed;
        this.visionRange = visionRange;

        this.matingEnergyNeeded = matingEnergyNeeded;
        this.breedLength = breedLength;
        this.breedSpeed = breedSpeed;
        this.breedCooldown = breedCooldown;
        this.breedCooldownTime = 0;
        this.breedFactor = breedFactor;
        this.fetuses = new ArrayList<>();
        this.pregnant = false;

        this.target = null;
        this.relations = new HashMap<>();
    }

    public void update() {
        ai.update();
    }

    public HashMap<Creature, java.lang.Double> getRelations() {
        return relations;
    }

    public void setRelations(HashMap<Creature, java.lang.Double> relations) {
        this.relations = relations;
    }

    public Double getTarget() {
        return target;
    }

    public void setTarget(Double target) {
        this.target = target;
    }

    public Color getTargetColor() {
        return targetColor;
    }

    public void setTargetColor(Color targetColor) {
        this.targetColor = targetColor;
    }

    public static int getColorDiff(int r1, int g1, int b1, int r2, int g2, int b2) {
        return Math.abs(r2 - r1) + Math.abs(g2 - g1) + Math.abs(b2 - b1);
    }

    public static int getColorDiff(Color a, Color b) {
        return getColorDiff(a.getRed(), a.getGreen(), a.getBlue(), b.getRed(), b.getGreen(),
                b.getBlue());
    }

    public EntityAI getAI() {
        return ai;
    }

    public void setAI(EntityAI ai) {
        this.ai = ai;
    }

    public Gender getGender() {
        return gender;
    }

    /*
     * public void setGender(Gender gender) { this.gender = gender; }
     */

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = clamp(age, maxAge, 0);
    }

    public double getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(double maxAge) {
        this.maxAge = maxAge;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = clamp(energy, maxEnergy, 0);
    }

    public double getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(double maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getVisionRange() {
        return visionRange;
    }

    public void setVisionRange(double visionRange) {
        this.visionRange = visionRange;

    }

    public double getMatingEnergyNeeded() {
        return matingEnergyNeeded;
    }

    public void setMatingEnergyNeeded(double matingEnergyNeeded) {
        this.matingEnergyNeeded = matingEnergyNeeded;
    }

    public double getBreedLength() {
        return breedLength;
    }

    public void setBreedLength(double breedLength) {
        this.breedLength = breedLength;
    }

    public double getBreedSpeed() {
        return breedSpeed;
    }

    public void setBreedSpeed(double breedSpeed) {
        this.breedSpeed = breedSpeed;
    }

    public double getBreedTime() {
        return breedTime;
    }

    public void setBreedTime(double breedTime) {
        this.breedTime = breedTime;
    }

    public double getBreedCooldown() {
        return breedCooldown;
    }

    public void setBreedCooldown(double breedCooldown) {
        this.breedCooldown = breedCooldown;
    }

    public double getBreedCooldownTime() {
        return breedCooldownTime;
    }

    public void setBreedCooldownTime(double breedCooldownTime) {
        this.breedCooldownTime = clamp(breedCooldownTime, breedCooldown, 0);
    }

    public double getBreedFactor() {
        return breedFactor;
    }

    public void setBreedFactor(double breedFactor) {
        this.breedFactor = breedFactor;
    }

    public ArrayList<Creature> getFetuses() {
        return fetuses;
    }

    public void addFetus(Creature fetus) {
        fetuses.add(fetus);
    }

    public void clearFetuses() {
        this.fetuses.clear();
    }

    public boolean isPregnant() {
        return pregnant;
    }

    public void setPregnant(boolean pregnant) {
        this.pregnant = pregnant;
    }

    private double clamp(double n, double max, double min) {
        if (n > max)
            return max;
        if (n < min)
            return min;

        return n;
    }

    @Override
    public String toString() {
        return "Creature [target=" + target + ", ai=" + ai + ", gender=" + gender + ", age=" + age
                + ", maxAge=" + maxAge + ", energy=" + energy + ", maxEnergy=" + maxEnergy
                + ", speed=" + speed + ", visionRange=" + visionRange + ", matingEnergyNeeded="
                + matingEnergyNeeded + ", breedLength=" + breedLength + ", breedSpeed=" + breedSpeed
                + ", breedTime=" + breedTime + ", breedCooldown=" + breedCooldown
                + ", breedCooldownTime=" + breedCooldownTime + ", breedFactor=" + breedFactor
                + ", fetuses=" + fetuses + ", pregnant=" + pregnant + ", position=" + position
                + "]";
    }

    public ColorAnimationData getColorAnimation() {
        return animData;
    }

    public void setColorAnimation(ColorAnimationData animData) {
        this.animData = animData;
    }
}

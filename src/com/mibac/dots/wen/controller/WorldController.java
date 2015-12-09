package com.mibac.dots.wen.controller;

import java.awt.geom.Point2D.Double;
import java.util.Vector;

import com.mibac.dots.wen.creatures.Creature;
import com.mibac.dots.wen.creatures.Entity;
import com.mibac.dots.wen.creatures.Food;
import com.mibac.dots.wen.creatures.WorldModel;
import com.mibac.dots.wen.view.MainWindow;
import com.mibac.dots.wen.view.WorldView;

public class WorldController {
    private WorldModel model;
    private WorldView view;
    private WorldUpdater updater;
    private MainWindow window;

    public WorldController(WorldModel model, WorldView view) {
        this.model = model;
        this.view = view;
        this.updater = new WorldUpdater(model);
    }

    public Vector<Creature> getCreatures() {
        return model.getCreatures();
    }

    public Vector<Food> getFood() {
        return model.getFood();
    }

    public void update(double delta) {
        updater.update(delta * model.getSpeedFactor());
        window.displayEntity(model.getSelectedCreature());
    }

    public void setSelectedCreature(Creature c) {
        model.setSelectedCreatureIndex(model.getCreatures().indexOf(c));
    }

    public void changeZoomFactor(int change) {
        int zoomFactor = view.getZoomFactor();

        if (zoomFactor + change > 0)
            view.setZoomFactor(zoomFactor + change);
    }

    public void setZoomFactor(int zoomFactor) {
        if (zoomFactor > 0)
            view.setZoomFactor(zoomFactor);
    }

    public int getZoomFactor() {
        return view.getZoomFactor();
    }

    public int getMaxSpeedFactor() {
        return model.getMaxSpeedFactor();
    }

    public void setMaxSpeedFactor(int maxSpeedFactor) {
        model.setMaxSpeedFactor(maxSpeedFactor);
    }

    public void setSpeedFactor(int speedFactor) {
        model.setSpeedFactor(speedFactor);
    }

    public int getSpeedFactor() {
        return model.getSpeedFactor();
    }

    public void changeSpeedFactor(int change) {
        setSpeedFactor(getSpeedFactor() + change);
    }

    public void setViewInputFocus() {
        view.requestFocus();
    }

    public void addCreature(Entity creature) {
        model.addEntity(creature);
    }

    public Creature getCreature(Double position) {
        return view.getCreature(position);
    }

    public int getOffsetX() {
        return view.getOffsetX();
    }

    public void changeOffsetX(int change) {
        view.setOffsetX(view.getOffsetX() + change);
    }

    public int getOffsetY() {
        return view.getOffsetY();
    }

    public void changeOffsetY(int change) {
        view.setOffsetY(view.getOffsetY() + change);
    }

    public void setMainWindow(MainWindow window) {
        this.window = window;
    }

    public void setMaxFoodAmount(int maxFoodAmount) {
        model.setMaxFoodAmount(maxFoodAmount);
    }

    public int getMaxFoodAmount() {
        return model.getMaxFoodAmount();
    }
}

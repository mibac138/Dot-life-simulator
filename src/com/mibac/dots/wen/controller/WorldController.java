package com.mibac.dots.wen.controller;

import java.awt.geom.Point2D.Double;
import java.util.Vector;

import javax.swing.JSlider;

import com.mibac.dots.wen.creatures.Creature;
import com.mibac.dots.wen.creatures.Entity;
import com.mibac.dots.wen.creatures.Food;
import com.mibac.dots.wen.creatures.WorldModel;
import com.mibac.dots.wen.view.Window;
import com.mibac.dots.wen.view.WorldInputListener;
import com.mibac.dots.wen.view.WorldView;

public class WorldController {
    public static int MAX_SPEED_FACTOR = 10;
    private WorldModel model;
    private WorldInputListener in;
    private WorldView view;
    private WorldUpdater updater;
    private Window window;

    public WorldController(WorldModel model, WorldInputListener in, WorldView view) {
        init(model, in, view, new WorldUpdater(model));
    }

    public WorldController(WorldModel model) {
        init(model, new WorldInputListener(this), new WorldView(model), new WorldUpdater(model));
    }

    private void init(WorldModel model, WorldInputListener in, WorldView view,
            WorldUpdater updater) {
        this.model = model;
        this.in = in;
        this.view = view;
        this.view.addKeyListener(in);
        this.view.addMouseListener(in);
        this.view.addMouseWheelListener(in);
        this.updater = updater;
    }

    public Vector<Creature> getCreatures() {
        return model.getCreatures();
    }

    public Vector<Food> getFood() {
        return model.getFood();
    }

    public void update(double delta) {
        JSlider slider = window.getSpeedSlider();
        slider.setMaximum(WorldController.MAX_SPEED_FACTOR);
        window.setSpeedSlider(slider);
        handleInput();

        if (model.getSpeedFactor() > 0) {
            updater.update(delta * model.getSpeedFactor());
            window.displayEntity(model.getSelectedCreature());
            view.repaint();
        }
    }

    public void setSelectedCreature(Creature c) {
        model.setSelectedCreatureIndex(model.getCreatures().indexOf(c));
    }

    public void changeZoomFactor(int change) {
        setZoomFactor(view.getZoomFactor() + change);
    }

    public void setZoomFactor(int zoomFactor) {
        if (zoomFactor > 0)
            view.setZoomFactor(zoomFactor);
    }

    public int getZoomFactor() {
        return view.getZoomFactor();
    }

    public int getMaxSpeedFactor() {
        return WorldController.MAX_SPEED_FACTOR;
    }

    public void setMaxSpeedFactor(int maxSpeedFactor) {
        WorldController.MAX_SPEED_FACTOR = maxSpeedFactor;
    }

    public void setSpeedFactor(int speedFactor) {
        model.setSpeedFactor(speedFactor);
        JSlider speed = window.getSpeedSlider();
        speed.setValue(model.getSpeedFactor());
        window.setSpeedSlider(speed);
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

    public void removeEntity(Entity e) {
        model.removeEntity(e);
    }

    public void addEntity(Entity e) {
        model.addEntity(e);
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

    public void setWindow(Window window) {
        this.window = window;
    }

    public void setMaxFoodAmount(int maxFoodAmount) {
        model.setMaxFoodAmount(maxFoodAmount);
    }

    public int getMaxFoodAmount() {
        return model.getMaxFoodAmount();
    }

    public int getWorldWidth() {
        return model.getWidth();
    }

    public int getWorldHeight() {
        return model.getHeight();
    }

    public void handleInput() {
        in.handleKeys();
        in.handleMouse(view);
    }

    public WorldView getView() {
        return view;
    }
}

package com.mibac.dots.wen.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D.Double;

import javax.swing.JPanel;

import com.mibac.dots.wen.creatures.Creature;
import com.mibac.dots.wen.creatures.Creature.Gender;
import com.mibac.dots.wen.creatures.WorldModel;
import com.mibac.dots.wen.util.Debug;

public class WorldView extends JPanel {
    private static final long serialVersionUID = 1L;
    public static int CREATURE_SIZE = 12;
    public static int FOOD_SIZE = 8;

    private WorldModel world;

    private int zoomFactor; // it's actually unzoom (?) factor -- the higher the value the view is
                            // getting smaller
    private int offsetX;
    private int offsetY;

    public WorldView(WorldModel world) {
        this.world = world;
        this.zoomFactor = 1;
        this.offsetX = 0;
        this.offsetY = 0;

        setFocusable(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int creature2 = CREATURE_SIZE / 2;
        int food2 = FOOD_SIZE / 2;

        int foodZoomed = applyZoom(FOOD_SIZE);
        int creatureZoomed = applyZoom(CREATURE_SIZE);

        g.setColor(Color.GREEN);
        world.getFood().stream()
                .forEach(food -> g.fillOval(applyZoom(food.getPosition().getX() - food2 + offsetX),
                        applyZoom(food.getPosition().getY() - food2 + offsetY), foodZoomed,
                        foodZoomed));

        world.getCreatures().stream().forEach(creature -> {
            if (creature.getGender() == Gender.MALE)
                g.setColor(Color.BLUE);
            else
                g.setColor(Color.PINK);
            g.fillOval(applyZoom(creature.getPosition().getX() - creature2 + offsetX),
                    applyZoom(creature.getPosition().getY() - creature2 + offsetY), creatureZoomed,
                    creatureZoomed);
        });

        Creature selected = world.getSelectedCreature();

        if (selected != null) {
            g.setColor(Color.RED);
            g.drawOval(applyZoom(selected.getPosition().getX() - creature2 + offsetX),
                    applyZoom(selected.getPosition().getY() - creature2 + offsetY), creatureZoomed,
                    creatureZoomed);
        }

        if (Debug.DEBUG) {
            g.setColor(Color.RED);
            if (Debug.DRAW_BORDER)
                g.drawRect(applyZoom(offsetX), applyZoom(offsetY), applyZoom(world.getWidth()),
                        applyZoom(world.getHeight()));

            world.getCreatures().stream().forEach(creature -> {
                if (Debug.DRAW_PATH && creature.getTarget() != null)
                    g.drawLine(applyZoom(creature.getPosition().getX() + offsetX),
                            applyZoom(creature.getPosition().getY() + offsetY),
                            applyZoom(creature.getTarget().getX() + offsetX),
                            applyZoom(creature.getTarget().getY() + offsetY));

                if (Debug.DRAW_VISION) {
                    int vision = (int) creature.getVisionRange();

                    g.drawOval(applyZoom(creature.getPosition().getX() - vision + offsetX),
                            applyZoom(creature.getPosition().getY() - vision + offsetY),
                            applyZoom(vision * 2), applyZoom(vision * 2));
                }
            });
        }
    }

    public Creature getCreature(Double position) {
        return world
                .getCreatures().stream().filter(c -> c.getPosition()
                        .distance(position) < WorldView.CREATURE_SIZE / zoomFactor)
                .findFirst().orElse(null);
    }

    public int getZoomFactor() {
        return zoomFactor;
    }

    public void setZoomFactor(int zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    public void setWorldModel(WorldModel world) {
        this.world = world;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    private int applyZoom(double n) {
        return (int) (n / zoomFactor);
    }
}

package com.mibac.dots.wen.view;


import static com.mibac.dots.wen.util.Debug.DRAW;
import static com.mibac.dots.wen.util.Debug.DRAW_BORDER;
import static com.mibac.dots.wen.util.Debug.DRAW_PATH;
import static com.mibac.dots.wen.util.Debug.DRAW_VISION;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D.Double;

import javax.swing.JPanel;

import com.mibac.dots.wen.Creatures;
import com.mibac.dots.wen.creatures.Creature;
import com.mibac.dots.wen.creatures.WorldModel;
import com.mibac.dots.wen.view.themes.Theme;

public class WorldView extends JPanel {
    private static final long serialVersionUID = 1L;
    public static int CREATURE_SIZE = 12;
    public static int FOOD_SIZE = 8;

    private Theme t;
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
        update();
    }

    public void update() {
        t = Creatures.getTheme();
    }

    @Override
    public void paint(Graphics g) {
        // TODO Double buffered rendering (?)
        g.setColor(t.getBackgroundColor());
        g.fillRect(0, 0, getWidth(), getHeight());
        int c2 = CREATURE_SIZE / 2;
        int f2 = FOOD_SIZE / 2;

        int foodZoomed = applyZoom(FOOD_SIZE);
        int creatureZoomed = applyZoom(CREATURE_SIZE);

        world.getFood().stream().forEach(f -> {
            g.setColor(f.getColor());
            int x = applyZoom(f.getPosition().getX() - f2 + offsetX);
            int y = applyZoom(f.getPosition().getY() - f2 + offsetY);
            g.fillOval(x, y, foodZoomed, foodZoomed);
            g.setColor(Color.BLACK);
            g.drawOval(x, y, foodZoomed, foodZoomed);
        });

        world.getCreatures().stream().forEach(c -> {
            g.setColor(c.getColor());
            int x = applyZoom(c.getPosition().getX() - c2 + offsetX);
            int y = applyZoom(c.getPosition().getY() - c2 + offsetY);
            g.fillOval(x, y, creatureZoomed, creatureZoomed);
            g.setColor(Color.BLACK);
            g.drawOval(x, y, creatureZoomed, creatureZoomed);
        });

        Creature selected = world.getSelectedCreature();

        if (selected != null) {
            g.setColor(t.getSelectedCreatureColor());
            g.drawOval(applyZoom(selected.getPosition().getX() - c2 + offsetX),
                    applyZoom(selected.getPosition().getY() - c2 + offsetY), creatureZoomed,
                    creatureZoomed);
        }

        if (DRAW.isEnabled()) {
            if (DRAW_BORDER.isEnabled()) {
                g.setColor(t.getBorderColor());
                g.drawRect(applyZoom(offsetX), applyZoom(offsetY), applyZoom(world.getWidth()),
                        applyZoom(world.getHeight()));
            }

            world.getCreatures().stream().forEach(creature -> {
                if (DRAW_PATH.isEnabled() && creature.getTarget() != null) {
                    g.setColor(t.getPathColor());
                    g.drawLine(applyZoom(creature.getPosition().getX() + offsetX),
                            applyZoom(creature.getPosition().getY() + offsetY),
                            applyZoom(creature.getTarget().getX() + offsetX),
                            applyZoom(creature.getTarget().getY() + offsetY));
                }

                if (DRAW_VISION.isEnabled()) {
                    int vision = (int) creature.getVisionRange();

                    g.setColor(t.getVisionRangeColor());
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

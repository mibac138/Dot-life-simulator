package com.mibac.dots.wen.view;


import static com.mibac.dots.wen.util.Debug.PRINT_INPUT;
import static com.mibac.dots.wen.util.Logger.log;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D.Double;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.mibac.dots.wen.controller.WorldController;
import com.mibac.dots.wen.creatures.Creature;

public class WorldInputListener implements KeyListener, MouseListener, MouseWheelListener {
    private WorldController controller;
    private final Set<Integer> pressed;
    private boolean LMB; // left mouse button
    private boolean MMB; // middle mouse button (wheel)
    private boolean RMB; // right mouse button
    private Point lastPosition;
    private Point click;

    public WorldInputListener(WorldController controller) {
        this.controller = controller;
        this.pressed = new HashSet<>();
        this.LMB = false;
        this.MMB = false;
        this.RMB = false;
        this.lastPosition = MouseInfo.getPointerInfo().getLocation();
    }

    public void handleKeys() {
        for (Iterator<Integer> i = pressed.iterator(); i.hasNext();) {
            int key = i.next();

            if (key == KeyEvent.VK_RIGHT)
                controller.changeOffsetX(-5);
            else if (key == KeyEvent.VK_LEFT)
                controller.changeOffsetX(5);
            else if (key == KeyEvent.VK_UP)
                controller.changeOffsetY(5);
            else if (key == KeyEvent.VK_DOWN)
                controller.changeOffsetY(-5);
            else if (key == KeyEvent.VK_ADD) {
                controller.changeSpeed(1);
                i.remove();
            } else if (key == KeyEvent.VK_SUBTRACT) {
                controller.changeSpeed(-1);
                i.remove();
            }
        }
    }

    public void handleMouse(WorldView view) {
        double dx = MouseInfo.getPointerInfo().getLocation().getX() - lastPosition.getX();
        double dy = MouseInfo.getPointerInfo().getLocation().getY() - lastPosition.getY();

        if (LMB || MMB || RMB) {
            if (Math.abs(dx) > 0)
                controller.changeOffsetX((int) dx * view.getZoomFactor());
            if (Math.abs(dy) > 0)
                controller.changeOffsetY((int) dy * view.getZoomFactor());
        }

        if (click != null) {
            Creature c = controller.getCreature(new Double(click.x, click.y));
            log("Clicked: Creature c = " + c, PRINT_INPUT);

            controller.setSelectedCreature(c);
        }

        lastPosition = MouseInfo.getPointerInfo().getLocation();
        click = null;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        controller.changeZoomFactor(e.getWheelRotation());
        log("Wheel moved by " + e.getWheelRotation(), PRINT_INPUT);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        click = screenToView(e.getPoint());
        log("Clicked at " + click, PRINT_INPUT);
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        controller.setViewInputFocus();

        if (e.getButton() == MouseEvent.BUTTON1) {
            LMB = true;
            log("Pressed LMB", PRINT_INPUT);
        } else if (e.getButton() == MouseEvent.BUTTON2) {
            MMB = true;
            log("Pressed MMB", PRINT_INPUT);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            RMB = true;
            log("Pressed RMB", PRINT_INPUT);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            LMB = false;
            log("Released LMB", PRINT_INPUT);
        } else if (e.getButton() == MouseEvent.BUTTON2) {
            MMB = false;
            log("Released MMB", PRINT_INPUT);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            RMB = false;
            log("Released RMB", PRINT_INPUT);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressed.add(e.getKeyCode());
        log("Pressed " + e.getKeyChar(), PRINT_INPUT);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyCode());
        log("Released " + e.getKeyChar(), PRINT_INPUT);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private Point screenToView(Point point) {
        point.x = point.x * controller.getZoomFactor() - controller.getOffsetX();
        point.y = point.y * controller.getZoomFactor() - controller.getOffsetY();
        return point;
    }

}

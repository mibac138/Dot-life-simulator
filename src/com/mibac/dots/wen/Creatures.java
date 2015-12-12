package com.mibac.dots.wen;

import static com.mibac.dots.wen.util.Debug.Type.PRINT_OTHER;
import static com.mibac.dots.wen.util.Logger.log;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D.Double;

import javax.swing.Timer;
import javax.swing.UIManager;

import com.mibac.dots.wen.controller.WorldController;
import com.mibac.dots.wen.creatures.Creature;
import com.mibac.dots.wen.creatures.WorldBuilder;
import com.mibac.dots.wen.creatures.WorldModel;
import com.mibac.dots.wen.util.Logger;
import com.mibac.dots.wen.view.MainWindow;
import com.mibac.dots.wen.view.WorldInputListener;
import com.mibac.dots.wen.view.WorldView;

public class Creatures implements ActionListener {
    private static final int REFRESH_TIME = 15;
    private Timer timer;
    private WorldModel model;
    private WorldView view;
    private WorldController controller;
    private WorldInputListener listener;
    private MainWindow window;
    private double time;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        new Creatures();
    }

    public Creatures() {
        if (timer != null && timer.isRunning())
            timer.stop();

        new Logger();
        model = new WorldBuilder(800, 600).generateRandomWorld(20, 50).setMaxSpeedFactor(25)
                .build();
        view = new WorldView(model);
        controller = new WorldController(model, view);
        listener = new WorldInputListener(controller);
        // statistics = new Statistics(worldModel);

        view.addKeyListener(listener);
        view.addMouseListener(listener);
        view.addMouseWheelListener(listener);

        window = new MainWindow(controller, view);

        controller.setMainWindow(window);
        controller.addEntity(new Creature(new Double(900d, 700d)));

        timer = new Timer(REFRESH_TIME, this);
        timer.start();
        time = System.nanoTime();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double currentTime = System.nanoTime();
        double delta = (System.nanoTime() - time) / 1E9;
        time = currentTime;

        log("! DELTA ! " + delta, PRINT_OTHER);

        listener.handleKeys();
        listener.handleMouse(view);

        view.repaint();

        if (model.getSpeedFactor() > 0) {
            for (Creature c : model.getCreatures())
                c.update();

            window.update(delta);
            controller.update(delta);
            // statistics.update(delta);
        }
    }

}

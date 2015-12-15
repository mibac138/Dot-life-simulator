package com.mibac.dots.wen;

import static com.mibac.dots.wen.util.Debug.Type.PRINT_OTHER;
import static com.mibac.dots.wen.util.Logger.log;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.UIManager;

import com.mibac.dots.wen.controller.WorldController;
import com.mibac.dots.wen.creatures.WorldBuilder;
import com.mibac.dots.wen.creatures.WorldModel;
import com.mibac.dots.wen.util.Logger;
import com.mibac.dots.wen.view.MainWindow;
import com.mibac.dots.wen.view.Window;

public class Creatures implements ActionListener {
    private static final int REFRESH_TIME = 15;
    private Timer timer;
    private MainWindow main;
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
        WorldModel a = new WorldBuilder(800, 600).generateRandomWorld(20, 50).setMaxSpeedFactor(25)
                .build();
        WorldModel b = new WorldBuilder(1000, 1000).generateRandomWorld(10, 5000)
                .setMaxFoodAmount(10000).setMaxSpeedFactor(100).build();

        // statistics = new Statistics(worldModel);

        main = new MainWindow();
        main.addWindow(new Window(new WorldController(a)));
        main.addWindow(new Window(new WorldController(b)));

        timer = new Timer(REFRESH_TIME, this);
        timer.start();
        time = System.nanoTime();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double currentTime = System.nanoTime();
        double delta = (currentTime - time) / 1e9;
        time = currentTime;

        log("! DELTA ! " + delta, PRINT_OTHER);

        main.update(delta);
    }
}

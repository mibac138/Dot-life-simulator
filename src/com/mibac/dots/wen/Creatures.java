package com.mibac.dots.wen;


import static com.mibac.dots.wen.util.Debug.PRINT_OTHER;
import static com.mibac.dots.wen.util.Logger.log;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.mibac.dots.wen.controller.WorldController;
import com.mibac.dots.wen.creatures.WorldModel;
import com.mibac.dots.wen.view.MainWindow;
import com.mibac.dots.wen.view.Window;
import com.mibac.dots.wen.view.themes.DefaultTheme;
import com.mibac.dots.wen.view.themes.Theme;

public class Creatures {
    private static final int UPDATE_TIME = 1000 / 60;
    private static final int RENDER_TIME = 1000 / 60;
    private static Theme theme;
    private MainWindow main;

    public static void main(String[] args) {
        new Creatures();
    }

    public Creatures() {
        setTheme(new DefaultTheme());

        WorldModel a = new WorldModel.Builder(800, 600).generateRandomWorld(20, 50).build();
        WorldModel b = new WorldModel.Builder(1000, 1000).generateRandomWorld(10, 5000)
                .setMaxFoodAmount(10000).build();

        // statistics = new Statistics(worldModel);

        main = new MainWindow();
        main.addWindow(new Window(new WorldController(a)));
        main.addWindow(new Window(new WorldController(b)));

        Timer updater = new Timer(UPDATE_TIME, new Updater());
        updater.start();
        Timer renderer = new Timer(RENDER_TIME, new Renderer());
        renderer.start();
    }


    public static Theme getTheme() {
        return theme;
    }

    public static void setTheme(Theme theme) {
        Creatures.theme = theme;

        try {
            UIManager.setLookAndFeel(theme.getLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null,
                    "There was a problem when changing theme! While setting new look and feel exception was thrown! ("
                            + e.getMessage() + ")");
        }
    }

    public class Updater implements ActionListener {
        private double time;

        public Updater() {
            this.time = System.nanoTime();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            double currentTime = System.nanoTime();
            double delta = (currentTime - time) / 1e9;
            time = currentTime;

            log("! UPDATING ! delta:" + delta, PRINT_OTHER);

            main.update(delta);
        }
    }

    public class Renderer implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            log("! RENDERING !", PRINT_OTHER);

            main.render();
        }
    }
}

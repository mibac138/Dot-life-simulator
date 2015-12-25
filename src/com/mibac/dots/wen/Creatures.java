package com.mibac.dots.wen;


import static com.mibac.dots.wen.util.Debug.PRINT_INPUT;
import static com.mibac.dots.wen.util.Debug.PRINT_OTHER;
import static com.mibac.dots.wen.util.Logger.log;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.mibac.dots.wen.controller.WorldController;
import com.mibac.dots.wen.creatures.WorldModel;
import com.mibac.dots.wen.io.Configuration;
import com.mibac.dots.wen.io.CreaturesConfiguration;
import com.mibac.dots.wen.util.Debug;
import com.mibac.dots.wen.view.MainWindow;
import com.mibac.dots.wen.view.Window;
import com.mibac.dots.wen.view.themes.DefaultTheme;
import com.mibac.dots.wen.view.themes.Theme;

public class Creatures {
    public static final File DIRECTORY =
            new File(System.getProperty("user.home") + File.separator + "Desktop");
    public static final String VERSION_NAME = "Tango";
    public static final Modifier VERSION_MODIFIER = Modifier.BETA;
    public static final byte VERSION_MAJOR = 2;
    public static final short VERSION_MINOR = 1;
    private static final int UPDATE_TIME = 1000 / 60;
    private static final int RENDER_TIME = 1000 / 60;
    private static Theme theme;
    private static CreaturesConfiguration config;
    private MainWindow main;

    public static enum Modifier {
        ALPHA, BETA, RELEASE;

        @Override
        public String toString() {
            char[] c = name().toLowerCase().toCharArray();
            c[0] = Character.toUpperCase(c[0]);
            return new String(c);
        }
    }

    /**
     * None arguments are implemented for now
     */
    public static void main(String[] array) {
        String args = "";
        for (int i = 0; i < array.length; i++)
            args += " " + array[i];
        args = args.substring(1);

        try {
            new Creatures(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Creatures(String args) throws Exception {
        WorldModel a = new WorldModel.Builder(800, 600).generateRandomWorld(20, 50).build();
        WorldModel b = new WorldModel.Builder(1000, 1000).generateRandomWorld(10, 5000)
                .setMaxFoodAmount(10000).build();

        setTheme(new DefaultTheme());
        initConfig(DIRECTORY + File.separator + "config.dc", null);
        loadConfig();

        main = new MainWindow();
        main.addWindow(new Window(new WorldController(a)));
        main.addWindow(new Window(new WorldController(b)));

        parseArgs(args);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (config != null)
                    config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        Timer updater = new Timer(UPDATE_TIME, new Updater());
        updater.start();
        Timer renderer = new Timer(RENDER_TIME, new Renderer());
        renderer.start();

        main.setVisible(true);
    }

    public static CreaturesConfiguration getConfig() {
        return config;
    }

    private void loadConfig() {
        for (Debug d : Debug.values())
            d.setEnabled(config.getDebug().get(d));

        WorldController.MAX_SPEED = config.getWorld().getMaxSpeed();
    }

    private void initConfig(String file, LinkedHashMap<String, Object> defaults) {
        LinkedHashMap<String, Object> d = new LinkedHashMap<>();
        d.put("world.maxSpeed", 25);
        d.put("debug", false);
        d.put("debug.draw", true);
        d.put("debug.draw.path", true);
        d.put("debug.draw.vision", true);
        d.put("debug.draw.border", true);
        d.put("debug.print", true);
        d.put("debug.print.energy", true);
        d.put("debug.print.death", true);
        d.put("debug.print.ai", true);
        d.put("debug.print.breed", true);
        d.put("debug.print.move", true);
        d.put("debug.print.input", true);
        d.put("debug.print.other", true);
        d.put("debug.cmd", true);

        if (defaults != null && !defaults.isEmpty())
            d.putAll(defaults);

        try {
            config = new CreaturesConfiguration(new File(file), null);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "There was a error during reading configuration. Creating backup file and using default config");
            e.printStackTrace();
            try {
                File f, backupFile;
                if (!file.endsWith(Configuration.EXTENSION))
                    f = new File(file + Configuration.EXTENSION);
                else
                    f = new File(file);

                backupFile =
                        new File(file.substring(0, file.length() - Configuration.EXTENSION.length())
                                + "-backup-" + System.currentTimeMillis()
                                + Configuration.EXTENSION);
                // trim extension and append "-backup-#millis#.dc"

                byte[] content = Files.readAllBytes(Paths.get(f.toURI()));
                Files.write(Paths.get(backupFile.toURI()), content);
                f.delete();
                config = new CreaturesConfiguration(f, null);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null,
                        "Error occured while creating backup file. Giving up");
                e1.printStackTrace();
                System.exit(1);
            }
        }
    }

    private void parseArgs(String args) {
        String pattern = "(?:--)(?<key>[a-zA-Z]*)(?: ?= ?(?<val>[0-9a-zA-Z]+))?";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(args);

        while (matcher.find()) {
            String s = args.substring(matcher.start(), matcher.end());
            String k = matcher.group(1);
            String v = matcher.group(2);
            log("argument: \"" + s + "\"" + System.getProperty("line.separator")
                    + "       key:      \"" + k + System.getProperty("line.separator")
                    + "       val:      \"" + v + "\"", PRINT_INPUT);
        }
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

    class Updater implements ActionListener {
        private double time;

        public Updater() {
            this.time = System.nanoTime();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            double currentTime = System.nanoTime();
            double delta = (currentTime - time) / 1E9;
            time = currentTime;

            log("! UPDATING ! delta:" + delta, PRINT_OTHER);

            main.update(delta);
        }
    }

    class Renderer implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            log("! RENDERING !", PRINT_OTHER);

            main.render();
        }
    }
}

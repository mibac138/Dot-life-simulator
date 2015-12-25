package com.mibac.dots.wen.view;


import static com.mibac.dots.wen.util.Debug.DEBUG;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InvalidClassException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.mibac.dots.wen.Creatures;
import com.mibac.dots.wen.Creatures.Modifier;
import com.mibac.dots.wen.controller.WorldController;
import com.mibac.dots.wen.creatures.WorldModel;
import com.mibac.dots.wen.util.JCloseableTabbedPane;

public class MainWindow extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem loadWorldItem;
    private JMenuItem saveWorldItem;
    private JMenuItem exportStatisticsItem;
    private JMenuItem closeItem;

    private JMenu worldMenu;
    private JMenuItem createWorldItem;
    private JMenuItem createCreatureItem;
    private JMenuItem createCreaturesItem;

    private JMenu statisticsMenu;
    private JMenuItem showStatisticsItem;

    private JMenu helpMenu;
    private JMenuItem optionsItem;
    private JMenuItem debugOptionsItem;

    private JMenuItem toggleDrawingItem;

    private JCloseableTabbedPane tabbedPane;

    private boolean drawWorld;

    public MainWindow() {
        setVisible(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        String s = "";
        if (Creatures.VERSION_MODIFIER != Modifier.RELEASE)
            s = " " + Creatures.VERSION_MODIFIER.toString();
        setTitle("Dots " + Creatures.VERSION_NAME + s + " (" + Creatures.VERSION_MAJOR + "."
                + Creatures.VERSION_MINOR + ")");
        drawWorld = true;

        initUI();

        setResizable(true);
        setVisible(true);
    }

    private void initUI() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        exportStatisticsItem = new JMenuItem("Export Statistics");
        exportStatisticsItem.addActionListener(this);
        loadWorldItem = new JMenuItem("Load world");
        loadWorldItem.addActionListener(this);
        saveWorldItem = new JMenuItem("Save world");
        saveWorldItem.addActionListener(this);
        closeItem = new JMenuItem("Close");
        closeItem.addActionListener(this);

        fileMenu.add(exportStatisticsItem);
        fileMenu.add(loadWorldItem);
        fileMenu.add(saveWorldItem);
        fileMenu.addSeparator();
        fileMenu.add(closeItem);

        worldMenu = new JMenu("Creation");
        createWorldItem = new JMenuItem("Create World");
        createWorldItem.addActionListener(this);
        worldMenu.add(createWorldItem);
        worldMenu.addSeparator();
        createCreatureItem = new JMenuItem("Create Creature");
        createCreatureItem.addActionListener(this);
        worldMenu.add(createCreatureItem);
        createCreaturesItem = new JMenuItem("Create Creatures");
        createCreaturesItem.addActionListener(this);
        worldMenu.add(createCreaturesItem);

        statisticsMenu = new JMenu("Statistics");
        showStatisticsItem = new JMenuItem("Show Statistics");
        showStatisticsItem.addActionListener(this);
        statisticsMenu.add(showStatisticsItem);

        helpMenu = new JMenu("Help");
        optionsItem = new JMenuItem("Options");
        optionsItem.addActionListener(this);
        debugOptionsItem = new JMenuItem("Debug options");
        debugOptionsItem.addActionListener(this);
        helpMenu.add(optionsItem);
        if (DEBUG.isEnabled())
            helpMenu.add(debugOptionsItem);

        toggleDrawingItem = new JMenuItem("Toggle drawing (on)");
        toggleDrawingItem.addActionListener(this);

        menuBar.add(fileMenu);
        menuBar.add(worldMenu);
        menuBar.add(statisticsMenu);
        menuBar.add(helpMenu);
        menuBar.add(toggleDrawingItem);

        setJMenuBar(menuBar);

        tabbedPane = new JCloseableTabbedPane();

        add(tabbedPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveWorldItem) {
            File dir = askForDirectory("Select directory where the world you want to be saved");

            if (dir == null || dir.getAbsolutePath().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Directory you choosed is incorrect",
                        "Bad directory", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ((Window) tabbedPane.getSelectedComponent()).saveWorld(dir);
        } else if (e.getSource() == loadWorldItem) {
            File dir =
                    askForDirectory("Select directory where the world you want to load is located");

            if (dir == null || dir.getAbsolutePath().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Directory you choosed is incorrect",
                        "Bad directory", JOptionPane.ERROR_MESSAGE);
                return;
            }

            WorldModel model = null;

            try {
                model = WorldModel.load(dir);
            } catch (InvalidClassException ex) {
                String msg = ex.getMessage();
                String s = msg.substring(msg.lastIndexOf('.') + 1);
                s = s.substring(0, s.indexOf(';'));
                JOptionPane.showMessageDialog(this, "Incompatible class (" + s + ")", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error occured while loading world (" + ex.getMessage() + ")", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (model == null) {
                JOptionPane.showMessageDialog(this, "Unknown error occured while loading world",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String name = dir.getName();
            name = name.substring(0, name.lastIndexOf("."));

            addWindow(new Window(new WorldController(model)), name);
        } else if (e.getSource() == closeItem)
            System.exit(0);
        else if (e.getSource() == createWorldItem)
            new CreateWorldView(this);
        else if (e.getSource() == createCreatureItem)
            new CreateCreatureView(getWindow(tabbedPane.getSelectedIndex()).getController());
        else if (e.getSource() == optionsItem)
            new OptionsView();
        else if (e.getSource() == debugOptionsItem)
            new DebugOptionsView();
        else if (e.getSource() == toggleDrawingItem) {
            drawWorld = !drawWorld;
            toggleDrawingItem.setText("Toggle drawing (" + (drawWorld ? "on" : "off") + ")");
        }
    }

    public int getWindowIndex(Window w) {
        return tabbedPane.indexOfComponent(w);
    }

    public Window getWindow(int n) {
        return (Window) tabbedPane.getComponentAt(n);
    }

    public void addWindow(Window w, String title) {
        tabbedPane.addTab(title, w);
    }

    public void addWindow(Window w) {
        addWindow(w, "#" + (tabbedPane.getTabCount() + 1));
    }

    public void update(double delta) {
        for (int i = 0; i < tabbedPane.getComponentCount() - 1; i++)
            if (tabbedPane.getComponentAt(i) instanceof Window)
                ((Window) tabbedPane.getComponentAt(i)).update(delta);
    }


    public void render() {
        for (int i = 0; i < tabbedPane.getComponentCount() - 1; i++)
            if (tabbedPane.getComponentAt(i) instanceof Window)
                ((Window) tabbedPane.getComponentAt(i)).render(drawWorld);
    }

    public static boolean confirmQuit(String what) {
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to close this "
                + what
                + "? Any unsaved progress will be deleted! You can save progress under File menu");
        return confirm == JOptionPane.YES_OPTION;
    }

    private File askForDirectory(String text, File dir) {
        JFileChooser chooser = new JFileChooser();
        if (dir != null)
            chooser.setCurrentDirectory(dir);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileFilter(new FileFilter() {

            @Override
            public String getDescription() {
                return "DWorld files";
            }

            @Override
            public boolean accept(File f) {
                return getExtension(f + "").equalsIgnoreCase("dw");
            }
        });
        int option = chooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION)
            return chooser.getSelectedFile();
        else
            return null;
    }

    private File askForDirectory(String text) {
        return askForDirectory(text, null);
    }

    private String getExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

        if (i > p)
            extension = fileName.substring(i + 1);

        return extension;
    }
}

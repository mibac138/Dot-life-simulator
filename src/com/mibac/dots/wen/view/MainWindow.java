package com.mibac.dots.wen.view;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

public class MainWindow extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem exportStatisticsItem;
    private JMenuItem closeItem;

    private JMenu worldMenu;
    private JMenuItem createWorldItem;
    private JMenuItem createCreatureItem;
    private JMenuItem createCreaturesItem;

    private JMenu statisticsMenu;
    private JMenuItem showStatisticsItem;

    private JMenu helpMenu;
    private JMenuItem debugOptionsItem;

    private JTabbedPane tabbedPane;

    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setTitle("Experimental version of Dots [formerly Sierra beta]");

        initUI();

        // setResizable(true);
        setVisible(true);
    }

    private void initUI() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        exportStatisticsItem = new JMenuItem("Export Statistics");
        exportStatisticsItem.addActionListener(this);
        closeItem = new JMenuItem("Close");
        closeItem.addActionListener(this);
        fileMenu.add(exportStatisticsItem);
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
        debugOptionsItem = new JMenuItem("Open debug options");
        debugOptionsItem.addActionListener(this);
        helpMenu.add(debugOptionsItem);

        menuBar.add(fileMenu);
        menuBar.add(worldMenu);
        menuBar.add(statisticsMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        tabbedPane = new JTabbedPane();

        add(tabbedPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeItem)
            System.exit(0);
        else if (e.getSource() == createCreatureItem)
            new CreateCreatureView(getWindow(tabbedPane.getSelectedIndex()).getController());
        else if (e.getSource() == debugOptionsItem)
            new DebugOptionsView();
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
        for (int i = 0; i < tabbedPane.getComponentCount(); i++)
            if (tabbedPane.getComponentAt(i) instanceof Window)
                ((Window) tabbedPane.getComponentAt(i)).update(delta);
    }
}

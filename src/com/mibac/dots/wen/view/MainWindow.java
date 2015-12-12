package com.mibac.dots.wen.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.mibac.dots.wen.controller.WorldController;
import com.mibac.dots.wen.creatures.Creature;

public class MainWindow extends JFrame implements ChangeListener, ActionListener {
    private static final long serialVersionUID = 1L;
    private WorldController controller;

    private JSplitPane split;

    private JLabel textLabel;
    private JLabel speedLabel;
    private JLabel maxFoodLabel;
    private JSpinner maxFoodSpinner;
    private JSlider speedSlider;

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

    private Creature creature;
    private JPanel entityInfo;
    private JLabel entityInfoToString;
    private JLabel entityPosition;
    private JLabel entityAge;
    private JLabel entityMaxAge;
    private JLabel entityEnergy;
    private JLabel entityMaxEnergy;
    private JLabel entitySpeed;
    private JLabel entityVisionRange;
    private JLabel entityMatingEnergyNeeded;
    private JLabel entityBreedLength;
    private JLabel entityBreedSpeed;
    private JLabel entityBreedTime;
    private JLabel entityPregnant;
    private JLabel entityAI;
    private JLabel entityBreedCooldown;
    private JLabel entityBreedCooldownTime;
    private JLabel entityBreedFactor;
    private JButton entityKill;
    private JButton entityClone;

    public MainWindow(WorldController controller, WorldView view) {
        this.controller = controller;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setTitle("Experimental version of Dots");

        initUI(view);

        setResizable(true);
        setVisible(true);
    }

    private void initUI(WorldView view) {
        JPanel rightContainer = new JPanel();
        rightContainer.setLayout(new BoxLayout(rightContainer, BoxLayout.PAGE_AXIS));

        textLabel = new JLabel("Creatures");
        textLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        speedLabel = new JLabel("Speed");
        speedLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        speedSlider = new JSlider(0, controller.getMaxSpeedFactor(), controller.getSpeedFactor());
        speedSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        speedSlider.setMajorTickSpacing(5);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setSnapToTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setPaintTicks(true);
        speedSlider.addChangeListener(this);

        JPanel maxFoodContainer = new JPanel();
        maxFoodContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
        maxFoodContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

        maxFoodLabel = new JLabel("Max Food");
        SpinnerModel maxFoodSpinnerModel =
                new SpinnerNumberModel(controller.getMaxFoodAmount(), 0, Integer.MAX_VALUE, 1);
        maxFoodSpinner = new JSpinner(maxFoodSpinnerModel);
        maxFoodSpinner.setEditor(new JSpinner.NumberEditor(maxFoodSpinner, "#"));
        maxFoodSpinner.addChangeListener(this);

        maxFoodContainer.add(maxFoodLabel);
        maxFoodContainer.add(maxFoodSpinner);

        entityInfo = new JPanel();
        entityInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        entityInfoToString = new JLabel();
        entityInfoToString.setBounds(5, 5, 250, 15);
        entityPosition = new JLabel();
        entityPosition.setBounds(5, 25, 250, 15);
        entityAge = new JLabel();
        entityAge.setBounds(5, 45, 250, 15);
        entityMaxAge = new JLabel();
        entityMaxAge.setBounds(5, 65, 250, 15);
        entityEnergy = new JLabel();
        entityEnergy.setBounds(5, 85, 250, 15);
        entityMaxEnergy = new JLabel();
        entityMaxEnergy.setBounds(5, 105, 250, 15);
        entitySpeed = new JLabel();
        entitySpeed.setBounds(5, 125, 250, 15);
        entityVisionRange = new JLabel();
        entityVisionRange.setBounds(5, 145, 250, 15);
        entityMatingEnergyNeeded = new JLabel();
        entityMatingEnergyNeeded.setBounds(5, 165, 250, 15);
        entityBreedLength = new JLabel();
        entityBreedLength.setBounds(5, 185, 250, 15);
        entityBreedSpeed = new JLabel();
        entityBreedSpeed.setBounds(5, 205, 250, 15);
        entityBreedTime = new JLabel();
        entityBreedTime.setBounds(5, 225, 250, 15);
        entityPregnant = new JLabel();
        entityPregnant.setBounds(5, 245, 250, 15);
        entityAI = new JLabel();
        entityAI.setBounds(5, 265, 250, 15);
        entityBreedCooldown = new JLabel();
        entityBreedCooldown.setBounds(265, 5, 250, 15);
        entityBreedCooldownTime = new JLabel();
        entityBreedCooldownTime.setBounds(265, 25, 250, 15);
        entityBreedFactor = new JLabel();
        entityBreedFactor.setBounds(265, 45, 250, 15);
        entityInfo.setLayout(null);

        entityKill = new JButton("Kill entity");
        entityKill.setBounds(265, 205, 100, 15);
        entityKill.addActionListener(this);
        entityClone = new JButton("Clone entity");
        entityClone.setBounds(265, 225, 100, 15);
        entityClone.addActionListener(this);

        entityInfo.add(entityInfoToString);
        entityInfo.add(entityPosition);
        entityInfo.add(entityAge);
        entityInfo.add(entityMaxAge);
        entityInfo.add(entityEnergy);
        entityInfo.add(entityMaxEnergy);
        entityInfo.add(entitySpeed);
        entityInfo.add(entityVisionRange);
        entityInfo.add(entityMatingEnergyNeeded);
        entityInfo.add(entityBreedLength);
        entityInfo.add(entityBreedSpeed);
        entityInfo.add(entityBreedTime);
        entityInfo.add(entityPregnant);
        entityInfo.add(entityAI);
        entityInfo.add(entityBreedCooldown);
        entityInfo.add(entityBreedCooldownTime);
        entityInfo.add(entityBreedFactor);
        entityInfo.add(entityKill);
        entityInfo.add(entityClone);

        rightContainer.add(textLabel);
        rightContainer.add(Box.createVerticalStrut(10));
        rightContainer.add(speedLabel);
        rightContainer.add(speedSlider);
        rightContainer.add(maxFoodContainer);
        rightContainer.add(entityInfo);
        entityInfo.setFocusTraversalPolicy(new FocusTraversalOnArray(
                new Component[] {entityInfoToString, entityPosition, entityAge, entityMaxAge,
                        entityEnergy, entityMaxEnergy, entitySpeed, entityVisionRange}));

        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, view, rightContainer);
        split.setResizeWeight(0.8);

        getContentPane().add(split);

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
    }

    public JSlider getSpeedSlider() {
        return speedSlider;
    }

    public void setSpeedSlider(JSlider speedSlider) {
        this.speedSlider = speedSlider;
    }

    public void setViewInputFocus() {
        controller.setViewInputFocus();
    }

    public void displayEntity(Creature c) {
        if (c == null) {
            creature = null;
            entityInfo.setVisible(false);
            return;
        }

        creature = c;
        entityInfoToString.setText(c.toString());
        entityPosition.setText("Position: " + String.format("%.2f", c.getPosition().getX()) + ", "
                + String.format("%.2f", c.getPosition().getY()));
        entityAge.setText("Age: " + String.format("%.2f", c.getAge()));
        entityMaxAge.setText("Max age: " + String.format("%.2f", c.getMaxAge()));
        entityEnergy.setText("Energy: " + String.format("%.2f", c.getEnergy()));
        entityMaxEnergy.setText("Max energy: " + String.format("%.2f", c.getMaxEnergy()));
        entitySpeed.setText("Speed: " + String.format("%.2f", c.getSpeed()));
        entityVisionRange.setText("Vision range: " + String.format("%.2f", c.getVisionRange()));
        entityMatingEnergyNeeded.setText(
                "Mating energy needed: " + String.format("%.2f", c.getMatingEnergyNeeded()));
        entityBreedLength.setText("Breed length: " + String.format("%.2f", c.getBreedLength()));
        entityBreedSpeed.setText("Breed speed: " + String.format("%.2f", c.getBreedSpeed()));
        entityBreedTime.setText("Breed time: " + String.format("%.2f", c.getBreedTime()));
        entityPregnant.setText("Pregnant: " + c.isPregnant());
        entityAI.setText("AI: " + c.getAI().getClass().getSimpleName());
        entityBreedCooldown
                .setText("Breed cooldown: " + String.format("%.2f", c.getBreedCooldown()));
        entityBreedCooldownTime
                .setText("Breed cooldown time: " + String.format("%.2f", c.getBreedCooldownTime()));
        entityBreedFactor.setText("Breed factor: " + String.format("%.2f", c.getBreedFactor()));
        entityInfo.setVisible(true);
    }

    public void update(double delta) {
        textLabel.setText("Creatures: " + controller.getCreatures().size());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeItem)
            System.exit(0);
        else if (e.getSource() == createCreatureItem)
            new CreateCreatureView(controller);
        else if (e.getSource() == debugOptionsItem)
            new DebugOptionsView();
        else if (e.getSource() == entityKill) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to kill this creature?", "Confirmation",
                    JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION)
                controller.removeEntity(creature);
        } else if (e.getSource() == entityClone)
            new CreateCreatureView(creature, controller);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == speedSlider)
            controller.setSpeedFactor(speedSlider.getValue());
        else if (e.getSource() == maxFoodSpinner)
            controller.setMaxFoodAmount((int) maxFoodSpinner.getValue());
    }
}

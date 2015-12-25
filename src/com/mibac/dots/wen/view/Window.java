package com.mibac.dots.wen.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import com.mibac.dots.wen.controller.WorldController;
import com.mibac.dots.wen.creatures.Creature;

public class Window extends JPanel implements ChangeListener, ActionListener {
    private static final long serialVersionUID = 1L;
    private WorldController controller;

    private JSplitPane split;

    private JLabel textLabel;
    private JLabel speedLabel;
    private JLabel maxFoodLabel;
    private JSpinner maxFoodSpinner;
    private JSlider speedSlider;

    private Creature creature;
    private JPanel entityPanel;
    private JTable entityInfoTable;
    private JButton entityKill;
    private JButton entityClone;

    public Window(WorldController controller) {
        this.controller = controller;
        this.controller.setWindow(this);

        initUI(controller.getView());
    }

    private void initUI(WorldView view) {
        JPanel rightContainer = new JPanel();
        rightContainer.setLayout(new BoxLayout(rightContainer, BoxLayout.PAGE_AXIS));

        JPanel worldData = new JPanel();
        // worldData.setAlignmentX(Component.RIGHT_ALIGNMENT);
        worldData.setLayout(new BoxLayout(worldData, BoxLayout.PAGE_AXIS));
        worldData.setBackground(Color.YELLOW);

        textLabel = new JLabel("Creatures");
        textLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        speedLabel = new JLabel("Speed");
        speedLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        speedSlider = new JSlider(0, controller.getMaxSpeedFactor(), controller.getSpeed());
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

        worldData.add(textLabel);
        worldData.add(Box.createVerticalStrut(10));
        worldData.add(speedLabel);
        worldData.add(speedSlider);
        worldData.add(maxFoodContainer);

        entityPanel = new JPanel(new BorderLayout());

        String[] rows = {"variable", "value"};
        Object[][] columns = {{"AI", null}, {"coordinates", null}, {"color", null},
                {"target color", null}, {"color animation data", null}, {"gender", null},
                {"age", null}, {"max age", null}, {"energy", null}, {"max energy", null},
                {"speed", null}, {"vision range", null}, {"mating energy needed", null},
                {"breed length", null}, {"breed speed", null}, {"breed time", null},
                {"breed cooldown", null}, {"breed cooldown time", null}, {"breed factor", null},
                {"pregnant", null}};
        entityInfoTable = new JTable();
        entityInfoTable.setModel(new DefaultTableModel(columns, rows) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;

                return super.isCellEditable(row, column);
            }

            @Override
            public void setValueAt(Object o, int x, int y) {
                if (o instanceof Number) {
                    super.setValueAt(String.format("%.2f", o), x, y);
                    return;
                }
                super.setValueAt(o, x, y);
            }
        });

        entityKill = new JButton("Kill entity");
        entityKill.addActionListener(this);
        entityClone = new JButton("Clone entity");
        entityClone.addActionListener(this);

        JPanel entityBtnsPanel = new JPanel(new BorderLayout());
        entityBtnsPanel.add(entityKill, BorderLayout.WEST);
        entityBtnsPanel.add(entityClone, BorderLayout.EAST);

        entityPanel.add(entityInfoTable, BorderLayout.CENTER);
        entityPanel.add(entityBtnsPanel, BorderLayout.SOUTH);
        entityPanel.setVisible(false);

        // rightContainer.add(textLabel);
        // rightContainer.add(Box.createVerticalStrut(10));
        // rightContainer.add(speedLabel);
        // rightContainer.add(speedSlider);
        rightContainer.add(Box.createVerticalBox());
        rightContainer.add(worldData);
        // rightContainer.add(maxFoodContainer);
        rightContainer.add(entityPanel);

        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, view, rightContainer);
        split.setResizeWeight(0.8);

        setLayout(new BorderLayout());
        add(split, BorderLayout.CENTER);
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

    public void saveWorld(File directory) {
        controller.saveWorld(directory);
    }

    public void displayEntity(Creature c) {
        if (c == null) {
            creature = null;
            entityPanel.setVisible(false);
            return;
        }

        creature = c;
        int column = 1;
        int row = 0; // to avoid further mess with changing all rows when a variable is added (ex)
                     // in
                     // the 1st place
        entityInfoTable.setValueAt(c.getAI().getClass().getSimpleName(), row++, column);
        entityInfoTable.setValueAt("x: " + String.format("%.2f", c.getPosition().getX()) + " y: "
                + String.format("%.2f", c.getPosition().getY()), row++, column);
        entityInfoTable.setValueAt(c.getColor(), row++, column);
        entityInfoTable.setValueAt(c.getTargetColor(), row++, column);
        entityInfoTable.setValueAt(c.getColorAnimation(), row++, column);
        entityInfoTable.setValueAt(c.getGender(), row++, column);
        entityInfoTable.setValueAt(c.getAge(), row++, column);
        entityInfoTable.setValueAt(c.getMaxAge(), row++, column);
        entityInfoTable.setValueAt(c.getEnergy(), row++, column);
        entityInfoTable.setValueAt(c.getMaxEnergy(), row++, column);
        entityInfoTable.setValueAt(c.getSpeed(), row++, column);
        entityInfoTable.setValueAt(c.getVisionRange(), row++, column);
        entityInfoTable.setValueAt(c.getMatingEnergyNeeded(), row++, column);
        entityInfoTable.setValueAt(c.getBreedLength(), row++, column);
        entityInfoTable.setValueAt(c.getBreedSpeed(), row++, column);
        entityInfoTable.setValueAt(c.getBreedTime(), row++, column);
        entityInfoTable.setValueAt(c.getBreedCooldown(), row++, column);
        entityInfoTable.setValueAt(c.getBreedCooldownTime(), row++, column);
        entityInfoTable.setValueAt(c.getBreedFactor(), row++, column);
        entityInfoTable.setValueAt(c.isPregnant(), row++, column);
        entityPanel.setVisible(true);
    }

    public void update(double delta) {
        textLabel.setText("Creatures: " + controller.getCreatures().size());
        speedSlider.setMaximum(controller.getMaxSpeedFactor());
        controller.update(delta);
    }

    public void render(boolean drawWorld) {
        controller.render(drawWorld);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == entityKill) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to kill this creature?", "Confirmation",
                    JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION)
                controller.removeEntity(creature);
        } else if (e.getSource() == entityClone)
            new CreateCreatureView(controller, creature);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == speedSlider)
            controller.setSpeedFactor(speedSlider.getValue());
        else if (e.getSource() == maxFoodSpinner)
            controller.setMaxFoodAmount((int) maxFoodSpinner.getValue());
    }

    WorldController getController() {
        return controller;
    }
}

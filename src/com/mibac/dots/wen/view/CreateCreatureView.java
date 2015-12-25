package com.mibac.dots.wen.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D.Double;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mibac.dots.wen.ai.BetterAI;
import com.mibac.dots.wen.controller.WorldController;
import com.mibac.dots.wen.creatures.Creature;
import com.mibac.dots.wen.creatures.Creature.Gender;

public class CreateCreatureView extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private WorldController controller;

    private JPanel backPanel;
    private JTextField maxEnergyInput;
    private JTextField maxAgeInput;
    private JTextField positionXInput;
    private JTextField positionYInput;
    private JTextField speedInput;
    private JTextField visionRangeInput;
    private JTextField matingEnergyNeededInput;
    private JTextField breedLengthInput;
    private JTextField breedProgressSpeedInput;
    private JTextField breedCooldownInput;
    private JTextField breedFactorInput;
    private JComboBox<String> genderComboBox;
    private JButton createButton;

    public CreateCreatureView(WorldController controller) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setTitle("Create Creature");

        this.controller = controller;

        initUI();

        setResizable(true);
        setVisible(true);
    }

    public CreateCreatureView(WorldController controller, Creature creature) {
        this(controller);

        maxEnergyInput.setText(creature.getMaxEnergy() + "");
        maxAgeInput.setText(creature.getMaxAge() + "");
        positionXInput.setText(creature.getPosition().getX() + "");
        positionYInput.setText(creature.getPosition().getY() + "");
        speedInput.setText(creature.getSpeed() + "");
        visionRangeInput.setText(creature.getVisionRange() + "");
        matingEnergyNeededInput.setText(creature.getMatingEnergyNeeded() + "");
        breedLengthInput.setText(creature.getBreedLength() + "");
        breedProgressSpeedInput.setText(creature.getBreedSpeed() + "");
        breedCooldownInput.setText(creature.getBreedCooldown() + "");
        breedFactorInput.setText(creature.getBreedFactor() + "");
        genderComboBox.setSelectedIndex(creature.getGender() == Gender.MALE ? 0 : 1);
    }

    private void initUI() {
        backPanel = new JPanel();
        backPanel.setLayout(new GridLayout(0, 1));

        JPanel[] panels = new JPanel[11];
        for (int k = 0; k < panels.length; k++)
            panels[k] = new JPanel(new FlowLayout(FlowLayout.LEFT));

        Dimension pSize = new Dimension(50, 20);

        maxEnergyInput = new JTextField();
        maxEnergyInput.setPreferredSize(pSize);
        maxAgeInput = new JTextField();
        maxAgeInput.setPreferredSize(pSize);
        positionXInput = new JTextField();
        positionXInput.setPreferredSize(pSize);
        positionYInput = new JTextField();
        positionYInput.setPreferredSize(pSize);
        speedInput = new JTextField();
        speedInput.setPreferredSize(pSize);
        visionRangeInput = new JTextField();
        visionRangeInput.setPreferredSize(pSize);
        String[] values = {"Male", "Female"};
        genderComboBox = new JComboBox<String>(values);
        matingEnergyNeededInput = new JTextField();
        matingEnergyNeededInput.setPreferredSize(pSize);
        breedLengthInput = new JTextField();
        breedLengthInput.setPreferredSize(pSize);
        breedProgressSpeedInput = new JTextField();
        breedProgressSpeedInput.setPreferredSize(pSize);
        breedCooldownInput = new JTextField();
        breedCooldownInput.setPreferredSize(pSize);
        breedFactorInput = new JTextField();
        breedFactorInput.setPreferredSize(pSize);

        panels[0].add(new JLabel("Max Energy:"));
        panels[0].add(maxEnergyInput);

        panels[1].add(new JLabel("Max Life:"));
        panels[1].add(maxAgeInput);

        panels[2].add(new JLabel("Position:"));
        panels[2].add(positionXInput);
        panels[2].add(positionYInput);

        panels[3].add(new JLabel("Speed:"));
        panels[3].add(speedInput);

        panels[4].add(new JLabel("Vision Range:"));
        panels[4].add(visionRangeInput);

        panels[5].add(new JLabel("Sex:"));
        panels[5].add(genderComboBox);

        panels[6].add(new JLabel("Mating Energy Needed:"));
        panels[6].add(matingEnergyNeededInput);

        panels[7].add(new JLabel("Breed Length:"));
        panels[7].add(breedLengthInput);

        panels[8].add(new JLabel("Breed Progress Speed:"));
        panels[8].add(breedProgressSpeedInput);

        panels[9].add(new JLabel("Breed cooldown:"));
        panels[9].add(breedCooldownInput);

        panels[10].add(new JLabel("Breed factor:"));
        panels[10].add(breedFactorInput);

        for (JPanel p : panels)
            backPanel.add(p);

        createButton = new JButton("Create Creature");
        createButton.addActionListener(this);

        backPanel.add(createButton);

        setContentPane(backPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton)
            createCreature();
    }

    private void createCreature() {
        double maxEnergy;
        double maxAge;
        double positionX;
        double positionY;
        double speed;
        double visionRange;
        double matingEnergyNeeded;
        double breedLength;
        double breedProgressSpeed;
        double breedCooldown;
        double breedFactor;

        String gender;


        try {
            maxEnergy = java.lang.Double.parseDouble(maxEnergyInput.getText());
            maxAge = java.lang.Double.parseDouble(maxAgeInput.getText());
            positionX = java.lang.Double.parseDouble(positionXInput.getText());
            positionY = java.lang.Double.parseDouble(positionYInput.getText());
            speed = java.lang.Double.parseDouble(speedInput.getText());
            visionRange = java.lang.Double.parseDouble(visionRangeInput.getText());
            gender = (String) genderComboBox.getSelectedItem();
            matingEnergyNeeded = java.lang.Double.parseDouble(matingEnergyNeededInput.getText());
            breedLength = java.lang.Double.parseDouble(breedLengthInput.getText());
            breedProgressSpeed = java.lang.Double.parseDouble(breedProgressSpeedInput.getText());
            breedCooldown = java.lang.Double.parseDouble(breedCooldownInput.getText());
            breedFactor = java.lang.Double.parseDouble(breedFactorInput.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input must be a number.", "Wrong input",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            assertTrue("Max Energy", maxEnergy >= 0);
            assertTrue("Max Life", maxAge >= 0);
            assertTrue("Position X", positionX >= 0);
            assertTrue("Position Y", positionY >= 0);
            assertTrue("Speed", speed >= 0);
            assertTrue("Vision Range", visionRange >= 0);
            assertTrue("Mating Energy Needed", matingEnergyNeeded >= 0);
            assertTrue("Breed Length", breedLength >= 0);
            assertTrue("Breed Progress Speed", breedProgressSpeed >= 0);
            assertTrue("Breed Cooldown", breedCooldown >= 0);
            assertTrue("Breed Factor", breedFactor >= 0);

            assertTrue("Position X", positionX <= controller.getWorldWidth());
            assertTrue("Position Y", positionY <= controller.getWorldHeight());
        } catch (AssertionError e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Wrong input",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Gender gnd;
        if (gender.equals("Male"))
            gnd = Gender.MALE;
        else
            gnd = Gender.FEMALE;

        controller.addEntity(new Creature(new Double(positionX, positionY), new BetterAI(), gnd, 0,
                maxAge, maxEnergy, maxEnergy, speed, visionRange, matingEnergyNeeded, breedLength,
                breedProgressSpeed, breedCooldown, breedFactor));
        dispose();
    }

    private void assertTrue(String message, boolean bool) throws AssertionError {
        if (!bool)
            throw new AssertionError(message);
    }
}

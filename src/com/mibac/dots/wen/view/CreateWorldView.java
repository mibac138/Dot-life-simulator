package com.mibac.dots.wen.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mibac.dots.wen.controller.WorldController;
import com.mibac.dots.wen.creatures.WorldModel;

public class CreateWorldView extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private MainWindow main;
    private JTextField widthInput;
    private JTextField heightInput;
    private JTextField foodRateInput;
    private JTextField goodMutationChanceInput;
    private JTextField mutationRateInput;
    private JButton createButton;


    public CreateWorldView(MainWindow main) {
        this.main = main;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(250, 200);
        setLocationRelativeTo(null);
        setTitle("Create World");

        initUI();

        setResizable(true);
        setVisible(true);
    }

    private void initUI() {
        JPanel backPanel = new JPanel();
        backPanel.setLayout(new GridLayout(0, 1));

        JPanel[] panels = new JPanel[5];

        for (int i = 0; i < panels.length; i++) {
            panels[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
            backPanel.add(panels[i]);
        }

        JLabel widthLabel = new JLabel("Width:");
        widthInput = new JTextField();
        widthInput.setPreferredSize(new Dimension(50, 20));
        panels[0].add(widthLabel);
        panels[0].add(widthInput);


        JLabel heightLabel = new JLabel("Height:");
        heightInput = new JTextField();
        heightInput.setPreferredSize(new Dimension(50, 20));
        panels[1].add(heightLabel);
        panels[1].add(heightInput);

        JLabel foodRateLabel = new JLabel("Food Rate (0 - 100):");
        foodRateInput = new JTextField();
        foodRateInput.setPreferredSize(new Dimension(50, 20));
        panels[2].add(foodRateLabel);
        panels[2].add(foodRateInput);

        JLabel goodMutationChanceLabel = new JLabel("Good mutation chance (0 - 100):");
        goodMutationChanceInput = new JTextField();
        goodMutationChanceInput.setPreferredSize(new Dimension(50, 20));
        panels[3].add(goodMutationChanceLabel);
        panels[3].add(goodMutationChanceInput);

        JLabel mutationRateLabel = new JLabel("Mutation rate:");
        mutationRateInput = new JTextField();
        mutationRateInput.setPreferredSize(new Dimension(50, 20));
        panels[4].add(mutationRateLabel);
        panels[4].add(mutationRateInput);

        createButton = new JButton("Create World");
        createButton.addActionListener(this);

        backPanel.add(createButton);

        setContentPane(backPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() != createButton)
            return;

        int width = 0, height = 0;
        short foodRate = 0;
        double goodMutationChance = 0, mutationRate = 0;

        try {
            width = Integer.parseInt(widthInput.getText());
            height = Integer.parseInt(heightInput.getText());
            foodRate = Short.parseShort(foodRateInput.getText());
            goodMutationChance = Double.parseDouble(goodMutationChanceInput.getText());
            mutationRate = Double.parseDouble(mutationRateInput.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Exception occured"); // TODO make more messages
                                                                      // (**readable**)
        }

        WorldModel model = new WorldModel.Builder(width, height).setFoodCreationRatio(foodRate)
                .setGoodMutationChance(goodMutationChance).setMutationRate(mutationRate).setSpeed(1)
                .build();

        main.addWindow(new Window(new WorldController(model)));

        dispose();
    }
}

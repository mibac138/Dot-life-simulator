package com.mibac.dots.wen.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateWorldView extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField widthInput;
    private JComponent heightInput;
    private JTextField foodRateInput;
    private JTextField goodMutationChange;
    private JTextField mutationRate;
    private JButton createButton;


    public CreateWorldView() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(220, 150);
        setLocationRelativeTo(null);
        setTitle("Create World");

        initUI();

        setResizable(true);
        setVisible(true);
    }

    private void initUI() {
        JPanel backPanel = new JPanel();
        backPanel.setLayout(new GridLayout(0, 1));

        JPanel widthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel widthLabel = new JLabel("Width:");
        widthInput = new JTextField();
        widthInput.setPreferredSize(new Dimension(50, 20));
        widthPanel.add(widthLabel);
        widthPanel.add(widthInput);


        JPanel heightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel heightLabel = new JLabel("Height:");
        heightInput = new JTextField();
        heightInput.setPreferredSize(new Dimension(50, 20));
        heightPanel.add(heightLabel);
        heightPanel.add(heightInput);

        JPanel foodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel foodRateLabel = new JLabel("Food Rate: (0-100)");
        foodRateInput = new JTextField();
        foodRateInput.setPreferredSize(new Dimension(50, 20));
        foodPanel.add(foodRateLabel);
        foodPanel.add(foodRateInput);

        backPanel.add(widthPanel);
        backPanel.add(heightPanel);
        backPanel.add(foodPanel);

        createButton = new JButton("Create World");
        createButton.addActionListener(this);

        backPanel.add(createButton);

        setContentPane(backPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

}

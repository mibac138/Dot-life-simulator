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

import com.mibac.dots.wen.Creatures;
import com.mibac.dots.wen.controller.WorldController;
import com.mibac.dots.wen.util.Debug;

public class OptionsView extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JTextField maxSpeedFactor;

    private JButton saveButton;

    public OptionsView() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 100);
        setLocationRelativeTo(null);
        setTitle("Options");

        initUI();

        setResizable(true);
        setVisible(true);
    }

    private void initUI() {
        setLayout(new GridLayout(0, 1));
        JPanel[] panels = new JPanel[2];

        for (int i = 0; i < panels.length; i++) {
            panels[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
            add(panels[i]);
        }

        maxSpeedFactor = new JTextField(WorldController.MAX_SPEED + "");
        maxSpeedFactor.setPreferredSize(new Dimension(50, 20));

        panels[0].add(new JLabel("Max speed factor"));
        panels[0].add(maxSpeedFactor);

        saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(60, 20));
        saveButton.addActionListener(this);

        panels[1].add(new JLabel("Confirm"));
        panels[1].add(saveButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            int mSpeed = WorldController.MAX_SPEED;
            try {
                mSpeed = Integer.parseInt(maxSpeedFactor.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        Debug.DEBUG.isEnabled()
                                ? "Max speed factor must be a number. " + ex.getMessage()
                                : "Max speed factor must be a number.");
                return;
            }

            if (mSpeed < 0) {
                JOptionPane.showMessageDialog(this, "Max speed factor must be bigger than 0.");
                return;
            }

            WorldController.MAX_SPEED = mSpeed;
            Creatures.getConfig().set("util.maxSpeedFactor", mSpeed);

            dispose();
        }
    }
}

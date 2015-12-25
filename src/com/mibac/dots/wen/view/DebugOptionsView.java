package com.mibac.dots.wen.view;

import static com.mibac.dots.wen.util.Debug.CMD;
import static com.mibac.dots.wen.util.Debug.DEBUG;
import static com.mibac.dots.wen.util.Debug.DRAW;
import static com.mibac.dots.wen.util.Debug.DRAW_BORDER;
import static com.mibac.dots.wen.util.Debug.DRAW_PATH;
import static com.mibac.dots.wen.util.Debug.DRAW_VISION;
import static com.mibac.dots.wen.util.Debug.PRINT;
import static com.mibac.dots.wen.util.Debug.PRINT_AI;
import static com.mibac.dots.wen.util.Debug.PRINT_BREED;
import static com.mibac.dots.wen.util.Debug.PRINT_DEATH;
import static com.mibac.dots.wen.util.Debug.PRINT_ENERGY;
import static com.mibac.dots.wen.util.Debug.PRINT_INPUT;
import static com.mibac.dots.wen.util.Debug.PRINT_MOVE;
import static com.mibac.dots.wen.util.Debug.PRINT_OTHER;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;

public class DebugOptionsView extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private static final int w = 90;
    private static final int h = 20;

    private JCheckBox debug;

    private JCheckBox draw;
    private JCheckBox draw_path;
    private JCheckBox draw_vision;
    private JCheckBox draw_border;

    private JCheckBox print;
    private JCheckBox print_energy;
    private JCheckBox print_death;
    private JCheckBox print_ai;
    private JCheckBox print_breed;
    private JCheckBox print_move;
    private JCheckBox print_input;
    private JCheckBox print_other;

    private JCheckBox cmds;

    public DebugOptionsView() {
        debug = new JCheckBox("Debug");
        debug.setBounds(5, 5, w, h);
        debug.setSelected(DEBUG.isThisEnabled());
        debug.addActionListener(this);

        draw = new JCheckBox("Draw");
        draw.setBounds(10, 25, w, h);
        draw.setSelected(DRAW.isThisEnabled());
        draw.addActionListener(this);
        draw_path = new JCheckBox("Draw path");
        draw_path.setBounds(15, 45, w, h);
        draw_path.addActionListener(this);
        draw_path.setSelected(DRAW_PATH.isThisEnabled());
        draw_vision = new JCheckBox("Draw vision");
        draw_vision.setBounds(15, 65, w, h);
        draw_vision.addActionListener(this);
        draw_vision.setSelected(DRAW_VISION.isThisEnabled());
        draw_border = new JCheckBox("Draw border");
        draw_border.setBounds(15, 85, w, h);
        draw_border.addActionListener(this);
        draw_border.setSelected(DRAW_BORDER.isThisEnabled());

        print = new JCheckBox("Print");
        print.setBounds(10, 105, w, h);
        print.setSelected(PRINT.isThisEnabled());
        print.addActionListener(this);
        print_energy = new JCheckBox("Print energy");
        print_energy.setBounds(15, 125, w, h);
        print_energy.setSelected(PRINT_ENERGY.isThisEnabled());
        print_energy.addActionListener(this);
        print_death = new JCheckBox("Print deaths");
        print_death.setBounds(15, 145, w, h);
        print_death.setSelected(PRINT_DEATH.isThisEnabled());
        print_energy.addActionListener(this);
        print_ai = new JCheckBox("Print ai");
        print_ai.setBounds(15, 165, w, h);
        print_ai.setSelected(PRINT_AI.isThisEnabled());
        print_ai.addActionListener(this);
        print_breed = new JCheckBox("Print breed");
        print_breed.setBounds(15, 185, w, h);
        print_breed.setSelected(PRINT_BREED.isThisEnabled());
        print_breed.addActionListener(this);
        print_move = new JCheckBox("Print move");
        print_move.setBounds(15, 205, w, h);
        print_move.setSelected(PRINT_MOVE.isThisEnabled());
        print_move.addActionListener(this);
        print_input = new JCheckBox("Print input");
        print_input.setBounds(15, 225, w, h);
        print_input.setSelected(PRINT_INPUT.isThisEnabled());
        print_input.addActionListener(this);
        print_other = new JCheckBox("Print other");
        print_other.setBounds(15, 245, w, h);
        print_other.setSelected(PRINT_OTHER.isThisEnabled());
        print_other.addActionListener(this);

        cmds = new JCheckBox("Commands");
        cmds.setBounds(10, 265, w, h);
        cmds.setSelected(CMD.isEnabled());
        cmds.addActionListener(this);
        getContentPane().setLayout(null);

        getContentPane().add(debug);

        getContentPane().add(draw);
        getContentPane().add(draw_path);
        getContentPane().add(draw_vision);
        getContentPane().add(draw_border);

        getContentPane().add(print);
        getContentPane().add(print_energy);
        getContentPane().add(print_death);
        getContentPane().add(print_ai);
        getContentPane().add(print_breed);
        getContentPane().add(print_move);
        getContentPane().add(print_input);
        getContentPane().add(print_other);

        getContentPane().add(cmds);

        setSize(200, 325);
        setTitle("Debug options");
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == debug)
            DEBUG.setEnabled(debug.isSelected());
        else if (e.getSource() == draw)
            DRAW.setEnabled(draw.isSelected());
        else if (e.getSource() == draw_path)
            DRAW_PATH.setEnabled(draw_path.isSelected());
        else if (e.getSource() == draw_vision)
            DRAW_VISION.setEnabled(draw_vision.isSelected());
        else if (e.getSource() == draw_border)
            DRAW_BORDER.setEnabled(draw_border.isSelected());
        else if (e.getSource() == print)
            PRINT.setEnabled(print.isSelected());
        else if (e.getSource() == print_energy)
            PRINT_ENERGY.setEnabled(print_energy.isSelected());
        else if (e.getSource() == print_death)
            PRINT_DEATH.setEnabled(print_death.isSelected());
        else if (e.getSource() == print_ai)
            PRINT_AI.setEnabled(print_ai.isSelected());
        else if (e.getSource() == print_breed)
            PRINT_BREED.setEnabled(print_breed.isSelected());
        else if (e.getSource() == print_move)
            PRINT_MOVE.setEnabled(print_move.isSelected());
        else if (e.getSource() == print_input)
            PRINT_INPUT.setEnabled(print_input.isSelected());
        else if (e.getSource() == print_other)
            PRINT_OTHER.setEnabled(print_other.isSelected());
        else if (e.getSource() == cmds)
            CMD.setEnabled(cmds.isSelected());
    }
}

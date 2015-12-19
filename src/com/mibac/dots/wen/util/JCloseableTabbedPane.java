package com.mibac.dots.wen.util;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.metal.MetalIconFactory;

public class JCloseableTabbedPane extends JTabbedPane {
    private static final long serialVersionUID = 1L;

    @Override
    public void addTab(String title, Icon icon, Component component, String tip) {
        super.addTab(title, icon, component, tip);
        setTabComponentAt(getTabCount() - 1, new CloseButtonTab(component, title, icon));
    }

    @Override
    public void addTab(String title, Icon icon, Component component) {
        addTab(title, icon, component, null);
    }

    @Override
    public void addTab(String title, Component component) {
        addTab(title, null, component);
    }

    public class CloseButtonTab extends JPanel {
        private static final long serialVersionUID = 1L;

        public CloseButtonTab(Component tab, String title, Icon icon) {
            setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
            JLabel label = new JLabel(title);
            label.setIcon(icon);
            add(label);

            // TODO some nicer icon
            JButton button = new JButton(MetalIconFactory.getInternalFrameCloseIcon(14));
            button.setMargin(new Insets(0, 0, 0, 0));
            button.addMouseListener(new CloseListener(tab));
            add(button);
        }
    }

    public class CloseListener implements MouseListener {
        private Component tab;

        public CloseListener(Component tab) {
            this.tab = tab;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() instanceof JButton) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to close this tab? Any unsaved progress will be deleted! You can save the tab under File menu");
                if (confirm != JOptionPane.YES_OPTION)
                    return;

                JButton clickedButton = (JButton) e.getSource();
                JTabbedPane tabbedPane =
                        (JTabbedPane) clickedButton.getParent().getParent().getParent();
                tabbedPane.remove(tab);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
}

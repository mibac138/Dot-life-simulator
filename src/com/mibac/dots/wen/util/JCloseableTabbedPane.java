package com.mibac.dots.wen.util;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import com.mibac.dots.wen.view.MainWindow;

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

    public class CloseButtonTab extends JPanel implements MouseListener {
        private static final long serialVersionUID = 1L;
        private Component tab;

        public CloseButtonTab(Component tab, String title, Icon icon) {
            setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
            JLabel label = new JLabel(title);
            label.setIcon(icon);
            add(label);

            this.tab = tab;

            // TODO some nicer icon
            JButton button = new JButton(UIManager.getIcon("InternalFrame.closeIcon"));// MetalIconFactory.getInternalFrameCloseIcon(14));
            button.setMargin(new Insets(-2, 0, -2, -5));
            button.addMouseListener(this);
            add(button);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() instanceof JButton)
                if (MainWindow.confirmQuit("tab")) {
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

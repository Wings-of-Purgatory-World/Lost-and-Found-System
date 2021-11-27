package com.客户端;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePage extends JPanel {
    JPanel jPanel;
    JP1 jpx;
    public HomePage(JPanel jPanel, JP1 jpx) {
        this.jPanel = jPanel;
        this.jpx = jpx;

        setLayout(new BorderLayout());
        JPanel jPanel2 = new JPanel(null);
        jPanel2.setPreferredSize(new Dimension(540, 400));
        jPanel2.setSize(540, 400);

        ComponentGroup1 componentGroup1 = new ComponentGroup1(jPanel,jPanel2,jpx);
        jPanel2.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                componentGroup1.draw();
            }
        });
        JScrollPane jScrollPane = new JScrollPane(jPanel2);
        JScrollBar Bar = null;
        Bar = jScrollPane.getVerticalScrollBar();
        Bar.setUnitIncrement(40);
        add(jScrollPane);
    }
}
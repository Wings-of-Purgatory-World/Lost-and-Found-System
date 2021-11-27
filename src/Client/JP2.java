package com.客户端;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class JP2 extends JPanel{
    public static final int LOST = 11;
    public static final int FOUND = 22;

    private int state;
    private JPanel jPanel;
    private JP1 jpx;

    public JP2(JPanel jPanel, JP1 jpx, int state){
        this.jPanel = jPanel;
        this.jpx = jpx;
        this.state = state;
        this.jPanel = jPanel;
        this.jpx = jpx;

        setLayout(new BorderLayout());
        JPanel jPanel2 = new JPanel(null);
        jPanel2.setPreferredSize(new Dimension(540, 400));
        jPanel2.setSize(540, 400);

        ComponentGroup componentGroup = new ComponentGroup(jPanel,jPanel2,jpx,state);
        jPanel2.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                componentGroup.draw();
            }
        });

        JScrollPane jScrollPane = new JScrollPane(jPanel2);
        JScrollBar Bar = null;
        Bar = jScrollPane.getVerticalScrollBar();
        Bar.setUnitIncrement(40);
        add(jScrollPane);
    }

//    public static void main(String[] args){
//        JFrame jFrame = new JFrame("dfs");
//        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//
//        jFrame.setSize(540,400);
//
//        JP2 jp = new JP2(null,null,JP2.LOST);
//
//        JPanel jPanel = new JPanel(new CardLayout());
//        jPanel.add(jp);
//        jFrame.add(jPanel);
//        jFrame.setVisible(true);
//    }

}


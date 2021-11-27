package com.客户端;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class JP1 extends JPanel {
//    private JPanel jPanel;
    private ArrayList<JPanel> jPanels = new ArrayList<>();
    private int index = 0;

    public JP1(){
        setLayout(new BorderLayout());
        JPanel jPanel1 = new JPanel(null);
        jPanel1.setBackground(Color.WHITE);
        jPanel1.setPreferredSize(new Dimension(0, 35));
        add(jPanel1, BorderLayout.NORTH);

        JButton jButton1 = new JButton("《");
        jButton1.setSize(50,25);
        jButton1.setLocation(3,5);
        jButton1.setBackground(Color.LIGHT_GRAY);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(index == 0)return;
                jPanels.get(index--).setVisible(false);
                jPanels.get(index).setVisible(true);
            }
        });

        JButton jButton2 = new JButton("》");
        jButton2.setBackground(Color.LIGHT_GRAY);
        jButton2.setSize(50,25);
        jButton2.setLocation(56,5);
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(index == jPanels.size()-1)return;
                jPanels.get(index++).setVisible(false);
                jPanels.get(index).setVisible(true);
            }
        });

        jPanel1.add(jButton1);
        jPanel1.add(jButton2);

        JPanel jPanel2 = new JPanel(new CardLayout());
        jPanel2.setPreferredSize(new Dimension(540,400));
        add(jPanel2,BorderLayout.CENTER);

        HomePage homePage = new HomePage(jPanel2,this);
        homePage.setBackground(Color.CYAN);
        homePage.setPreferredSize(new Dimension(540,400));
        jPanel2.add(homePage);
        jPanels.add(homePage);
    }

    public ArrayList<JPanel> getjPanels() {
        return jPanels;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setjPanels(ArrayList<JPanel> jPanels) {
        this.jPanels = jPanels;
    }

//    public static void main(String[] args) {
//        JFrame jFrame = new JFrame("dfs");
//        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//
//        jFrame.setSize(540,400);
//        JP1 jp = new JP1();
//        jFrame.add(jp);
//        jFrame.setVisible(true);
//    }
}

package com.客户端;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class ButtonInterface extends JComponent {
    ArrayList<RectButton> array = new ArrayList<>();

    private static final int DEFAULT_WIDTH = 170;
    private static final int DEFAULT_HEIGHT = 200;

    private RectButton selectedButton;
    private RectButton current = null;

    public ButtonInterface(JPanel jp1, JPanel jp2, JPanel jp3, JPanel jp4) {
        double leftX = 5;
        double leftY = 5;
        RectButton button1 = new RectButton(leftX, leftY, "首页",jp1);
        RectButton button2 = new RectButton(leftX, leftY + (leftY + RectButton.HEIGHT) * 1, "查找",jp2);
        RectButton button3 = new RectButton(leftX, leftY + (leftY + RectButton.HEIGHT) * 2, "发布",jp3);
        RectButton button4 = new RectButton(leftX, leftY + (leftY + RectButton.HEIGHT) * 3, "xxx",jp4);

        array.add(button1);
        array.add(button2);
        array.add(button3);
        array.add(button4);

        selectedButton = button1;

        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < 4; i++) {
            RectButton rectButton = array.get(i);
            if (selectedButton == rectButton) {
                rectButton.drawButton(g2, Color.white);
            } else if (current == rectButton) {
                rectButton.drawButton(g2, Color.white);
            } else {
                rectButton.drawButton(g2, Color.LIGHT_GRAY);
            }
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    //鼠标事件监听器1
    private class MouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent event){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Point2D point = event.getPoint();
            RectButton rectButton = find(point.getX(),point.getY());
            if(rectButton!=null){
                selectedButton = rectButton;
                for(RectButton button : array){
                    button.getJpanel().setVisible(false);
                }
                selectedButton.getJpanel().setVisible(true);
            }
            repaint();
        }
    }

    //鼠标事件监听器2
    private class MouseMotionHandler implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Point2D point = e.getPoint();
            current = find(point.getX(),point.getY());
            repaint();
        }
    }

    public RectButton find(double x, double y){
        for(RectButton rectButton : array){
            if(rectButton.getRect().contains(x,y)){
                return rectButton;
            }
        }
        return null;
    }

    //按钮类(内部类)
    private class RectButton {
        public final static int WIDTH = 150;
        public final static int HEIGHT = 40;

        private Rectangle2D rect;
        private String message;
        private Font font = new Font("Serif", Font.BOLD, 17);
        private JPanel jpanel;

        //getter------------------------------
        public JPanel getJpanel() {
            return jpanel;
        }

        public Rectangle2D getRect() {
            return rect;
        }

        public Font getFont() {
            return font;
        }

        //按钮构造方法----------------------------------
        public RectButton(double x, double y, String s, JPanel jp) {
            createRect(x, y);
            createMess(s);
            jpanel = jp;
        }

        //创造矩形框------------------------------------
        private void createRect(double x, double y) {
            rect = new Rectangle2D.Double(x, y, WIDTH, HEIGHT);
        }

        //创建按钮文本-----------------------------------
        private void createMess(String s) {
            message = s;
        }

        //显示按钮--------------------------------------
        public void drawButton(Graphics2D g2, Color color) {
            g2.setPaint(color);
            g2.draw(rect);
            g2.fill(rect);
            g2.setFont(font);
            FontRenderContext context = g2.getFontRenderContext();
            Rectangle2D bounds = font.getStringBounds(message, context);
            double x1 = rect.getX() + (WIDTH - bounds.getWidth()) / 2;
            double y1 = rect.getY() + (HEIGHT - bounds.getHeight()) / 2 - bounds.getY();
            g2.setPaint(Color.BLACK);
            g2.drawString(message, (int) x1, (int) y1);
        }
    }
}



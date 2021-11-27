package com.客户端;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class JP4 extends JPanel {
    JPanel jPanel;
    JPanel jp4 = this;
    private JPanel jPanelx;
    public JP4(JPanel jPanel){
        this.jPanel = jPanel;
        setLayout(new BorderLayout());

        jPanelx =  new JPanel(null);
        jPanelx.setPreferredSize(new Dimension(530,400));

        JComponent1 jcomponent1 = new JComponent1();
//        System.out.println(jcomponent1.getWidth()+" "+jcomponent1.getHeight());
        jPanelx.add(jcomponent1);

        jPanelx.setSize(530,400);
        jPanelx.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
//                System.out.println("jpanelx "+jPanelx.getWidth()+" "+jPanelx.getHeight());
                jcomponent1.repaint();
            }
        });

        JScrollPane jScrollPane = new JScrollPane(jPanelx);
        JScrollBar Bar = null;
        Bar = jScrollPane.getVerticalScrollBar();
        Bar.setUnitIncrement(40);
        add(jScrollPane);
    }
    private class JComponent1 extends JComponent{
        public static final int WIDTH = 530;
        public static final int HEIGHT = 320;

        private Rectangle2D rect1;
        private Rectangle2D rect2;
        private Color rect1Color = Color.BLACK;
        private Color rect2Color = Color.BLACK;

        public JComponent1(){
            addMouseListener(new MouseHandler());
            addMouseMotionListener(new MouseMotionHandler());
            setSize(WIDTH,HEIGHT);
        }

        @Override
        protected void paintComponent(Graphics g) {
            calculation();
            Graphics2D g2 = (Graphics2D)g;
            g2.setPaint(Color.BLACK);
            Font font = new Font("宋体", Font.BOLD, 20);
            g2.setFont(font);
            FontRenderContext context = g2.getFontRenderContext();

            Rectangle2D rect = new Rectangle2D.Double(0,0,WIDTH-1,HEIGHT-1);
//            g2.draw(rect);

            g2.setPaint(rect1Color);
            rect1 = new Rectangle2D.Double(60,60,200,200);
            g2.draw(rect1);
            Rectangle2D bounds1 = font.getStringBounds("寻找失物", context);
            g2.drawString("寻找失物",(int)(rect1.getX()+(+rect1.getWidth()- bounds1.getWidth())/2),
                    (int)(rect1.getY()+(rect1.getHeight()-bounds1.getHeight())/2-bounds1.getY()));

            g2.setPaint(rect2Color);
            rect2 = new Rectangle2D.Double(rect1.getX()+rect1.getWidth()+10,60,200,200);
            g2.draw(rect2);
            Rectangle2D bounds2 = font.getStringBounds("寻找失主",context);
            g2.drawString("寻找失主",(int)(rect2.getX()+(rect2.getWidth()- bounds2.getWidth())/2),
                    (int)(rect2.getY()+(rect2.getHeight()-bounds2.getHeight())/2-bounds2.getY()));

        }

        public void calculation() {
            if (jPanelx.getWidth() >= 530&&jPanelx.getHeight()>=400) {
                int x = (jPanelx.getWidth() - getWidth()) / 2;
                int y = (jPanelx.getHeight()-getHeight())/2;
                setLocation(x, y);
//                System.out.println(jPanelx.getWidth() + " " + jPanelx.getHeight());
//                System.out.println(getX() + " " + getY());

            }
        }

        private class MouseHandler extends MouseAdapter {
            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Rectangle2D rectTemp = find(e);
                if(rectTemp == rect1) {
                    JPanel jp3 = new JP3(jPanel,JP3.LOST);
                    jPanel.add(jp3);
                    jp4.setVisible(false);
                    jp3.setVisible(true);
                }
                if(rectTemp == rect2){
                    JPanel jp3 = new JP3(jPanel,JP3.FOUND);
                    jPanel.add(jp3);
                    jp4.setVisible(false);
                    jp3.setVisible(true);
                }
            }
        }

        private class MouseMotionHandler implements MouseMotionListener{

            @Override
            public void mouseDragged(MouseEvent e) {
                //null
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Rectangle2D rectTemp = find(e);
                if(rectTemp == rect1) rect1Color = Color.cyan;
                if(rectTemp == rect2) rect2Color = Color.cyan;
                if(rectTemp == null) {
                    rect1Color = Color.BLACK;
                    rect2Color = Color.BLACK;
                }
                repaint();
            }
        }

        private Rectangle2D find(MouseEvent e){
            if(rect1.contains(e.getPoint())){
                return rect1;
            }
            if(rect2.contains(e.getPoint())){
                return rect2;
            }
            return null;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(WIDTH,HEIGHT);
        }
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        JPanel jPanel1 = new JPanel(new CardLayout());
        jPanel1.setSize(530,400);
        jPanel1.add(new JP4(jPanel1));
        jPanel1.setBackground(Color.YELLOW);

        jFrame.add(jPanel1);
        jFrame.setSize(300,300);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
}

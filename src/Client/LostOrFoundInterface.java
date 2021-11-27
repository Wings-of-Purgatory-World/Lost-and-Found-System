package com.客户端;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class LostOrFoundInterface{

    private String type;         //物品类型
    private String name;         //物品名称
    private String color;        //主要颜色
    private String place;        //丢失地点
    private String number;       //手机号码
    private String time;         //发布时间
    private String describe;     //描述信息

    private JScrollPane jScrollPane;
    private static JPanel jPanelx;

    public LostOrFoundInterface(String...ss){
        type     = ss[0];
        name     = ss[1];
        color    = ss[2];
        place    = ss[3];
        number   = ss[4];
        time     = ss[5];
        describe = ss[6];

        jPanelx =  new JPanel(null);
        jPanelx.setPreferredSize(new Dimension(540,800));

//        jPanelx.setBackground(Color.red);
        DetailedInformation detailedInformation = new DetailedInformation();
        jPanelx.add(detailedInformation);
        jPanelx.setSize(540,700);
        jPanelx.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                detailedInformation.repaint();
            }
        });
        jScrollPane = new JScrollPane(jPanelx);
        JScrollBar Bar = null;
        Bar = jScrollPane.getVerticalScrollBar();
        Bar.setUnitIncrement(40);
    }

    class DetailedInformation extends JComponent{

        public static final int WIDTH = 540;
        public static final int HEIGHT = 700;

        public DetailedInformation(){
            setSize(WIDTH,HEIGHT);
            JTextArea jTextArea = new JTextArea();
            jTextArea.setWrapStyleWord(true);
            jTextArea.setLineWrap(true);
            jTextArea.setBackground(Color.white);
            jTextArea.setText(describe);
            jTextArea.setEditable(false);
            JScrollPane jScrollPane = new JScrollPane(jTextArea);
            jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
            jScrollPane.setSize(350,150);
            jScrollPane.setLocation(100,400);
            add(jScrollPane);

        }

        @Override
        protected void paintComponent(Graphics g) {
            calculation();

            Graphics2D g2 = (Graphics2D)g;
            g2.setPaint(Color.BLACK);
            Font font = new Font("宋体", Font.BOLD, 20);
            g2.setFont(font);
            FontRenderContext context = g2.getFontRenderContext();
            Rectangle2D bounds1 = font.getStringBounds("* 物品类型 :  ", context);
            Rectangle2D rect = new Rectangle2D.Double(0,0,539,600);
            g2.draw(rect);

            int x = (int)(getWidth()/2 - bounds1.getWidth());
            int y = 20;
            g2.drawString("* 物品类型 : ",x,y-(int)bounds1.getY());
            g2.drawString("* 物品名称 : ",x,y+(int)(bounds1.getHeight()+30)-(int)bounds1.getY());
            g2.drawString("* 主要颜色 : ",x,y+(int)(bounds1.getHeight()+30)*2-(int)bounds1.getY());
            g2.drawString("* 丢失地点 : ",x,y+(int)(bounds1.getHeight()+30)*3-(int)bounds1.getY());
            g2.drawString("* 联系方式 : ",x,y+(int)(bounds1.getHeight()+30)*4-(int)bounds1.getY());
            g2.drawString("* 发布时间 : ",x,y+(int)(bounds1.getHeight()+30)*5-(int)bounds1.getY());
            g2.drawString("* 描述 :",x,y+(int)(bounds1.getHeight()+30)*6-(int)bounds1.getY()+30);

            g2.setFont(new Font("宋体", Font.BOLD, 15));
            g2.drawString(type,x+(int)bounds1.getWidth(),y-(int)bounds1.getY());
            g2.drawString(name,x+(int)bounds1.getWidth(),y+(int)(bounds1.getHeight()+30)*1-(int)bounds1.getY());
            g2.drawString(color,x+(int)bounds1.getWidth(),y+(int)(bounds1.getHeight()+30)*2-(int)bounds1.getY());
            g2.drawString(place,x+(int)bounds1.getWidth(),y+(int)(bounds1.getHeight()+30)*3-(int)bounds1.getY());
            g2.drawString(number,x+(int)bounds1.getWidth(),y+(int)(bounds1.getHeight()+30)*4-(int)bounds1.getY());
            g2.drawString(time,x+(int)bounds1.getWidth(),y+(int)(bounds1.getHeight()+30)*5-(int)bounds1.getY());

        }

        public void calculation(){
            if(jPanelx.getWidth()>=540){
                int x = (jPanelx.getWidth()-getWidth())/2;
                int y = 100;
                setLocation(x,y);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(WIDTH,HEIGHT);
        }
    }

    //一个对象只能使用一次
    public JPanel getJpanel(){
        JPanel jPanel = new JPanel(new BorderLayout());
        jPanel.add(jScrollPane);
        jPanel.setSize(540,400);
        return jPanel;
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("dfs");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        LostOrFoundInterface lostOrFoundInterface = new LostOrFoundInterface("电子产品","荣耀手机","红色","教二","1234567891","2019-2-21","fhwoeh\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        JPanel jPanel1 = new JPanel(new CardLayout());

        jPanel1.setBackground(Color.MAGENTA);
        jPanel1.add(lostOrFoundInterface.getJpanel());
        jFrame.add(jPanel1);
        jFrame.setVisible(true);
        jFrame.pack();
        jPanelx.setPreferredSize(new Dimension(540,700));
    }
}



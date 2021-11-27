package com.客户端;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Informations extends JComponent {
    public static final int WIDTH = 200;
    public static final int HEIGHT = 70;

    //信息框显示的颜色
    private Color state = Color.BLACK;

    private String type = "";         //物品类型
    private String name = "";         //物品名称
    private String color = "";        //主要颜色
    private String place = "";        //丢失地点
    private String number = "";       //手机号码
    private String time = "";         //发布时间
    private String describe = "";     //描述信息

    private JPanel jPanel;
    private Rectangle2D rect;
    private LostOrFoundInterface lostOrFoundInterface;
    private JP1 jpx;

    public Informations(String s, JPanel jPanel, JP1 jpx){
        InformationSegmentation(s);
        this.jPanel = jPanel;
        this.jpx = jpx;
        addMouseListener(new MouseHandler());
    }

    public void InformationSegmentation(String s){
        String[] ss = s.split("##");
        for(String temp : ss){
            String []ss1 = temp.split("//");
            if(ss1[1].equalsIgnoreCase("null"))break;
            switch(ss1[0]){
                case "物品类型":{
                    type = ss1[1];
                    break;
                }
                case "物品名称":{
                    name = ss1[1];
                    break;
                }
                case "描述信息":{
                    describe = ss1[1];
                    break;
                }
                case "主要颜色":{
                    color = ss1[1];
                    break;
                }
                case "联系方式":{
                    number = ss1[1];
                    break;
                }
                case "发布时间":{
                    Date date = new Date(Long.valueOf(ss1[1]));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    time = format.format(date);
                    break;
                }
                case "地点":{
                    place = ss1[1];
                    break;
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D)g;
        Font font = new Font("Serif", Font.BOLD, 13);
        g2.setFont(font);
        g2.setPaint(state);

        rect = new Rectangle2D.Double(0,0,WIDTH-1,HEIGHT-1);
        g2.draw(rect);

        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds1 = font.getStringBounds(name, context);

        g2.drawString(name,7,(int)(7-bounds1.getY()));
        g2.drawString(type,7,(int)(7+bounds1.getHeight()+5-bounds1.getY()));
        g2.drawString(place,7,(int)(7+(bounds1.getHeight()+5)*2-bounds1.getY()));
    }

    //鼠标事件
    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            state = Color.cyan;
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            state = Color.BLACK;
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            lostOrFoundInterface = new LostOrFoundInterface(type,name,
                    color,place,number,time,describe);

            ArrayList<JPanel> jPanels = new ArrayList<>();
            for(int i = 0; i <=jpx.getIndex(); i++){
                jPanels.add(jpx.getjPanels().get(i));
            }

            jPanels.add(lostOrFoundInterface.getJpanel());
            jpx.setjPanels(jPanels);
            jPanel.removeAll();

            //重新添加面板
            for(JPanel jp : jpx.getjPanels()){
                jPanel.add(jp);
                jp.setVisible(false);
            }

            jpx.getjPanels().get(jPanels.size()-1).setVisible(true);

            jpx.setIndex(jpx.getIndex()+1);
        }
    }

    public String getTime() {
        return time;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH,HEIGHT);
    }

}

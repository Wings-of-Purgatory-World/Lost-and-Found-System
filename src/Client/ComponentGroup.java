package com.客户端;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

public class ComponentGroup{
    public static final int LOST = 11;
    public static final int FOUND = 22;

    private ArrayList<Informations> array = new ArrayList<>();
    private int state;
    private JPanel jPanel;
    private JP1 jpx;
    private JPanel jp2;
    private Load load;
    private int previousNumber = 19;

    //jpanel:CardLayout所在页, jp2:JP2所在页(绘制组件页), jpx:JP1所在页
    public ComponentGroup(JPanel jPanel, JPanel jp2, JP1 jpx, int state){
        this.jPanel = jPanel;
        this.jpx = jpx;
        this.jp2 = jp2;
        this.state = state;
        this.load = new Load();
        load.setSize(Load.WIDTH,Load.HEIGHT);

        if(state == LOST){
            try {
                previousNumber = conversionFormat(ClientTask.acquireLostInformation("0"),array);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(previousNumber >= 20) {
                try {
                    previousNumber = conversionFormat(ClientTask.acquireLostInformation(
                            array.get(array.size() - 1).getTime()), array);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if(state == FOUND){
            try {
                conversionFormat(ClientTask.acquireFoundInformation("0"),array);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(previousNumber >= 20){
                try {
                    previousNumber = conversionFormat(ClientTask.acquireFoundInformation(
                            array.get(array.size()-1).getTime()),array);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        draw();

//        面板高度动态刷新
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int b1 = -1;
                while (true) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int a = (jp2.getWidth() - 100) / 220;//列数
                    if (a > 5) a = 5;
                    int b;//行数
                    if (array.size() % a == 0) {
                        b = array.size() / a;
                    } else {
                        b = array.size() / a + 1;
                    }
                    if (b1 != b) {
                        b1 = b;
                        if (b == 0) {
                            jp2.setPreferredSize(new Dimension(540, 400));
                        } else {
                            jp2.setPreferredSize(new Dimension(540, 200 + 85 * b));
                        }
                    }
                }
            }
        });
        thread.start();
    }

    public int conversionFormat(ArrayList<String> array,ArrayList<Informations> arrayx){
        if(array==null)return 0;
        for(String s : array){
            arrayx.add(new Informations(s,jPanel,jpx));
        }
        return array.size();
    }

    //绘制组件
    public void draw() {
        jp2.removeAll();
        recalculate();
        jp2.add(load);
        for (Informations informations : array) {
            informations.setSize(200,70);
            jp2.add(informations);
            informations.repaint();
        }
    }

    public void recalculate() {
        int width = jp2.getWidth();

        //容器容量大于540可重新计算
        if (width >= 540 && width <= 1200) {

            int a = (width - 100) / 220;//列数
            int b ;//行数
            if(array.size()%a==0){
                b = array.size()/a;
            }else{
                b=array.size()/a+1;
            }

            int x = (jp2.getWidth() - (20 * (a - 1) + a * 200) )/ 2;
            int y = 100;

            for (int i = 0; i < b; i++) {
                for (int j = 1; j <= a; j++) {
                    //若计算完毕则跳出循环
                    if (i * a + j > array.size()) break;

                    //计算信息组件位置
                    array.get(i * a + j - 1).setLocation(x + (j - 1) * 220,y + i * 85);
                }
            }

            load.setLocation((int)(jp2.getWidth()-load.getWidth())/2,(int)(jp2.getHeight()-load.getHeight()-20));
        } else if (width > 1200) {
            int a = 5;
            int b;
            if(array.size()%a==0){
                b = array.size()/a;
            }else{
                b=array.size()/a+1;
            }

            int x = (jp2.getWidth() - (20 * (a - 1) + a * 200) )/ 2;
            int y = 100;

            for (int i = 0; i < b; i++) {
                for (int j = 1; j <= a; j++) {

                    //若计算完毕则跳出循环
                    if (i * a + j > array.size()) break;

                    //计算信息组件位置
                    array.get(i * a + j - 1).setLocation(x + (j - 1) * 220,y + i * 85);
                }
            }
            load.setLocation((int)(jp2.getWidth()-load.getWidth())/2,(int)(jp2.getHeight()-load.getHeight()-20));
        }
    }

    private class Load extends JComponent{
        public static final int WIDTH = 110;
        public static final int HEIGHT = 20;

        public Load(){
            addMouseListener(new MouseHandler());
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            Font font = new Font("Serif", Font.BOLD, 13);
            g2.setPaint(Color.BLACK);
            Rectangle2D rectangle2D = new Rectangle2D.Double(0,0,WIDTH-1,HEIGHT-1);
//            g2.draw(rectangle2D);

            if(previousNumber < 20){
                FontRenderContext context = g2.getFontRenderContext();
                Rectangle2D bounds = font.getStringBounds("~~到底了~~", context);
                g2.drawString("~~到底了~~",(int)((rectangle2D.getWidth()-bounds.getWidth())/2),(int)(-bounds.getY()));
            }else{
                FontRenderContext context = g2.getFontRenderContext();
                Rectangle2D bounds = font.getStringBounds("按一下，加载信息", context);
                g2.drawString("按一下，加载信息 ",(int)((rectangle2D.getWidth()-bounds.getWidth())/2)+7,(int)(-bounds.getY()));
            }
        }

        private class MouseHandler extends MouseAdapter {
            public void mousePressed(MouseEvent event){
                if(previousNumber >= 20){
                    if(state == LOST){
                        try {
                            previousNumber = conversionFormat(ClientTask.acquireLostInformation(
                                    array.get(array.size() - 1).getTime()), array);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        draw();
                        System.out.println("lost");
                    }else if(state == FOUND){
                        try {
                            previousNumber = conversionFormat(ClientTask.acquireFoundInformation(
                                    array.get(array.size() - 1).getTime()), array);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        draw();
                        System.out.println("found");
                    }
                }
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(WIDTH,HEIGHT);
        }
    }
}

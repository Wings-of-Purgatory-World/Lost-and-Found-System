package com.客户端;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

//主页组件布局群
public class ComponentGroup1 {
    private ArrayList<Informations> array1 = new ArrayList<>();//寻找失物 Lost
    private ArrayList<Informations> array2 = new ArrayList<>();//寻找失主 Found
    private More more1, more2;
    private Category cate1, cate2;
    private int lost,found;
    private JP1 jpx;

    //jp1为公共JPanel,jp2为主页JPanel
    private JPanel jp1, jp2;

    public ComponentGroup1(JPanel jp1, JPanel jp2, JP1 jpx) {
        this.jp1 = jp1;
        this.jp2 = jp2;
        this.jpx = jpx;
        more1 = new More(More.LOST);
        more2 = new More(More.FOUND);
        more1.setSize(42, 16);
        more2.setSize(42, 16);
        cate1 = new Category("找失物");
        cate2 = new Category("找失主");
        cate1.setSize(70, 20);
        cate2.setSize(70, 20);
        try {
            lost = conversionFormat(ClientTask.acquireLostInformation("0"),array1);
//            System.out.println("lost"+lost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            found = conversionFormat(ClientTask.acquireFoundInformation("0"),array2);
//            System.out.println("found"+found);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Informations i : array1) {
            i.setSize(200, 70);
            jp2.add(i);
        }
        for (Informations i : array2) {
            i.setSize(200, 70);
            jp2.add(i);
        }
        draw();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int b1 = -1;
                while (true) {
                    int length = 100;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int a = (jp2.getWidth() - 100) / 220;//列数
                    int b;//行数

                    if (a > 5) a = 5;

                    if (array1.size() % a == 0) {
                        b = array1.size() / a;
                        length += 100;
                    } else {
                        b = array1.size() / a + 1;
                        length += 100;
                    }

                    if (array2.size() % a == 0) {
                        b += array2.size() / a;
                        length += 100;
                    } else {
                        b += array2.size() / a + 1;
                        length += 100;
                    }

                    if (b1 != b) {
                        b1 = b;
                        if (b == 0) {
                            jp2.setPreferredSize(new Dimension(540, 400));
                        } else {
                            jp2.setPreferredSize(new Dimension(540, length + 85 * b));
                        }
                    }
                }
            }
        });
        thread.start();
    }

    public int conversionFormat(ArrayList<String> array, ArrayList<Informations> arrayx) {
        if (array == null) return 0;
        for (String s : array) {
            arrayx.add(new Informations(s, jp1, jpx));
        }
        return array.size();
    }

    public void draw() {
        jp2.removeAll();
        //第一次计算
        int[] arr = recalculate(0, 0, array1, more1, cate1);

        //结果为null则无需绘制array1组件
        if (arr == null) {
            //计算array2组件位置
            int[] arr1 = recalculate(0, 0, array2, more2, cate2);
            //无组件则返回
            if (arr1 == null) return;
            //绘制array2组件
            if(found>0) {
                for (Informations i : array2) {
                    jp2.add(i);
                    i.repaint();
                }
                jp2.add(more2);
                jp2.add(cate2);
                cate2.repaint();
                more2.repaint();
                return;
            }
        }
        int y2 = 0;
        //绘制array1组件
        if(lost>0) {
            for (Informations i : array1) {
                jp2.add(i);
                i.repaint();
            }
            jp2.add(more1);
            more1.repaint();
            jp2.add(cate1);
            cate1.repaint();
            y2 = arr[1];
        }

        if(found >0) {
            //计算array2组件位置
            int[] arr1 = recalculate(arr[0], y2, array2, more2, cate2);
            if (arr1 == null) return;
            for (Informations i : array2) {
                jp2.add(i);
                i.repaint();
            }
            jp2.add(more2);
            jp2.add(cate2);
            more2.repaint();
            cate2.repaint();
            return;
        }
    }

    //计算组件位置方法
    public int[] recalculate(int zeroPointX, int zeroPointY, ArrayList<Informations> array, More more, Category category) {
        int width = jp2.getWidth();

        //容器容量大于540可重新计算
        if (width >= 540 && width <= 1200) {

            int a = (width - 100) / 220;//列数
            int b;//行数
            if (array.size() % a == 0) {
                b = array.size() / a;
            } else {
                b = array.size() / a + 1;
            }

            int x = (jp2.getWidth() - (20 * (a - 1) + a * 200)) / 2 + zeroPointX;
            int y = 100 + zeroPointY;

            more.setLocation(x + (a - 1) * 220 + 160, y - 30);
            category.setLocation(x, y - 30);

            for (int i = 0; i < b; i++) {
                for (int j = 1; j <= a; j++) {
                    //若计算完毕则跳出循环
                    if (i * a + j > array.size()) break;

                    //计算信息组件位置
                    array.get(i * a + j - 1).setLocation(x + (j - 1) * 220, y + i * 85);
                }
            }
            int[] arr = {0, (int) (y + (b + 1) * 85)};
            return arr;
        } else if (width > 1200) {
            int a = 5;
            int b;
            if (array.size() % a == 0) {
                b = array.size() / a;
            } else {
                b = array.size() / a + 1;
            }

            int x = (jp2.getWidth() - (20 * (a - 1) + a * 200)) / 2 + zeroPointX;
            int y = 100 + zeroPointY;

            more.setLocation(x + (a - 1) * 220 + 160, y - 30);
            category.setLocation(x, y - 30);

            for (int i = 0; i < b; i++) {
                for (int j = 1; j <= a; j++) {

                    //若计算完毕则跳出循环
                    if (i * a + j > array.size()) break;

                    //计算信息组件位置
                    array.get(i * a + j - 1).setLocation(x + (j - 1) * 220, y + i * 85);
                }
            }
            int[] arr = {0, (int) (y + (b + 1) * 85)};
            return arr;
        }
        return null;
    }


    private class More extends JComponent {
        public static final int WIDTH = 42;
        public static final int HEIGHT = 16;
        public static final int LOST = 11;
        public static final int FOUND = 22;

        private int state;

        private Color color = Color.BLACK;

        public More(int state) {
            this.state = state;
            addMouseListener(new MouseHandler());
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            Font font = new Font("Serif", Font.BOLD, 13);
            g2.setFont(font);
            g2.setPaint(color);

            Rectangle2D rectangle2D = new Rectangle2D.Double(0, 0, WIDTH - 1, HEIGHT - 1);

            FontRenderContext context = g2.getFontRenderContext();
            Rectangle2D bounds1 = font.getStringBounds("更多...", context);

            g2.drawString("更多...", 0, (int) (-bounds1.getY()));
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(WIDTH, HEIGHT);
        }

        private class MouseHandler extends MouseAdapter {
            public void mousePressed(MouseEvent event) {
                if (state == LOST) {
                    JP2 jPanel = new JP2(jp1, jpx,JP2.LOST);
                    ArrayList<JPanel> jPanels = new ArrayList<>();
                    for(int i = 0; i <=jpx.getIndex(); i++){
                        jPanels.add(jpx.getjPanels().get(i));
                    }

                    jPanels.add(jPanel);
                    jpx.setjPanels(jPanels);
                    jp1.removeAll();

                    //重新添加面板
                    for(JPanel jp : jpx.getjPanels()){
                        jp1.add(jp);
                        jp.setVisible(false);
                    }

                    jpx.getjPanels().get(jPanels.size()-1).setVisible(true);
                    jpx.setIndex(jpx.getIndex()+1);

                } else if (state == FOUND) {
                    JP2 jPanel = new JP2(jp1, jpx, JP2.FOUND);
                    ArrayList<JPanel> jPanels = new ArrayList<>();
                    for(int i = 0; i <=jpx.getIndex(); i++){
                        jPanels.add(jpx.getjPanels().get(i));
                    }

                    jPanels.add(jPanel);
                    jpx.setjPanels(jPanels);
                    jp1.removeAll();

                    //重新添加面板
                    for(JPanel jp : jpx.getjPanels()){
                        jp1.add(jp);
                        jp.setVisible(false);
                    }

                    jpx.getjPanels().get(jPanels.size()-1).setVisible(true);
                    jpx.setIndex(jpx.getIndex()+1);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                color = Color.CYAN;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                color = Color.BLACK;
                repaint();
            }
        }
    }
}
class Category extends JComponent{
    public static final int WIDTH = 70;
    public static final int HEIGHT = 20;
    String category;
    public Category(String category){
        this.category = category;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        Font font = new Font("Serif", Font.BOLD, 13);
        g2.setFont(font);
        g2.setPaint(Color.BLACK);
        Rectangle2D rectangle2D = new Rectangle2D.Double(0,0,WIDTH-1,HEIGHT-1);
//        g2.draw(rectangle2D);

        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds1 = font.getStringBounds(category, context);
        g2.drawString(category,0, (int)(-bounds1.getY()));
        Line2D line2D = new Line2D.Double(0,(int)bounds1.getHeight(),70,(int)bounds1.getHeight());
        g2.draw(line2D);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH,HEIGHT);
    }
}

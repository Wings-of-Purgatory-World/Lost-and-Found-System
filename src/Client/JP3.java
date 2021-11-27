package com.客户端;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class JP3 extends JPanel{
    public static int LOST = 11;
    public static int FOUND =22;
    private static JPanel jPanel;
    private static int state;
    static String choose1 = "电子产品", choose2 = "白色";
    private JPanel jPanelthis = this;
    private JPanel jPanelx;

    public JP3(JPanel jPanel,int state){
        setLayout(new BorderLayout());
        this.jPanel = jPanel;
        this.state = state;

        jPanelx =  new JPanel(null);
        jPanelx.setPreferredSize(new Dimension(540,900));

        jcomponent1 jcomponent1 = new jcomponent1();
        jPanelx.add(jcomponent1);

        jPanelx.setSize(540,800);
        jPanelx.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("jpanelx "+jPanelx.getWidth()+" "+jPanelx.getHeight());
                jcomponent1.repaint();
            }
        });

        JScrollPane jScrollPane = new JScrollPane(jPanelx);
        JScrollBar Bar = null;
        Bar = jScrollPane.getVerticalScrollBar();
        Bar.setUnitIncrement(40);
        add(jScrollPane);
    }
    private class jcomponent1 extends JComponent {
        public static final int WIDTH = 540;
        public static final int HEIGHT = 700;
        public jcomponent1(){
            setSize(540,700);

            //jTextField 和 jLabel 用于计算长度宽度，不用于显示
            JTextField jTextField = createJTextField();
            JLabel jLabel = createJLable("物品类型");

            int labelX = (getWidth()-(jTextField.getWidth()+jLabel.getWidth()+20))/2;

            JLabel jLabel1 = createJLable("物品类型");
            jLabel1.setLocation(labelX,100);
            add(jLabel1);

            JComboBox<String> comboBox1 = createJComboBox(new String[]{"电子产品","穿戴物品","卡","包","书本","日用品","学习用品"},44);
            comboBox1.setLocation(jLabel.getWidth()+jLabel1.getX()+20,jLabel1.getY()-6);
            add(comboBox1);

            JLabel jLabel2 = createJLable("主要颜色");
            jLabel2.setLocation(labelX,jLabel1.getY()+jLabel.getHeight()+30);
            add(jLabel2);

            JComboBox<String> comboBox2 = createJComboBox(new String[]{"红","黄","蓝","绿","橙","青","紫"},44);
            comboBox2.setLocation(jLabel.getWidth()+jLabel2.getX()+20,jLabel2.getY()-6);
            add(comboBox2);

            JLabel jLabel3 = createJLable("物品名称");
            jLabel3.setLocation(labelX,jLabel2.getY()+jLabel.getHeight()+30);
            add(jLabel3);

            JTextField jTextField3 = createJTextField();
            jTextField3.setLocation(jLabel.getWidth()+jLabel3.getX()+20,jLabel3.getY()-6);
            add(jTextField3);

            JLabel jLabel4 = createJLable("地点  ");
            jLabel4.setLocation(labelX,jLabel3.getY()+jLabel.getHeight()+30);
            add(jLabel4);

            JTextField jTextField4 = createJTextField();
            jTextField4.setLocation(jLabel.getWidth()+jLabel4.getX()+20,jLabel4.getY()-6);
            add(jTextField4);

            JLabel jLabel5 = createJLable("联系电话");
            jLabel5.setLocation(labelX,jLabel4.getY()+jLabel.getHeight()+30);
            add(jLabel5);

            JTextField jTextField5 = createJTextField();
            jTextField5.setLocation(jLabel.getWidth()+jLabel5.getX()+20,jLabel5.getY()-6);
            add(jTextField5);

            JLabel jLabel6 = createJLable("详细描述");
            jLabel6.setLocation(labelX,jLabel5.getY()+jLabel.getHeight()+30);
            add(jLabel6);

            JTextArea jTextArea = new JTextArea();
            jTextArea.setWrapStyleWord(true);
            jTextArea.setLineWrap(true);
            jTextArea.setBackground(Color.white);
            JScrollPane jScrollPane = new JScrollPane(jTextArea);
            jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
            jScrollPane.setSize(330,150);
            jScrollPane.setLocation(110,400);
            add(jScrollPane);

            //物品类型//包##物品名称//书包##主要颜色//蓝色##描述信息//还不错##地点//教一##联系方式//15819293595##发布时间//
            JButton jButton1 = new JButton("确认");
            jButton1.setBackground(Color.WHITE);
            jButton1.setSize(80,30);
            jButton1.setLocation(jLabel6.getX(),580);
            jButton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(state==LOST){
                        String s = "物品类型//"+((choose1.equalsIgnoreCase(""))?"null":choose1)+"##物品名称//"+
                                ((jTextField3.getText().equalsIgnoreCase(""))?"null":jTextField3.getText())+"##主要颜色//"+
                                ((choose2.equalsIgnoreCase(""))?"null":choose2)+"##描述信息//"+
                                (jTextArea.getText().equalsIgnoreCase("")?"null":jTextArea.getText())+"##地点//"+
                                (jTextField4.getText().equalsIgnoreCase("")?"null":jTextField4.getText())+ "##联系方式//"+
                                (jTextField5.getText().equalsIgnoreCase("")?"null":jTextField5.getText())+"##发布时间//";
                        try {
                            ClientTask.lostPublish(s);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        System.out.println(s);
                        jPanel.remove(jPanelthis);
                    }else if(state == FOUND){
                        String s = "物品类型//"+((choose1.equalsIgnoreCase(""))?"null":choose1)+"##物品名称//"+
                                ((jTextField3.getText().equalsIgnoreCase(""))?"null":jTextField3.getText())+"##主要颜色//"+
                                ((choose2.equalsIgnoreCase(""))?"null":choose2)+"##描述信息//"+
                                (jTextArea.getText().equalsIgnoreCase("")?"null":jTextArea.getText())+"##地点//"+
                                (jTextField4.getText().equalsIgnoreCase("")?"null":jTextField4.getText())+ "##联系方式//"+
                                (jTextField5.getText().equalsIgnoreCase("")?"null":jTextField5.getText())+"##发布时间//";
                        try {
                            ClientTask.foundPublish(s);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        System.out.println(s);
                        jPanel.remove(jPanelthis);
                    }

                }
            });
            add(jButton1);

            JButton jButton2 = new JButton("取消");
            jButton2.setBackground(Color.WHITE);
            jButton2.setSize(80,30);
            jButton2.setLocation(jLabel6.getX()+230,580);
            jButton2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jPanel.remove(jPanelthis);
                }
            });
            add(jButton2);
        }

        @Override
        protected void paintComponent(Graphics g) {
            calculation();
            Graphics2D g2 = (Graphics2D)g;
            g2.setPaint(Color.BLACK);
            Rectangle2D rect = new Rectangle2D.Double(0,0,539,699);
//            g2.draw(rect);
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
            return new Dimension(540,700);
        }
    }

    public static JTextField createJTextField(){
        JTextField field = new JTextField();
        field.setFont(new Font("Serif", Font.BOLD, 15));
        field.setBackground(new Color(255, 255, 255, 255));
        field.setPreferredSize(new Dimension(200, 35));
        field.setSize(200,35);
        MatteBorder border = new MatteBorder(2, 2, 2, 2, new Color(192, 192, 192));
        field.setBorder(border);
        return field;
    }

    public static JLabel createJLable(String s){
        JLabel jLabel = new JLabel(s);
        jLabel.setFont(new Font("Serif", Font.BOLD, 17));
        jLabel.setBackground(Color.CYAN);
        jLabel.setPreferredSize(new Dimension(50,35));
        jLabel.setSize(80,20);
        return jLabel;
    }

    public  static JComboBox<String> createJComboBox(String [] ss,int choose) {
        final int COLOR = 33;
        final int TYPE = 44;
        for (String s : ss) {
            s = s + "                           ";
        }

        final JComboBox<String> comboBox = new JComboBox<>(ss);
        comboBox.setFont(new Font("Serif", Font.BOLD, 17));
        MatteBorder matteBorder = new MatteBorder(2, 2, 2, 2, new Color(192, 192, 192));
        comboBox.setBorder(matteBorder);

        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // 只处理选中的状态
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if(choose == TYPE){
                        choose1 = (String)e.getItem();
                        choose1 = choose1.replaceAll(" ","");
                    }else if(choose == COLOR){
                        choose2 = (String)e.getItem();
                        choose2 = choose1.replaceAll(" ","");
                    }
                }
            }
        });
        comboBox.setSelectedIndex(0);
        comboBox.setSize(200,30);
        return comboBox;
    }
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.add(new JP3(null,JP3.FOUND));
        jFrame.setSize(300,300);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
}


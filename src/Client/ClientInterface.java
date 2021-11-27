package com.客户端;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class ClientInterface extends JFrame {
    private String name;        //昵称
    private String password;    //密码
    private String account;     //账号
    private boolean state = false;    //状态（true为可以检查，false为还未输入）
    JFrame jFrame = this;
    //客户端界面(包含登录界面和主界面)
    public ClientInterface() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        signIn();
        mainInterface();
    }

    //登录界面
    private void signIn(){

        setSize(540, 440);
        setResizable(false);
        setLocationRelativeTo(null);
        JTextField jTextField = createJTextField();

        JPanel jPanel = new JPanel(null);
        jPanel.setSize(540,420);
        add(jPanel);

        JLabel jLabel1 = new JLabel("账号");
        jLabel1.setSize(60,35);
        jLabel1.setOpaque(true);
        jLabel1.setFont(new Font("Serif", Font.BOLD, 20));
        jLabel1.setLocation((jPanel.getWidth()-jLabel1.getWidth()-jTextField.getWidth()-10)/2,
                100);
//        jLabel1.setBackground(Color.PINK);
        jPanel.add(jLabel1);

        JTextField jTextField1 = createJTextField();
        jTextField1.setLocation(jLabel1.getX()+jLabel1.getWidth()+10,jLabel1.getY());
        jPanel.add(jTextField1);

        JLabel jLabel2 = new JLabel("密码");
        jLabel2.setSize(60,35);
        jLabel2.setOpaque(true);
        jLabel2.setFont(new Font("Serif", Font.BOLD, 20));
        jLabel2.setLocation(jLabel1.getX(),jLabel1.getY()+jLabel1.getHeight()+20);
//        jLabel2.setBackground(Color.PINK);
        jPanel.add(jLabel2);

        JTextField jTextField2 = createJTextField();
        jTextField2.setLocation(jLabel2.getX()+jLabel2.getWidth()+10,jLabel2.getY());
        jPanel.add(jTextField2);

        JLabel jLabel3 = new JLabel("账号或密码错误");
        jLabel3.setSize(200,25);
        jLabel3.setFont(new Font("Serif", Font.BOLD, 15));
        jLabel3.setLocation(jLabel2.getX(),jLabel2.getY()+jLabel2.getHeight()+2);
        jLabel3.setForeground(Color.red);
        jLabel3.setVisible(false);
        jPanel.add(jLabel3);

        JButton jButton = new JButton("登录");
        jButton.setSize(270,40);
        jButton.setFont(new Font("Serif", Font.BOLD, 20));
        jButton.setLocation(jLabel2.getX(),jLabel2.getY()+jLabel2.getHeight()+30);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String temp = ClientTask.signIn(jTextField1.getText()+"##"+jTextField2.getText()).get(0);
                    System.out.println(temp);
                    if(temp.equalsIgnoreCase("false")){
                        jLabel3.setVisible(true);
                        return;
                    }
                    state = true;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        jPanel.add(jButton);

        JLabel jLabel4 = new JLabel("注册账号");
        jLabel4.setSize(70,25);
        jLabel4.setFont(new Font("Serif", Font.BOLD, 15));
        jLabel4.setLocation(5,jPanel.getHeight()-jLabel4.getHeight()-30);
        jLabel4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                register(jFrame,jFrame);
            }
        });
        jPanel.add(jLabel4);

        setVisible(true);

        //锁
        while(!state){
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        remove(jPanel);
        System.out.println(1);
    }

    private JTextField createJTextField(){
        JTextField field = new JTextField();
        field.setFont(new Font("Serif", Font.BOLD, 15));
        field.setBackground(new Color(255, 255, 255, 255));
        field.setPreferredSize(new Dimension(200, 35));
        field.setSize(200,35);
        MatteBorder border = new MatteBorder(2, 2, 2, 2, new Color(192, 192, 192));
        field.setBorder(border);
        return field;
    }

    private void register(Frame owner, Component parentComponent) {
        // 创建一个注册对话框
        final JDialog dialog = new JDialog(owner, "注册", true);
        // 设置对话框的宽高
        dialog.setSize(500, 700);
        // 设置对话框大小不可改变
        dialog.setResizable(false);
        // 设置对话框相对显示的位置
        dialog.setLocationRelativeTo(parentComponent);

        JTextField jTextField = createJTextField();

        // 创建对话框的内容面板
        JPanel panel = new JPanel(null);
        panel.setSize(500,700);

        JLabel jLabel1 = new JLabel("用户名");
        jLabel1.setSize(100,35);
        jLabel1.setFont(new Font("Serif", Font.BOLD, 20));
        jLabel1.setLocation((panel.getWidth()-jLabel1.getWidth()-jTextField.getWidth()-10)/2, 130);
        panel.add(jLabel1);

        JTextField jTextField1 = createJTextField();
        jTextField1.setLocation(jLabel1.getX()+jLabel1.getWidth()+10,jLabel1.getY());
        panel.add(jTextField1);

        JLabel jLabel2 = new JLabel("账号");
        jLabel2.setSize(100,35);
        jLabel2.setFont(new Font("Serif", Font.BOLD, 20));
        jLabel2.setLocation(jLabel1.getX(),jLabel1.getY()+jLabel1.getHeight()+20);
        panel.add(jLabel2);

        JTextField jTextField2 = createJTextField();
        jTextField2.setLocation(jLabel2.getX()+jLabel2.getWidth()+10,jLabel2.getY());
        panel.add(jTextField2);

        JLabel jLabel3 = new JLabel("密码");
        jLabel3.setSize(100,35);
        jLabel3.setFont(new Font("Serif", Font.BOLD, 20));
        jLabel3.setLocation(jLabel2.getX(),jLabel2.getY()+jLabel2.getHeight()+20);
        panel.add(jLabel3);

        JTextField jTextField3 = createJTextField();
        jTextField3.setLocation(jLabel3.getX()+jLabel3.getWidth()+10,jLabel3.getY());
        panel.add(jTextField3);

        JLabel jLabel4 = new JLabel("再次输入");
        jLabel4.setSize(100,35);
        jLabel4.setFont(new Font("Serif", Font.BOLD, 20));
        jLabel4.setLocation(jLabel3.getX(),jLabel3.getY()+jLabel3.getHeight()+20);
        panel.add(jLabel4);

        JTextField jTextField4 = createJTextField();
        jTextField4.setLocation(jLabel4.getX()+jLabel4.getWidth()+10,jLabel4.getY());
        panel.add(jTextField4);

        JLabel jLabel5 = new JLabel("联系电话");
        jLabel5.setSize(100,35);
        jLabel5.setFont(new Font("Serif", Font.BOLD, 20));
        jLabel5.setLocation(jLabel4.getX(),jLabel4.getY()+jLabel4.getHeight()+20);
        panel.add(jLabel5);

        JTextField jTextField5 = createJTextField();
        jTextField5.setLocation(jLabel5.getX()+jLabel5.getWidth()+10,jLabel5.getY());
        panel.add(jTextField5);

        JLabel jLabel6 = new JLabel("两次密码不相同");
        jLabel6.setFont(new Font("Serif", Font.BOLD, 15));
        jLabel6.setSize(200,25);
        jLabel6.setForeground(Color.red);
        jLabel6.setLocation(jTextField4.getX(),jTextField4.getY()+jTextField4.getHeight());
        jLabel6.setVisible(false);
        panel.add(jLabel6);

        JButton jButton = new JButton("注册");
        jButton.setSize(310,40);
        jButton.setFont(new Font("Serif", Font.BOLD, 20));
        jButton.setLocation(jLabel5.getX(),jLabel5.getY()+jLabel5.getHeight()+30);
        panel.add(jButton);

        JLabel jLabel7 = new JLabel("账号已存在");
        jLabel7.setFont(new Font("Serif", Font.BOLD, 15));
        jLabel7.setSize(200,25);
        jLabel7.setForeground(Color.red);
        jLabel7.setLocation(jButton.getX(),jButton.getY()+jButton.getHeight()+2);
        jLabel7.setVisible(false);
        panel.add(jLabel7);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(jTextField1.getText().replaceAll(" ","").equalsIgnoreCase("")||
                    jTextField2.getText().replaceAll(" ","").equalsIgnoreCase("")||
                    jTextField3.getText().replaceAll(" ","").equalsIgnoreCase("")||
                    jTextField4.getText().replaceAll(" ","").equalsIgnoreCase("")||
                    jTextField5.getText().replaceAll(" ","").equalsIgnoreCase("")){
                        jLabel7.setText("所有输入框不能为空");
                        jLabel7.setVisible(true);
                        return;
                    }
                    jLabel7.setText("账号已存在");
                    jLabel7.setVisible(false);
                    if(!jTextField3.getText().equalsIgnoreCase(jTextField4.getText())){
                        jLabel6.setVisible(true);
                        return;
                    }
                    jLabel6.setVisible(false);
                    String temp = ClientTask.register(jTextField1.getText()+"##"+jTextField2.getText()+
                            "##"+jTextField3.getText()+"##"+jTextField5.getText()).get(0);
                    if(temp.equalsIgnoreCase("true")){
                        dialog.dispose();
                    }else{
                        jLabel7.setVisible(true);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        // 设置对话框的内容面板
        dialog.setContentPane(panel);
        // 显示对话框
        dialog.setVisible(true);
    }

    //客户端主界面
    private void mainInterface(){
        setVisible(false);
        setLayout(new BorderLayout());
        setSize(1200, 700);
        setResizable(true);

        //NORTH框
        JPanel jp2 = new JPanel(new BorderLayout());
        jp2.setBackground(Color.DARK_GRAY);
        jp2.setPreferredSize(new Dimension(0, 40));
        add(jp2, BorderLayout.NORTH);

        //CENTER框（显示内容）
        JP1 jp31 = new JP1();
        JPanel jp32 = new JPanel(new BorderLayout());
        JPanel jp33 = new JPanel(new CardLayout());
        JPanel jp34 = new JPanel(new BorderLayout());

        jp33.add(new JP4(jp33));

        CardLayout layout = new CardLayout();
        JPanel jPanel = new JPanel(layout);

        jp31.setBackground(new Color(245,245,245));
        jp32.setBackground(new Color(255,255,255));
        jp33.setBackground(Color.WHITE);
        jp34.setBackground(Color.WHITE);

        jPanel.add(jp31);
        jPanel.add(jp32);
        jPanel.add(jp33);
        jPanel.add(jp34);
        add(jPanel,BorderLayout.CENTER);

        //WEST框（按钮面板）
        JPanel jp = new JPanel(new BorderLayout());
        jp.setBackground(Color.LIGHT_GRAY);
        jp.setPreferredSize(new Dimension(170, 0));
        ButtonInterface buttonInterface = new ButtonInterface(jp31,jp32,jp33,jp34);
        jp.add(buttonInterface);
        add(jp, BorderLayout.WEST);

        setVisible(true);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new ClientInterface();
    }
}
package com.lfh;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerTask implements Runnable {
    private final Socket socket;//本线程创建一个socket

    public ServerTask(Socket socket) {
        this.socket = socket;
    }//将本线程的socket设为Client连接过来的socket

    @Override
    public void run() {
        try {
            //获取输入字节流(即客户端发送来的字节流)
            InputStream is = socket.getInputStream();
            //字节流转换为字符流
            InputStreamReader isReader = new InputStreamReader(is, StandardCharsets.UTF_8);
            //字符流转换为缓冲字符流
            BufferedReader reader = new BufferedReader(isReader);

            //获取输出字符流(即回复给客户端的消息)
            OutputStream os = socket.getOutputStream();
            PrintStream out = new PrintStream(os, true, "UTF-8");

            InetAddress clientIP = socket.getInetAddress();//获取客户端IP
            int clientPort = socket.getPort();//获取客户端端口

            out.println("连接成功");
            String msg = reader.readLine();//接收请求
            System.out.println("与客户端" + clientIP + ":" + clientPort + "连接并收到一个请求：" + msg);//打印请求

            //获取所需服务号
            String[] strings=msg.split("~");//分割字符串，提取服务号的字符串形式
            int request = Integer.parseInt(strings[0]);//字符串转整数

            //返回信息
            String response = "null";

            //根据服务号来进行相应服务
            switch (request){
                //注册
                case 0: {
                    String[] registerText = strings[1].split("##");//分割注册信息
                    //判断该账号是否已被注册
                    if (registerScreen(registerText[1])) {
                        writeFile(strings[1], "用户信息.txt");
                        response = "true";//注册成功
                    } else response = "false";//注册失败

                    break;
                }
                //登录
                case 1:{
                    String[] signInText=strings[1].split("##");//分割登录信息
                    //判断账号密码是否存在且正确
                    if (signInScreen(signInText[0],signInText[1])){
                        response="true";
                    }
                    else response="false";

                    break;
                }
                //发布Lost信息
                case 2: {
                    long timeMillis1 = System.currentTimeMillis();//信息发布时间
                    if (writeFile(strings[1] + timeMillis1, "Lost物品信息.txt")) {
                        response = "true";
                    } else response = "false";

                    break;
                }
                //发布Found信息
                case 3: {
                    long timeMillis2 = System.currentTimeMillis();//信息发布时间
                    if (writeFile(strings[1] + timeMillis2, "Found物品信息.txt")) {
                        response = "true";
                    } else response = "false";

                    break;
                }
                //回传Lost信息、Found信息、检索信息
                case 4:
                case 5:
                case 6: {
                    String[] requirements = strings[1].split("##");//分割检索字符串
                    String s = requirements[0].split("//")[1];//获取文本名
                    String filePath = "C:\\Users\\ying1\\Desktop\\Server\\" + s + "物品信息.txt";//获取文本路径

                    //生成检索要求数组
                    String[] screens = new String[3];
                    System.arraycopy(requirements, 1, screens, 0, requirements.length - 1);
                    //获取检索所得信息
                    response = readFile(filePath, screens);

                    break;
                }
                //服务失败
                default: {
                    System.out.println("服务失败");
                    break;
                }
            }

            out.println(response);//把数据传送去客户端
            out.println("over");//通知客户端服务成功
            System.out.println("服务成功");
            socket.close();//断开连接

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //登录信息筛选
    private static boolean signInScreen(String account, String password) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\ying1\\Desktop\\Server\\用户信息.txt"));//创建缓冲读取流
        String line;
        while ((line=reader.readLine())!=null){
            String[] splits = line.split("##");
            //账号密码比对,若有该账号和密码则返回true
            if (splits.length>1&&splits[1].equals(account)&&splits[2].equals(password)){
                return true;
            }
        }
        return false;
    }

    //注册信息筛选
    private static boolean registerScreen(String account) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\ying1\\Desktop\\Server\\用户信息.txt"));//创建缓冲读取流
        String line;
        while ((line=reader.readLine())!=null){
            String[] splits = line.split("##");
            //账号比对,若有该账号则返回false
            if (splits.length>1&&splits[1].equals(account)){
                return false;
            }
        }
        return true;
    }

    //写信息入File
    private static boolean writeFile(String line, String name) throws IOException {
        File file=new File("C:\\Users\\ying1\\Desktop\\Server\\"+name);
        FileOutputStream fos=new FileOutputStream(file,true);//创建一个不会覆盖文本的写入流
        byte[] buff;
        buff=line.getBytes();//读取写入信息到一个buff缓冲区
        fos.write(buff);//写入信息
        fos.write("\n".getBytes());//写入换行
        fos.flush();//刷新
        fos.close();//关闭写入流
        return true;
    }

    //读File获取信息（带筛选）
    private static String readFile(String name,String[] screens) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(name));//创建缓冲读取流
        StringBuilder stringBuilder = new StringBuilder();//字符串拼接器
        String line;
        String ls = System.getProperty("line.separator");//获取该文本的换行符
        int count=0;
        while ((line = reader.readLine()) != null) {
            String[] temp=line.split("##");

            //满20条信息时,退出循环
            if (count==20){
                break;
            }

            long searchTime=Long.parseLong(temp[6].split("//")[1]);//信息发布时间
            long screenTime=Long.parseLong(screens[1].split("//")[1]);//要求检索的时间
            String screen1=screens[0].split("//")[1];//要求检索的物品类型
            String screen2=screens[2].split("//")[1];//要求检索的主要颜色
            //添加信息
            if (screenTime<searchTime) {
                if (screen1.equals("null") && screen2.equals("null")) {
                    //拼接每一行的字符串和换行符
                    stringBuilder.append(line);
                    stringBuilder.append(ls);
                    count++;
                }
                else if (screen1.equals("null") ) {
                    if (screens[2].equals(temp[2])){
                        stringBuilder.append(line);
                        stringBuilder.append(ls);
                        count++;
                    }
                }
                else if ( screen2.equals("null")){
                    if (screens[0].equals(temp[0])){
                        stringBuilder.append(line);
                        stringBuilder.append(ls);
                        count++;
                    }
                }
                else {
                    if (screens[2].equals(temp[2])&&screens[0].equals(temp[0])){
                        stringBuilder.append(line);
                        stringBuilder.append(ls);
                        count++;
                    }
                }
            }
        }
        //若无符合的信息则返回null字符串
        if (stringBuilder.length()==0){
            return "null";
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);//删除最后一行的换行符'\n'
        reader.close();

        return stringBuilder.toString();//返回所要传输的字符串
    }
}
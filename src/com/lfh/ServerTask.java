package com.lfh;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerTask implements Runnable{
    private final Socket socket;//本线程创建一个socket

    public ServerTask(Socket socket) {
        this.socket = socket;
    }//将本线程的socket设为Client连接过来的socket

    @Override
    public void run() {
        try {
            //获取输入字节流(即客户端发送来的字节流)
            InputStream is=socket.getInputStream();
            //字节流转换为字符流
            InputStreamReader isReader=new InputStreamReader(is, StandardCharsets.UTF_8);
            //字符流转换为缓冲字符流
            BufferedReader reader=new BufferedReader(isReader);

            //获取输出字符流(即回复给客户端的消息)
            OutputStream os=socket.getOutputStream();
            PrintStream out =new PrintStream(os,true,"UTF-8");

            InetAddress clientIP=socket.getInetAddress();//获取客户端IP
            int clientPort=socket.getPort();//获取客户端端口

            out.println("连接成功");
            String msg=reader.readLine();//接收请求
            System.out.println("与客户端"+clientIP+":"+clientPort+"连接并收到一个请求："+msg);//打印请求

            //获取所需服务号
            String s = msg.split("~")[0];//分割字符串，提取服务号的字符串形式
            int request = Integer.parseInt(s);//字符串转整数

            //根据服务号来进行相应服务
            switch (request){
                case 0://留作注册
                case 1://留作登录
                    break;
                case 2://发布Lost信息
                    writeLostInformation(msg.split("~")[1]);//写入信息
                    out.println("over");//通知客户端服务成功
                    System.out.println("服务成功");
                    break;
                case 3://发布Found信息
                    writeFoundInformation(msg.split("~")[1]);//写入信息
                    out.println("over");//通知客户端服务成功
                    System.out.println("服务成功");
                    break;
                case 4://回传Lost信息
                    //文本路径请与写入的文本路径一致
                    String response1 = readFile("C:\\Users\\ying1\\Desktop\\Server\\Lost物品信息.txt");//获取Lost物品信息文本的数据
                    out.println(response1);//把数据传送去客户端
                    out.println("over");//通知客户端服务成功
                    System.out.println("服务成功");
                    break;
                case 5://回传Found信息
                    //文本路径请与写入的文本路径一致
                    String response2 = readFile("C:\\Users\\ying1\\Desktop\\Server\\Found物品信息.txt");//获取Lost物品信息文本的数据
                    out.println(response2);//把数据传送去客户端
                    out.println("over");//通知客户端服务成功
                    System.out.println("服务成功");
                    break;
                case 6://留作回传检索信息
                    break;
                default:
                    System.out.println("服务失败");
                    break;
            }

            socket.close();//断开连接

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //写入Lost物品信息
    private static void writeLostInformation(String line) throws IOException {
        //路径自行选择
        File file=new File("C:\\Users\\ying1\\Desktop\\Server\\Lost物品信息.txt");
        writeFile(line, file);
    }

    //写入Found物品信息
    private static void writeFoundInformation(String line) throws IOException {
        //路径自行选择
        File file=new File("C:\\Users\\ying1\\Desktop\\Server\\Found物品信息.txt");
        writeFile(line, file);
    }

    //写信息入File
    private static void writeFile(String line, File file) throws IOException {
        FileOutputStream fos=new FileOutputStream(file,true);//创建一个不会覆盖文本的写入流
        byte[] buff;
        buff=line.getBytes();//读取写入信息到一个buff缓冲区
        fos.write(buff);//写入信息
        fos.write("\n".getBytes());//写入换行
        fos.flush();//刷新
        fos.close();//关闭写入流
    }

    //从File中读信息
    private static String readFile(String name) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(name));//创建缓冲读取流
        StringBuilder stringBuilder = new StringBuilder();//字符串拼接器
        String line;
        String ls = System.getProperty("line.separator");//获取该文本的换行符
        while ((line = reader.readLine()) != null) {
            //拼接每一行的字符串和换行符
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);//删除最后一行的换行符
        reader.close();

        return stringBuilder.toString();//返回所要传输的字符串
    }
}

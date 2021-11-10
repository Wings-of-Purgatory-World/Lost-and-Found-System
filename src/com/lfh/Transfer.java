package com.lfh;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Transfer {
    //传送客户端信息，没有服务端信息接收的方法
    public static boolean transferNoResponse(String message) throws IOException {
        Socket clientSocket=new Socket();//创建一个套接字用于TCP连接
        InetAddress serverIP=InetAddress.getByName("127.0.0.1");//获取服务器的IP地址

        //连接服务器
        int serverPort=8080;//服务器端口号
        SocketAddress serverIPAndPort =new InetSocketAddress(serverIP,serverPort);//服务器ip+端口
        clientSocket.connect(serverIPAndPort);//Client与Server建立连接

        //通过字节流,直接写入信息
        OutputStream os=clientSocket.getOutputStream();
        PrintStream out =new PrintStream(os,true,"UTF-8");//自动刷新信息

        //通过字节流,直接读取数据
        InputStream is=clientSocket.getInputStream();//从该套接字获取服务器回复的信息
        BufferedReader reader=new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

        boolean mark1=true;
        while (mark1){
            String response= reader.readLine();
            if (response.equals("连接成功")){
                mark1=false;
            }
        }

        out.println(message);//发送请求

        boolean mark2=true;
        while (mark2){
            String response= reader.readLine();
            if (response.equals("over")){
                mark2=false;
            }
        }

        clientSocket.close();
        return true;
    }

    //传送客户端信息，有服务端信息接收的方法
    public static boolean transferAndResponse(String message) throws IOException {
        //文件目录请自行修改
        File file=new File("C:\\Users\\ying1\\Desktop\\Client\\返回信息.txt");//在客户端建立一个文本获取Server的返回信息

        Socket clientSocket=new Socket();//创建一个套接字用于TCP连接
        InetAddress serverIP=InetAddress.getByName("127.0.0.1");//获取服务器的IP地址

        //连接服务器
        int serverPort=8080;//服务器端口号
        SocketAddress serverIPAndPort =new InetSocketAddress(serverIP,serverPort);//服务器ip+端口
        clientSocket.connect(serverIPAndPort);//Client与Server建立连接

        //通过字节流,直接写入信息
        OutputStream os=clientSocket.getOutputStream();
        PrintStream out =new PrintStream(os,true,"UTF-8");//自动刷新信息

        //通过字节流,直接读取数据
        InputStream is=clientSocket.getInputStream();//从该套接字获取服务器回复的信息
        BufferedReader reader=new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

        boolean mark1=true;
        while (mark1){
            String response= reader.readLine();
            if (response.equals("连接成功")){
                mark1=false;
            }
        }

        out.println(message);//发送请求

        //接收返回信息
        String line;
        FileOutputStream fos=new FileOutputStream(file);//创建一个写入流
        while (!(line=reader.readLine()).equals("over")){
            byte[] buff;
            buff=line.getBytes();//读取写入信息到一个buff缓冲区
            fos.write(buff);//写入信息
            fos.write("\n".getBytes());//写入换行
            fos.flush();//刷新
        }

        clientSocket.close();

        return true;
    }
}

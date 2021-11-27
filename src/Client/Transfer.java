package com.客户端;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Transfer {

    //传送客户端信息，返回ArrayList数组,mark为true
    public static ArrayList<String> transfer(String message,boolean mark) throws IOException {

        Socket clientSocket=new Socket();//创建一个套接字用于TCP连接
        InetAddress serverIP=InetAddress.getByName("192.168.1.103");//获取服务器的IP地址

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

        ArrayList<String> arrayList=new ArrayList<>();
        //标记为true时，往ArrayList数组中加入服务器数据
        if (mark){
            //接收返回信息
            String line;
            //接收到over时停止接收信息
            while (!(line=reader.readLine()).equals("over")){
                arrayList.add(line);
            }
        }
        else {
            arrayList.add("null");
        }

        clientSocket.close();//关闭socket

        //如无服务器返回的数据则返回null
        if (arrayList.get(0).equals("null")){
            return null;
        }
        else return arrayList;
    }

}

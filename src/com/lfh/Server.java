package com.lfh;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("服务器已启动，等待客户端请求......");
        //创建线程池，大小为10
        ExecutorService pool= Executors.newFixedThreadPool(10);
        //服务器起一个端口
        ServerSocket serverSocket=new ServerSocket(8080);

        while (true){
            Socket socket = serverSocket.accept();//接收客户端请求，返回一个socket供通信
            pool.execute(new ServerTask(socket));//把任务提交至线程池
        }
    }
}

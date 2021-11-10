package com.lfh;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        //获取当前时间
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String dateStringParse = sdf.format(date);

        Scanner sc=new Scanner(System.in);
        boolean mark=true;
        System.out.println("欢迎登录失物招领系统");

        while (mark){
            System.out.println("输入0可进行寻找失物信息发布,输入1可进行寻找失主信息发布,输入2可获取寻找失物信息,输入3可获取寻找失主信息,输入4可退出本软件");
            System.out.print("请输入您所需要选择的功能的编号：");
            int choose = sc.nextInt();
            choose+=2;
            switch (choose){
                case 2:
                    ClientTask.lostPublish(dateStringParse);//发布Lost信息
                    break;
                case 3:
                    ClientTask.foundPublish(dateStringParse);//发布Found信息
                    break;
                case 4:
                    ClientTask.acquireLostInformation();//下载Server的所有Lost信息
                    break;
                case 5:
                    ClientTask.acquireFoundInformation();//下载Server的所有的Found信息
                    break;
                case 6:
                    mark=false;
                    System.out.println("谢谢您的使用，欢迎您下次再次使用本软件");
                    break;
            }
        }

    }
}

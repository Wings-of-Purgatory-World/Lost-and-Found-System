package com.lfh;

import java.io.*;

public class ClientTask {

    //注册方法
    public static void register(){

    }

    //登录方法
    public static void signIn(){

    }

    //寻找失物发布方法
    public static void lostPublish(String dateStringParse) throws IOException {
        Transfer.transferNoResponse("2~情况：Lost 物品信息：手机 颜色：红色 丢失地点：教二 发布时间："+dateStringParse);
    }

    //寻找失主发布方法
    public static void foundPublish(String dateStringParse) throws IOException {
        Transfer.transferNoResponse("3~情况：Found 物品信息：手机 颜色：红色 丢失地点：教二 发布时间："+dateStringParse);
    }

    //获取所有Lost信息的方法
    public static void acquireLostInformation() throws IOException {
        Transfer.transferAndResponse("4~ ");
    }

    //获取所有Found信息的方法
    public static void acquireFoundInformation() throws IOException {
        Transfer.transferAndResponse("5~ ");
    }

    //获取检索信息的方法
    public static void acquireSearchInformation(){

    }
}

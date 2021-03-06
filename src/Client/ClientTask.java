package com.客户端;

import java.io.IOException;
import java.util.ArrayList;

public class ClientTask {

    //注册方法
        /*
        输入String："郑世杰##72126471##liufuhua##15819293595"
        输出ArrayList<String>：ArrayList[0]为"true"或"false"
        */
    public static ArrayList<String> register(String registerLine) throws IOException {
        return Transfer.transfer("0~"+registerLine,true);
    }

    //登录方法
        /*
        输入String："984427926##liufuhua"
        输出ArrayList<String>：ArrayList[0]为"true"或"false"
        */
    public static ArrayList<String> signIn(String signInLine) throws IOException {
        return  Transfer.transfer("1~"+signInLine,true);
    }

    //寻找失物发布方法
         /*
        输入String："物品类型//电子产品##物品名称//手机##主要颜色//红色##描述信息//这个东西很棒##地点//教二##联系方式//984427926##发布时间//"
        输出ArrayList<String>：ArrayList[0]为"true"或"false"
        */
    public static ArrayList<String> lostPublish(String information) throws IOException {
        return Transfer.transfer("2~"+information,true);
    }

    //寻找失主发布方法
        /*
        输入String："物品类型//包##物品名称//书包##主要颜色//蓝色##描述信息//还不错##地点//教一##联系方式//15819293595##发布时间//"
        输出ArrayList<String>：ArrayList[0]为"true"或"false"
        */
    public static ArrayList<String> foundPublish(String information) throws IOException {
        return Transfer.transfer("3~"+information,true);
    }

    //获取所有Lost信息的方法
         /*
        输入String："时间毫秒"
        若有返回信息，输出ArrayList<String>
        若无返回信息，输出null
         */
    public static ArrayList<String> acquireLostInformation(String time) throws IOException {
        return Transfer.transfer("4~类型//Lost##物品类型//null##发布时间//"+time+"##主要颜色//null",true);
    }

    //获取所有Found信息的方法
        /*
        输入String："时间毫秒"
        若有返回信息，输出ArrayList<String>
        若无返回信息，输出null
        */
    public static ArrayList<String> acquireFoundInformation(String time) throws IOException {
        return Transfer.transfer("5~类型//Found##物品类型//null##发布时间//"+time+"##主要颜色//null",true);
    }

    //获取检索信息的方法
        /*
        输入String："类型//待输入##物品类型//待输入##发布时间//时间毫秒##主要颜色//待输入"
        输入例子：
        "类型//Lost##物品类型//电子产品##发布时间//1637559606077##主要颜色//null"
        "类型//Found##物品类型//电子产品##发布时间//1637559606077##主要颜色//null"
        "类型//Lost##物品类型//无##发布时间//1637559606077##主要颜色//null"
        "类型//Lost##物品类型//电子产品##发布时间//1637559606077##主要颜色//红色"
        若有返回信息，输出ArrayList<String>
        若无返回信息，输出null
        */
    public static ArrayList<String> acquireSearchInformation(String requirement) throws IOException {
        return Transfer.transfer("6~"+requirement,true);
    }
}
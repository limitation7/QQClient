package QQ.service;

import java.util.HashMap;

/*
* 该类管理客户端链接到服务端的线程的类*/
public class ManageClientConnectServerThread {
    //把多个线程放入到HashMap集合,key是用户id，value是线程
    private static HashMap<String,ClientConnectServerThread> hm=new HashMap<>();

    //将某个线程加入到集合中
    public static void addClientConnectServerThread(String userId,ClientConnectServerThread c){
        hm.put(userId,c);
    }
    // 通过userId 可以得到对应的线程
    public static ClientConnectServerThread getClientConnectServerThread(String userId){
        return hm.get(userId);
    }


}

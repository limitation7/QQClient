package QQ.service;

import QQ.qqcommon.Message;
import QQ.qqcommon.MessageType;
import QQ.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class UserClientService {

    private User u=new User();
    private Socket socket;
    //根据userId和pwd到服务器验证该用户是否合法
    public boolean checkUser(String userId,String pwd) {
        boolean b=false;
        //创建user对象
        u.setUserId(userId);
        u.setPwd(pwd);
        //连接到服务端发送u对象
        try {
            Socket socket = new Socket(InetAddress.getByName("127.0.0.1"),9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);//发送user对象
            //读取从服务器回复的message对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms=(Message) ois.readObject();

            if(ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){

                //创建一个和服务端保持通信的线程 ->创建一个类 ClientConnectServerThread
                //等待
                ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);
                clientConnectServerThread.start();
                //这里为了后面的扩展，将线程放到集合管理
                ManageClientConnectServerThread.addClientConnectServerThread(userId,clientConnectServerThread);
                b=true;
            }else{
                //登陆失败,不能启动和服务器通讯的线程
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;

    }
    //向服务器端请求在线用户列表
    public void onLineFriendList(){
        //发送一个Message,类型MESSAGE_GET_ONLINE_FRIEND
        Message message=new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(u.getUserId());
        //发送给服务器
        //应该得到当前线程的socketObjectOutputStream
        try{
            //从userID得到这个线程
            ClientConnectServerThread clientConnectServerThread=ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId());
            Socket socket=clientConnectServerThread.getSocket();
            //得到当前线程的Socket对应的objectOutputStream对象
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);//向服务器要求在线用户列表

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void logout(){
        Message message=new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getUserId());//一定要指定是哪个客户端id

        //发送message
        try {
            //ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectOutputStream oos = new
                    ObjectOutputStream(
                            ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
            System.out.println(u.getUserId()+"退出系统");
            System.exit(0);//退出系统
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

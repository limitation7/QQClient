package QQ.service;

import QQ.qqcommon.Message;
import QQ.qqcommon.MessageType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread{
    //该线程需要持有Socket
    private Socket socket;
    //构造器可以接受一个socket对象
    public ClientConnectServerThread(Socket socket){
        this.socket=socket;
    }
    //
    @Override
    public void run() {
        //因为线程需要在后台和服务器通讯，因此做成无限循环
        while (true){

//            System.out.println("客户端线程，等待读取从服务端发送的消息");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message=(Message)ois.readObject();//如果服务器没有发送Message对象线程会阻塞在这里
                //判断message类型，然后做处理
                //如果读取到的是服务端返回的在线用户列表
                if(message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)){
                    //取出在线列表信息并显示
                    String[] onlineUsers=message.getContent().split(" ");
                    System.out.println("=========当前在线列表如下=========");
                    for(String i:onlineUsers){
                        System.out.println("用户："+i);
                    }
                }else if(message.getMesType().equals(MessageType.MESSAGE_COMM_MES)){
                    //普通聊天消息，把服务端转发的消息显示到控制台
                    System.out.println("\n"+message.getSender()+"对"+message.getGetter()+" 说： "+message.getContent());
                }else if(message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)){
                    System.out.println("\n"+message.getSender()+"对大家说"+message.getContent());
                }else if(message.getMesType().equals(MessageType.MESSAGE_FILE_MES))
                {
                    System.out.println("\n"+message.getSender()+" 给 "+message.getGetter()+ "发送文件"
                    +message.getSrc()+" 到我的电脑的" +message.getDest());
                    byte[] bytes=message.getFileBytes();
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDest());
                    fileOutputStream.write(bytes);
                    fileOutputStream.close();
                    System.out.println("\n 保存文件成功");
                }
                else {{
                    System.out.println("其他类型的message暂时不处理");
                }}

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //为了更方便的得到socket 提供get方法
    public Socket getSocket() {
        return socket;
    }
}

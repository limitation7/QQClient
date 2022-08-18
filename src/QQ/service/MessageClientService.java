package QQ.service;

import QQ.qqcommon.Message;
import QQ.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class MessageClientService {
    //该对象提供和消息相关的服务方法
    //content 内容
    //senderId 发送用户Id
    //getterId 接收用户Id
    public void sendMessageToAll(String content,String senderId){
        Message message=new Message();
        message.setSender(senderId);
        message.setContent(content);
        message.setMesType(MessageType.MESSAGE_TO_ALL_MES);//群发消息类型
        message.setSendTime(new Date().toString());//发送时间设置到mess对象
        System.out.println(senderId+" 对大家说 "+content);
        //发送给服务端
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMessageToOne(String content,String senderId,String getterId){
        Message message=new Message();
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setContent(content);
        message.setMesType(MessageType.MESSAGE_COMM_MES);
        message.setSendTime(new Date().toString());//发送时间设置到mess对象
        System.out.println(senderId+" 对 "+getterId+" 说 "+content);
        //发送给服务端
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

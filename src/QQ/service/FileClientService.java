package QQ.service;

import QQ.qqcommon.Message;
import QQ.qqcommon.MessageType;

import java.io.*;

public class FileClientService {
    //该类文件传输
    public void sendFileToOne(String src,String dest,String senderId,String getterId){
        //src源文件
        //dest把文件传输到对方哪个目录
        //sender发送用户id
        //getterId接收文件id
        Message message=new Message();
        message.setMesType(MessageType.MESSAGE_FILE_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setSrc(src);
        message.setDest(dest);

        //需要将文件读取
        FileInputStream fileInputStream=null;
        byte[] fileBytes=new byte[(int)new File(src).length()];
        try {
            fileInputStream =new FileInputStream(src);
            fileInputStream.read(fileBytes);//将src文件读入到程序的字节数组
            //将文件对应的直接数组设置到message对象
            message.setFileBytes(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("\n"+getterId+" 给 "+ getterId+" 发送文件："+src+"到对方目录"+dest);

        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

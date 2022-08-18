package QQ.view;

import QQ.service.FileClientService;
import QQ.service.MessageClientService;
import QQ.service.UserClientService;
import QQ.utils.Utility;

public class QQView {
    private boolean loop=true;
    private String key="";//接受用户键盘输入
    private UserClientService userClientService=new UserClientService();//对象是用于登陆服务器注册用户
    private MessageClientService messageClientService =new MessageClientService();//对象用户私聊，群聊
    private FileClientService fileClientService=new FileClientService();
    public static void main(String[] args) {
        new QQView().mainMenu();
        System.out.println("客户端退出系统");
    }
    //显示主菜单
    private void mainMenu(){
        while (loop){
            System.out.println("========欢迎登陆=========");
            System.out.println("\t\t1登陆系统");
            System.out.println("\t\t2退出系统");
            System.out.println("输入选择");
            key= Utility.readString(1);
            switch (key){
                case"1":
                    System.out.println("请输入用户号:");
                    String userId=Utility.readString(50);
                    System.out.println("请输入密码:");
                    String pwd=Utility.readString(50);
                    //验证用户是否合法
                    if(userClientService.checkUser(userId,pwd)){//还没有写完
                        System.out.println("========欢迎"+userId+"=========");
                        //进入二级菜单
                        while (loop){
                            System.out.println("\n======网络通信系统二级菜单"+userId+"=======");
                            System.out.println("\t\t 1显示在线用户列表");
                            System.out.println("\t\t 2群发消息");
                            System.out.println("\t\t 3私发消息");
                            System.out.println("\t\t 4发送文件");
                            System.out.println("\t\t 9退出系统");
                            System.out.println("请输入选择");
                            key=Utility.readString(1);
                            switch (key){
                                case "1":
                                    //写一个方法获取在线列表
                                    userClientService.onLineFriendList();
                                    break;
                                case "2":
                                    System.out.println("请输入想堆大家说的话");
                                    String s = Utility.readString(100);
                                    messageClientService.sendMessageToAll(s,userId);
                                    //调用一个方法，将消息封装成message对象发送给服务端
                                    break;
                                case "3":
                                    System.out.println("请输入要想聊天的用户号(在线):");
                                    String getterId=Utility.readString(50);
                                    System.out.print("请输入想说的话：");
                                    String content = Utility.readString(100);
                                    //编写一个方法将消息发送给服务端
                                    messageClientService.sendMessageToOne(content,userId,getterId);
                                    break;
                                case "4":
                                    System.out.print("请输入你想发送的文件的用户(在线)");
                                    getterId=Utility.readString(50);
                                    System.out.print("请输入发送文件的路径");
                                    String src=Utility.readString(100);
                                    System.out.print("请输入发送到对方的路径");
                                    String dest=Utility.readString(100);
                                    fileClientService.sendFileToOne(src,dest,userId,getterId);
                                    break;
                                case "9":
                                    loop=false;
                                    System.out.println("客户端退出");
                                    userClientService.logout();
                                    break;
                            }
                        }
                    }
                    else {//登陆服务器失败
                        System.out.println("========登陆失败=========");
                    }
                    break;
                case"9":
                    loop=false;
                    break;
            }
        }
    }
}

package t1;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class client  {
    public static void main(String[] args) throws Exception {
        Socket s=new Socket("127.0.0.1",9999);
        pack p=new pack();
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入要登陆的账户");
        p.setId(sc.nextLine());
        if(check(s,p)){
            serverReturnClientThread serverReturnClientThread =new serverReturnClientThread(s);
            serverReturnClientThread.start();
            System.out.println("输入exit退出系统");
            ObjectOutputStream oos=null;
            while(true){
                String str=sc.nextLine();
                oos=new ObjectOutputStream(s.getOutputStream());
                if(str.equals("exit")){
                    p.setId(p.getId());
                    p.setStr(str);
                    p.setServerUser(true);
                    oos.writeObject(p);
                    //TimeUnit.MINUTES.sleep(3);
                    System.exit(0);
                    break;
                }
                else {
                    p.setId(p.getId());
                    p.setStr(str);
                    p.setServerUser(true);
                    oos.writeObject(p);
                }

            }
        }
        else {

        }
    }
    public static boolean check(Socket s,pack p) throws Exception {

        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(p);
        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
        pack o = (pack)ois.readObject();
        return o.isServerUser();

    }
}

package t1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class serverReturnClientThread extends Thread{
    private Socket socket;
    public serverReturnClientThread(Socket s){
        this.socket=s;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                pack o = (pack) ois.readObject();
                System.out.println(o.getId()+":"+o.getStr());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

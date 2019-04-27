package cn.tedu.shoot;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler {

    Socket socket = null;

    public ClientHandler(){
        try {
            System.out.println("正在连接服务端");
            socket = new Socket("127.0.0.1",8080);
            System.out.println("服务端连接成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    OutputStream os = null;

    class ThreadHandler implements Runnable{
        @Override
        public void run() {
            try {
                os = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] datas = new byte[1024];
            int len = -1;
            while(true){
                if(datas!=null){
                    try {
                        os.write(datas);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}


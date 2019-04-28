package cn.tedu.shoot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    ServerSocket server;
    Socket socket;
    OutputStream os;

    {
        try {
            server = new ServerSocket(8080);
            socket = server.accept();
            input();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void input() {
        try {
            InputStream is = socket.getInputStream();
            Scanner scan = new Scanner(is);
            os = socket.getOutputStream();
            byte[] datas = new byte[100];
            int len = -1;
            while ((len = is.read()) != -1) {
                os.write(datas, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

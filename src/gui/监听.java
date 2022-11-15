package gui;

import java.io.*;  
import java.net.*;  
public class 监听  
{  
    public static void oneServer() throws IOException  
    {  
        String receive = null;  
        @SuppressWarnings("resource")  
        ServerSocket server=new ServerSocket(8484);  
        System.out.println("服务器启动成功");  
        Socket socket=server.accept();  
        BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));  
        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));  
        while(true)                                                    //一直等客户端的消息  
        {  
                System.out.println("客户端发来:"+receive);  
                writer.write("服务器接收到了:"+receive+'\n');               //'\n'一定要加  
                writer.flush();  
        }  
//        writer.close();  
//        in.close();  
//        socket.close();  
//        server.close();  
    }  

}  
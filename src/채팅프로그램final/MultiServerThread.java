package ä�����α׷�final;
import java.net.*;

import javax.imageio.ImageIO;

import java.awt.ScrollPane;
import java.awt.image.BufferedImage;
import java.io.*;
public class MultiServerThread implements Runnable{
    private Socket socket;
    private MultiServer ms;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    MyCanvas myCanvas = new MyCanvas();
    ScrollPane sp = new ScrollPane();
	
    public MultiServerThread(MultiServer ms){
        this.ms = ms;
    }
    
    public synchronized void run(){
        boolean isStop = false;
        try{
        	// �޽��� �ۼ���
            socket = ms.getSocket();
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            String message = null;
            while(!isStop){
                message = (String)ois.readObject();
                String[] str = message.split("#");
                if(str[1].equals("exit")){
                    broadCasting(message);
                    isStop = true;
                }else{
                    broadCasting(message);
                }
            }
            
            ms.getList().remove(this);
            System.out.println(socket.getInetAddress()+
                    "���������� �����ϼ̽��ϴ�");
            System.out.println("list size : "+ms.getList().size());
           // �޽��� �ۼ��� end
            
            
        }catch(Exception e){
            ms.getList().remove(this);
            System.out.println(socket.getInetAddress()+
                    "������������ �����ϼ̽��ϴ�");
            System.out.println("list size : "+ms.getList().size());
        }
        
    }
    
    public void broadCasting(String message)throws IOException{
        for(MultiServerThread ct : ms.getList()){
           ct.send(message);
        }
    }
    
    public void send(String message)throws IOException{
        oos.writeObject(message);        
    }
    
}

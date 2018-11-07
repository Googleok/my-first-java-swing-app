package 채팅프로그램final;
import java.net.*;

import javax.imageio.ImageIO;

import java.awt.ScrollPane;
import java.awt.image.BufferedImage;
import java.io.*;
public class MultiServerImgThread implements Runnable{
    private Socket othersImg = null;
    private MultiServer ms;
    DataInputStream imgReciever = null;
	DataOutputStream imgSender = null;
    MyCanvas myCanvas = new MyCanvas();
    ScrollPane sp = new ScrollPane();
	
    public MultiServerImgThread(MultiServer ms){
        this.ms = ms;
    }
    
    public synchronized void run(){
    	boolean isStop = false;
		try {
			othersImg = ms.getSocket();
			imgReciever = new DataInputStream(othersImg.getInputStream());
			imgSender = new DataOutputStream(othersImg.getOutputStream());
			
			while(!isStop) {
				BufferedImage img = ImageIO.read(imgReciever);
				if (img != null) {
					myCanvas.setImage(img);
					myCanvas.repaint();
				}else {
					isStop = true;
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	System.out.println("SERVER : 이미지 수신 쓰레드 종료");
	
    }
    
//    public void broadCasting(String message)throws IOException{
//        for(MultiServerImgThread ct : ms.getList()){
//           ct.send(message);
//        }
//    }
//    
//    public void send(String message)throws IOException{
//        imgSender.writeInt(message);        
//    }
    
}

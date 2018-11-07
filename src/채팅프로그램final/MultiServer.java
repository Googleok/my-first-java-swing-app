package 채팅프로그램final;
import java.io.*;
import java.net.*;
import java.util.*;

public class MultiServer {
	
	private ArrayList<MultiServerThread> list;
	private Socket socket;
	
	public MultiServer()throws IOException{
		list = new ArrayList<MultiServerThread>();
		
		// 서버소켓 
		ServerSocket serverSocket = new ServerSocket(5000);
		// 서버 스레드
		MultiServerThread mst = null;
		// 플래그
		boolean isStop = false;
		
		while(!isStop){
			System.out.println("Server ready...");
			// 서버소켓에서 클라이언트 받기
			socket = serverSocket.accept();
			// 서버스레드 객체 생성 
			mst = new MultiServerThread(this);
			// ArrayList에 스레드 생성한 걸 넣어주기
			list.add(mst);
			// mst 스레드 돌리기
			Thread t = new Thread(mst);
			t.start();
		}
	}
	 
	
	public ArrayList<MultiServerThread>getList(){
		return list;
	}
	
	public Socket getSocket()
	{
		return socket;
	}
	
	public static void main(String arg[])throws IOException{
		new MultiServer();
	}
}

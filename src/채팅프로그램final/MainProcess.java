package 채팅프로그램final;
import java.io.IOException;

public class MainProcess {

	Login2 loginView;
	MultiClient clientView;
	Join2 joinView;

	public static void main(String[] args) {
		MainProcess main = new MainProcess();
		main.loginView = new Login2();
		main.loginView.setMain(main);
	}
	
	public void showFrameConversation() {
		loginView.dispose();
	    clientView = new MultiClient("127.0.0.1","재은");
	    try {
			clientView.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showFrameJoin() {
		loginView.dispose();
		this.joinView = new Join2();
	}
	
	public void showFrameLogin() {
		joinView.dispose();
		this.loginView = new Login2();
	}

}

package 채팅프로그램final;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class Login extends JFrame {

    private MainProcess main;
    
    private JTextField id;
    private JPasswordField pw;
    private JButton btnLogin;
    private JButton btnJoin;
	private boolean bLoginCheck;
	
	public Login() {
		setTitle("로그인");
		setSize(280,150);
		setResizable(false);
		setLocation(800, 450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 판넬 
		JPanel jpan1 = new JPanel();
		LoginPanel(jpan1);
		// 프레임에 판넬 추가
		add(jpan1);
		
		// 화면 사이즈에 맞게 자동 맞춤
		pack();
		
		setVisible(true);
	}
	
	// 로그인판넬
	public void LoginPanel(JPanel jpan1) {
		// 그리드 레이아웃으로 셋
		jpan1.setLayout(new GridLayout(3, 2));
		
		// id 텍스트필드 설정 
		id = new JTextField(15);
		id.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isLoginCheck();
			}
		});
		jpan1.add(new JLabel("ID"));
		jpan1.add(id);

		// 패스워드 패스워드필드 설정
		pw = new JPasswordField(15);
		pw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isLoginCheck();
			}

		});
		jpan1.add(new JLabel("PW"));
		jpan1.add(pw);

		// 로그인 버튼 설정
		btnLogin = new JButton("Login");
		jpan1.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isLoginCheck();
			}
		});
		
		// 회원가입 버튼 설정
		btnJoin = new JButton("Join");
		jpan1.add(btnJoin);
		btnJoin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 메인을 통해 회원가입 창으로...
				dispose();
				new Join();
			}
		});
	}
	
	// 로그인 체크
	public void isLoginCheck() {
			// 로그인 성공이라면 매니저창 뛰우기
			if(joinDB()) {
				main.showFrameConversation();
			}else {
				JOptionPane.showMessageDialog(null, "Failed");
			}
	}
	
	
	Connection conn = null; // DB연결된 상태(세션)을 담은 객체
	PreparedStatement pstm = null; // SQL문을 나타내는 객체
	ResultSet rs = null; // 쿼리문을 날린것에 대한 반환값을 담을 객체
	int n=0;
	public boolean joinDB() {
		boolean endJoin = false;
		try {
			// SQL 문장을 만들고 만약 문장이 질의어(SELECT문)이라면
			// 그 결과를 담을 ResultSet 객체를 준비한 후 실행시킨다.
			String quary = "SELECT USERNM FROM MEMBERS WHERE userid = ? and passwd = ?";
			
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			String userid =id.getText();
			String passwd = pw.getText();
			pstm.setString(1, userid);
			pstm.setString(2, passwd);
			
			rs = pstm.executeQuery();
		
			if(rs.next() == true && userid != null) {
				JOptionPane.showMessageDialog(null, rs.getString("usernm")+"님 환영합니다!");
				endJoin = true;
			}else {
				
			}
		} catch (SQLException sqle) {
			 
			System.out.println("SELECT문에서 예외 발생");
			sqle.printStackTrace();
		} finally {
			//DB 연결을 종료한다.
			try {
				if( pstm != null) {pstm.close();}
				if( conn != null) {conn.close();}
			}catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			
		}
		return endJoin;
	}
	
	// 로그인 참/거짓
	public boolean isLogin() {
		return bLoginCheck;
	}

	
	// mainProcess와 연동
	public void setMain(MainProcess main) {
		this.main = main;
	}
	
	public static void main(String[] args) {
		
	}
	
	

}

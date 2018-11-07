package 채팅프로그램final;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Join extends JFrame {
	 private MainProcess main;
	
	 private JTextField id;
	 private JPasswordField pw;
	 private JTextField name;
	 private JTextField phone;
	 private JTextField email;
	 private JButton btnJoin;
	 private JButton btnBack;
	 
	public Join() {
		setTitle("회원가입");
		setSize(400,700);
		setResizable(false);
		setLocation(800,450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 판넬
		JPanel jpan1 = new JPanel();
		JoinPanel(jpan1);
		// 프레임에 판넬 추가
		add(jpan1);
		// 화면 사이즈에 맞게 자동 맞춤
		pack();
		setVisible(true);
	}
	
	// 회원가입 판넬
	public void JoinPanel(JPanel jpan1) {
		jpan1.setLayout(new GridLayout(7,2));
		id = new JTextField(15);
		id.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		jpan1.add(new JLabel("ID"));
		jpan1.add(id);
		
		pw = new JPasswordField(15);
		jpan1.add(new JLabel("PW"));
		jpan1.add(pw);
		
		pw = new JPasswordField(15);
		jpan1.add(new JLabel("PW"));
		jpan1.add(pw);
		
		name = new JTextField(15);
		name.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		jpan1.add(new JLabel("NAME"));
		jpan1.add(name);
		
		phone = new JTextField(15);
		phone.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		jpan1.add(new JLabel("PHONE"));
		jpan1.add(phone);
		
		email = new JTextField(15);
		jpan1.add(new JLabel("EMAIL"));
		jpan1.add(email);
		
		btnJoin = new JButton("JOIN");
		btnJoin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				joinDB();
			}
		});
		jpan1.add(btnJoin);
		
		btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Login2();
			}
		});
		jpan1.add(btnBack);
	}
	
	// mainProcess와 연동
	public void setMain(MainProcess main) {
		this.main = main;
	}
	
	
	Connection conn = null; // DB연결된 상태(세션)을 담은 객체
	PreparedStatement pstm = null; // SQL문을 나타내는 객체
	ResultSet rs = null; // 쿼리문을 날린것에 대한 반환값을 담을 객체
	int n=0;
	public void joinDB() {
		try {
			// SQL 문장을 만들고 만약 문장이 질의어(SELECT문)이라면
			// 그 결과를 담을 ResultSet 객체를 준비한 후 실행시킨다.
			String quary = "INSERT INTO MEMBERS(USERID, PASSWD, USERNM, PHONE, EMAIL) VALUES(?,?,?,?,?)";
			
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			String userid =id.getText();
			String passwd = pw.getText();
			String usernm = name.getText();
			String number = phone.getText();
			String mail = email.getText();
			pstm.setString(1, userid);
			pstm.setString(2, passwd);
			pstm.setString(3, usernm);
			pstm.setString(4, number);
			pstm.setString(5, mail);
			
			n=pstm.executeUpdate();
			
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
		if(n>0) {
			System.out.println("등록완료");
		}else {
			System.out.println("실패");
		}
	
	}
	
	
	
	
	
	public static void main(String[] args) {
		new Join();
	}

	
	
}

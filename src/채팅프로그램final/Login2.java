package 채팅프로그램final;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login2 extends JFrame {

	private JTextField id;
	private JPasswordField pw;
	private JButton join;
	private JButton search;
	private JButton login;
	private JButton imgbtn;
	private boolean bLoginCheck;

	public Login2() {
		getContentPane().setForeground(Color.WHITE);
		setBackground(Color.WHITE);
		setBounds(100, 100, 490, 600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		panel.setForeground(Color.WHITE);
		panel.setBounds(0, 0, 480, 580);
		getContentPane().add(panel);
		panel.setLayout(null);

		id = new JTextField();
		id.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		id.setBounds(30, 230, 420, 60);
		id.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isLoginCheck();
			}
		});
		panel.add(id);
		id.setColumns(10);

		pw = new JPasswordField();
		pw.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		pw.setBounds(30, 302, 420, 60);
		pw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isLoginCheck();
			}

		});
		panel.add(pw);
		pw.setColumns(10);

		login = new JButton("로그인");
		login.setForeground(new Color(255, 255, 255));
		login.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isLoginCheck();
			}
		});
		login.setBackground(new Color(255, 153, 0));
		login.setBounds(30, 374, 420, 60);
		panel.add(login);

		join = new JButton("회원가입");
		join.setForeground(new Color(255, 255, 255));
		join.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		join.setBackground(new Color(255, 153, 0));
		join.setBounds(30, 446, 110, 30);
		join.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 메인을 통해 회원가입 창으로...
				dispose();
				new Join2();
			}
		});
		panel.add(join);

		search = new JButton("ID/PW찾기");
		search.setForeground(new Color(255, 255, 255));
		search.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		search.setBackground(new Color(255, 153, 0));
		search.setBounds(340, 446, 110, 30);
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose(); // 로그인창 끄기;
				new search();

			}
		});
		panel.add(search);

		imgbtn = new JButton();
		
		ImageIcon icon = new ImageIcon(".\\img\\logo.png");
		imgbtn.setBorderPainted(false);
		imgbtn.setContentAreaFilled(false);
		imgbtn.setIcon(icon);
		imgbtn.setBounds(160, 50, 150, 130);
		

		panel.add(imgbtn);
		setVisible(true);
	}

	public void isLoginCheck() {
		// 로그인 성공이라면 매니저창 뛰우기
		if (joinDB()) {
			main.showFrameConversation();
		} else {
			JOptionPane.showMessageDialog(null, "Failed");
		}
	}

	Connection conn = null; // DB연결된 상태(세션)을 담은 객체
	PreparedStatement pstm = null; // SQL문을 나타내는 객체
	ResultSet rs = null; // 쿼리문을 날린것에 대한 반환값을 담을 객체
	int n = 0;
	private MainProcess main;

	public boolean joinDB() {
		boolean endJoin = false;
		try {
			// SQL 문장을 만들고 만약 문장이 질의어(SELECT문)이라면
			// 그 결과를 담을 ResultSet 객체를 준비한 후 실행시킨다.
			String quary = "SELECT USERNM FROM MEMBERS WHERE userid = ? and passwd = ?";

			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			String userid = id.getText();
			String passwd = pw.getText();
			pstm.setString(1, userid);
			pstm.setString(2, passwd);

			rs = pstm.executeQuery();

			if (rs.next() == true && userid != null) {
				JOptionPane.showMessageDialog(null, rs.getString("usernm") + "님 환영합니다!");
				endJoin = true;
			} else {

			}
		} catch (SQLException sqle) {

			System.out.println("SELECT문에서 예외 발생");
			sqle.printStackTrace();
		} finally {
			// DB 연결을 종료한다.
			try {
				if (pstm != null) {
					pstm.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
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
		new Login2();

	}
}
package ä�����α׷�final;

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
		id.setFont(new Font("���� ���", Font.PLAIN, 30));
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
		pw.setFont(new Font("���� ���", Font.PLAIN, 30));
		pw.setBounds(30, 302, 420, 60);
		pw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isLoginCheck();
			}

		});
		panel.add(pw);
		pw.setColumns(10);

		login = new JButton("�α���");
		login.setForeground(new Color(255, 255, 255));
		login.setFont(new Font("���� ���", Font.PLAIN, 25));
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isLoginCheck();
			}
		});
		login.setBackground(new Color(255, 153, 0));
		login.setBounds(30, 374, 420, 60);
		panel.add(login);

		join = new JButton("ȸ������");
		join.setForeground(new Color(255, 255, 255));
		join.setFont(new Font("���� ���", Font.PLAIN, 15));
		join.setBackground(new Color(255, 153, 0));
		join.setBounds(30, 446, 110, 30);
		join.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ������ ���� ȸ������ â����...
				dispose();
				new Join2();
			}
		});
		panel.add(join);

		search = new JButton("ID/PWã��");
		search.setForeground(new Color(255, 255, 255));
		search.setFont(new Font("���� ���", Font.PLAIN, 15));
		search.setBackground(new Color(255, 153, 0));
		search.setBounds(340, 446, 110, 30);
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose(); // �α���â ����;
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
		// �α��� �����̶�� �Ŵ���â �ٿ��
		if (joinDB()) {
			main.showFrameConversation();
		} else {
			JOptionPane.showMessageDialog(null, "Failed");
		}
	}

	Connection conn = null; // DB����� ����(����)�� ���� ��ü
	PreparedStatement pstm = null; // SQL���� ��Ÿ���� ��ü
	ResultSet rs = null; // �������� �����Ϳ� ���� ��ȯ���� ���� ��ü
	int n = 0;
	private MainProcess main;

	public boolean joinDB() {
		boolean endJoin = false;
		try {
			// SQL ������ ����� ���� ������ ���Ǿ�(SELECT��)�̶��
			// �� ����� ���� ResultSet ��ü�� �غ��� �� �����Ų��.
			String quary = "SELECT USERNM FROM MEMBERS WHERE userid = ? and passwd = ?";

			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			String userid = id.getText();
			String passwd = pw.getText();
			pstm.setString(1, userid);
			pstm.setString(2, passwd);

			rs = pstm.executeQuery();

			if (rs.next() == true && userid != null) {
				JOptionPane.showMessageDialog(null, rs.getString("usernm") + "�� ȯ���մϴ�!");
				endJoin = true;
			} else {

			}
		} catch (SQLException sqle) {

			System.out.println("SELECT������ ���� �߻�");
			sqle.printStackTrace();
		} finally {
			// DB ������ �����Ѵ�.
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

	// �α��� ��/����
	public boolean isLogin() {
		return bLoginCheck;
	}

// mainProcess�� ����
	public void setMain(MainProcess main) {
		this.main = main;
	}

	public static void main(String[] args) {
		new Login2();

	}
}
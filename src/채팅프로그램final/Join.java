package ä�����α׷�final;
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
		setTitle("ȸ������");
		setSize(400,700);
		setResizable(false);
		setLocation(800,450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// �ǳ�
		JPanel jpan1 = new JPanel();
		JoinPanel(jpan1);
		// �����ӿ� �ǳ� �߰�
		add(jpan1);
		// ȭ�� ����� �°� �ڵ� ����
		pack();
		setVisible(true);
	}
	
	// ȸ������ �ǳ�
	public void JoinPanel(JPanel jpan1) {
		jpan1.setLayout(new GridLayout(7,2));
		id = new JTextField(15);
		id.setFont(new Font("���� ���", Font.PLAIN, 30));
		jpan1.add(new JLabel("ID"));
		jpan1.add(id);
		
		pw = new JPasswordField(15);
		jpan1.add(new JLabel("PW"));
		jpan1.add(pw);
		
		pw = new JPasswordField(15);
		jpan1.add(new JLabel("PW"));
		jpan1.add(pw);
		
		name = new JTextField(15);
		name.setFont(new Font("���� ���", Font.PLAIN, 30));
		jpan1.add(new JLabel("NAME"));
		jpan1.add(name);
		
		phone = new JTextField(15);
		phone.setFont(new Font("���� ���", Font.PLAIN, 30));
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
	
	// mainProcess�� ����
	public void setMain(MainProcess main) {
		this.main = main;
	}
	
	
	Connection conn = null; // DB����� ����(����)�� ���� ��ü
	PreparedStatement pstm = null; // SQL���� ��Ÿ���� ��ü
	ResultSet rs = null; // �������� �����Ϳ� ���� ��ȯ���� ���� ��ü
	int n=0;
	public void joinDB() {
		try {
			// SQL ������ ����� ���� ������ ���Ǿ�(SELECT��)�̶��
			// �� ����� ���� ResultSet ��ü�� �غ��� �� �����Ų��.
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
			
			System.out.println("SELECT������ ���� �߻�");
			sqle.printStackTrace();
		} finally {
			//DB ������ �����Ѵ�.
			try {
				if( pstm != null) {pstm.close();}
				if( conn != null) {conn.close();}
			}catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			
		}
		if(n>0) {
			System.out.println("��ϿϷ�");
		}else {
			System.out.println("����");
		}
	
	}
	
	
	
	
	
	public static void main(String[] args) {
		new Join();
	}

	
	
}

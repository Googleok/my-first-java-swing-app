package ä�����α׷�final;
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
		setTitle("�α���");
		setSize(280,150);
		setResizable(false);
		setLocation(800, 450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// �ǳ� 
		JPanel jpan1 = new JPanel();
		LoginPanel(jpan1);
		// �����ӿ� �ǳ� �߰�
		add(jpan1);
		
		// ȭ�� ����� �°� �ڵ� ����
		pack();
		
		setVisible(true);
	}
	
	// �α����ǳ�
	public void LoginPanel(JPanel jpan1) {
		// �׸��� ���̾ƿ����� ��
		jpan1.setLayout(new GridLayout(3, 2));
		
		// id �ؽ�Ʈ�ʵ� ���� 
		id = new JTextField(15);
		id.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isLoginCheck();
			}
		});
		jpan1.add(new JLabel("ID"));
		jpan1.add(id);

		// �н����� �н������ʵ� ����
		pw = new JPasswordField(15);
		pw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isLoginCheck();
			}

		});
		jpan1.add(new JLabel("PW"));
		jpan1.add(pw);

		// �α��� ��ư ����
		btnLogin = new JButton("Login");
		jpan1.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isLoginCheck();
			}
		});
		
		// ȸ������ ��ư ����
		btnJoin = new JButton("Join");
		jpan1.add(btnJoin);
		btnJoin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ������ ���� ȸ������ â����...
				dispose();
				new Join();
			}
		});
	}
	
	// �α��� üũ
	public void isLoginCheck() {
			// �α��� �����̶�� �Ŵ���â �ٿ��
			if(joinDB()) {
				main.showFrameConversation();
			}else {
				JOptionPane.showMessageDialog(null, "Failed");
			}
	}
	
	
	Connection conn = null; // DB����� ����(����)�� ���� ��ü
	PreparedStatement pstm = null; // SQL���� ��Ÿ���� ��ü
	ResultSet rs = null; // �������� �����Ϳ� ���� ��ȯ���� ���� ��ü
	int n=0;
	public boolean joinDB() {
		boolean endJoin = false;
		try {
			// SQL ������ ����� ���� ������ ���Ǿ�(SELECT��)�̶��
			// �� ����� ���� ResultSet ��ü�� �غ��� �� �����Ų��.
			String quary = "SELECT USERNM FROM MEMBERS WHERE userid = ? and passwd = ?";
			
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			String userid =id.getText();
			String passwd = pw.getText();
			pstm.setString(1, userid);
			pstm.setString(2, passwd);
			
			rs = pstm.executeQuery();
		
			if(rs.next() == true && userid != null) {
				JOptionPane.showMessageDialog(null, rs.getString("usernm")+"�� ȯ���մϴ�!");
				endJoin = true;
			}else {
				
			}
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
		
	}
	
	

}

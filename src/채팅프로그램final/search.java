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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class search extends JFrame {
	private JLabel id;
	private JLabel phone;
	private JLabel email;
	private JLabel name;
	private JTextField tid;
	private JTextField tphone;
	private JTextField temail;
	private JTextField tname;
	private JButton srid;
	private JButton srpw;
	private JButton back;
	private JButton imgbtn;
	private MainProcess main;
	
	public search()
	{
		getContentPane().setForeground(Color.WHITE);
		setBackground(Color.WHITE);
		setBounds(100, 100,500,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		panel.setForeground(Color.WHITE);
		panel.setBounds(0, 0, 482, 553);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tname = new JTextField();
		tname.setBounds(148, 138, 300, 50);
		panel.add(tname);
		tname.setColumns(10);
		
		tid = new JTextField();
		tid.setBounds(148, 353, 300, 50);
		panel.add(tid);
		tid.setColumns(10);
		
		tphone = new JTextField();
		tphone.setBounds(148, 415, 300, 50);
		panel.add(tphone);
		tphone.setColumns(10);
		
		temail = new JTextField();
		temail.setBounds(148, 200, 300, 50);
		panel.add(temail);
		temail.setColumns(10);
		
		name = new JLabel("NAME");
		name.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		name.setForeground(new Color(255, 153, 0));
		name.setBounds(44, 138, 100, 30);
		panel.add(name);
		
		id = new JLabel("ID");
		id.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		id.setForeground(new Color(255, 153, 0));
		id.setBounds(44, 327, 100, 30);
		panel.add(id);
		
		phone = new JLabel("PHONE");
		phone.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		phone.setForeground(new Color(255, 153, 0));
		phone.setBounds(44, 389, 100, 30);
		panel.add(phone);
		
		email = new JLabel("E-MAIL");
		email.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		email.setForeground(new Color(255, 153, 0));
		email.setBounds(44, 200, 100, 30);
		panel.add(email);
		
		srpw = new JButton("PW\uCC3E\uAE30");
		srpw.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		srpw.setForeground(new Color(255, 255, 255));
		srpw.setBackground(new Color(255, 153, 0));
		srpw.setBounds(343, 490, 105, 27);
		srpw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PWDB();
			}
		});
		panel.add(srpw);
		
		back = new JButton("BACK");
		back.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		back.setForeground(new Color(255, 255, 255));
		back.setBackground(new Color(255, 153, 0));
		back.setBounds(224, 490, 105, 27);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Login2();
			}
		});
		panel.add(back);
		
		srid = new JButton("ID\uCC3E\uAE30");
		srid.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		srid.setForeground(new Color(255, 255, 255));
		srid.setBackground(new Color(255, 153, 0));
		srid.setBounds(343, 271, 105, 27);
		srid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				IDDB();
			}
		});
		panel.add(srid);
		
		imgbtn = new JButton();
		
		ImageIcon icon = new ImageIcon(".\\img\\logo.png");
		imgbtn.setBorderPainted(false);
		imgbtn.setContentAreaFilled(false);
		imgbtn.setIcon(icon);
		imgbtn.setBounds(160, 5, 150, 130);
		

		panel.add(imgbtn);
		setVisible(true);
	}
	
	
	public void setMain(MainProcess main) {
		this.main = main;
	}
	
	
	Connection conn = null; // DB연결된 상태(세션)을 담은 객체
	PreparedStatement pstm = null; // SQL문을 나타내는 객체
	ResultSet rs = null; // 쿼리문을 날린것에 대한 반환값을 담을 객체
	int n=0;
	public boolean IDDB() {
		boolean endJoin = false;
		try {
			// SQL 문장을 만들고 만약 문장이 질의어(SELECT문)이라면
			// 그 결과를 담을 ResultSet 객체를 준비한 후 실행시킨다.
			String quary = "SELECT userID FROM MEMBERS WHERE usernm = ? and email = ?";
			
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			String usernm =tname.getText();
			String mail = temail.getText();
			pstm.setString(1, usernm);
			pstm.setString(2, mail);
			
			rs = pstm.executeQuery();
		
			if(rs.next() == true && usernm != null) {
				
				JOptionPane.showMessageDialog(null,"ID : "+rs.getString("userid"));
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
	

	public boolean PWDB() {
		boolean endJoin = false;
		try {
			// SQL 문장을 만들고 만약 문장이 질의어(SELECT문)이라면
			// 그 결과를 담을 ResultSet 객체를 준비한 후 실행시킨다.
			String quary = "SELECT PASSWD FROM MEMBERS WHERE userid = ? and phone = ?";
			
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			String userid =tid.getText();
			String number = tphone.getText();
			pstm.setString(1, userid);
			pstm.setString(2, number);
			
			rs = pstm.executeQuery();
		
			if(rs.next() == true && userid != null) {
				
				JOptionPane.showMessageDialog(null,"PASSWORD : "+rs.getString("passwd"));
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
	
	
	public static void main(String[] args) {
		new search();
	}

}

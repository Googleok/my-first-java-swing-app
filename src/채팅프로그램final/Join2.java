package Ã¤ÆÃÇÁ·Î±×·¥final;

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
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Join2 extends JFrame {
	
	private JLabel id;
	private JLabel pw;
	private JLabel phone;
	private JLabel email;
	private JLabel name;
	private JTextField tid;
	private JTextField tpw;
	private JTextField tphone;
	private JTextField temail;
	private JTextField tname;
	private JButton bjoin;
	private JButton back;
	private JButton imgbtn;
	private MainProcess main;

	public Join2()
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
		tname.setBounds(148, 170, 300, 50);
		tname.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 30));
		panel.add(tname);
		tname.setColumns(10);
		
		tid = new JTextField();
		tid.setBounds(148, 232, 300, 50);
		tid.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 30));
		panel.add(tid);
		tid.setColumns(10);
		
		tpw = new JTextField();
		tpw.setBounds(148, 356, 300, 50);
		tpw.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 30));
		panel.add(tpw);
		tpw.setColumns(10);
		
		tphone = new JTextField();
		tphone.setBounds(148, 418, 300, 50);
		tphone.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 30));
		panel.add(tphone);
		tphone.setColumns(10);
		
		temail = new JTextField();
		temail.setBounds(148, 294, 300, 50);
		temail.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 30));
		panel.add(temail);
		temail.setColumns(10);
		
		name = new JLabel("NAME");
		name.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 25));
		name.setForeground(new Color(255, 153, 0));
		name.setBounds(44, 170, 100, 30);
		panel.add(name);
		
		id = new JLabel("ID");
		id.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 25));
		id.setForeground(new Color(255, 153, 0));
		id.setBounds(44, 232, 100, 30);
		panel.add(id);
		
		pw = new JLabel("PW");
		pw.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 25));
		pw.setForeground(new Color(255, 153, 0));
		pw.setBounds(44, 294, 100, 30);
		panel.add(pw);
		
		phone = new JLabel("PHONE");
		phone.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 25));
		phone.setForeground(new Color(255, 153, 0));
		phone.setBounds(44, 356, 100, 30);
		panel.add(phone);
		
		email = new JLabel("E-MAIL");
		email.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 25));
		email.setForeground(new Color(255, 153, 0));
		email.setBounds(44, 418, 100, 30);
		panel.add(email);
		
		bjoin = new JButton("JOIN");
		bjoin.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 20));
		bjoin.setForeground(new Color(255, 255, 255));
		bjoin.setBackground(new Color(255, 153, 0));
		bjoin.setBounds(343, 490, 105, 27);
		bjoin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				joinDB();
			}
		});
		panel.add(bjoin);
		
		back = new JButton("BACK");
		back.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 20));
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

		imgbtn = new JButton();
		
		ImageIcon icon = new ImageIcon(".\\img\\logo.png");
		imgbtn.setBorderPainted(false);
		imgbtn.setContentAreaFilled(false);
		imgbtn.setIcon(icon);
		imgbtn.setBounds(160, 30, 150, 130);
		

		panel.add(imgbtn);
		
		setVisible(true);
		
	}
	
	public void setMain(MainProcess main) {
		this.main = main;
	}
	
	
	Connection conn = null; // DB¿¬°áµÈ »óÅÂ(¼¼¼Ç)À» ´ãÀº °´Ã¼
	PreparedStatement pstm = null; // SQL¹®À» ³ªÅ¸³»´Â °´Ã¼
	ResultSet rs = null; // Äõ¸®¹®À» ³¯¸°°Í¿¡ ´ëÇÑ ¹ÝÈ¯°ªÀ» ´ãÀ» °´Ã¼
	int n=0;
	public void joinDB() {
		try {
			// SQL ¹®ÀåÀ» ¸¸µé°í ¸¸¾à ¹®ÀåÀÌ ÁúÀÇ¾î(SELECT¹®)ÀÌ¶ó¸é
			// ±× °á°ú¸¦ ´ãÀ» ResultSet °´Ã¼¸¦ ÁØºñÇÑ ÈÄ ½ÇÇà½ÃÅ²´Ù.
			String quary = "INSERT INTO MEMBERS(USERID, PASSWD, USERNM, PHONE, EMAIL) VALUES(?,?,?,?,?)";
			
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			String userid =tid.getText();
			String passwd = tpw.getText();
			String usernm = tname.getText();
			String number = tphone.getText();
			String mail = temail.getText();
			pstm.setString(1, userid);
			pstm.setString(2, passwd);
			pstm.setString(3, usernm);
			pstm.setString(4, number);
			pstm.setString(5, mail);
			
			n=pstm.executeUpdate();
			
		} catch (SQLException sqle) {
			
			System.out.println("SELECT¹®¿¡¼­ ¿¹¿Ü ¹ß»ý");
			sqle.printStackTrace();
		} finally {
			//DB ¿¬°áÀ» Á¾·áÇÑ´Ù.
			try {
				if( pstm != null) {pstm.close();}
				if( conn != null) {conn.close();}
			}catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			
		}
		if(n>0) {
			System.out.println("µî·Ï¿Ï·á");
		}else {
			System.out.println("½ÇÆÐ");
		}
	
	}
	
	public static void main(String[] args) {
		
		new Join2();
		
	}
}

package ä�����α׷�final;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class OracleTest {
//	Connection conn = null; // DB����� ����(����)�� ���� ��ü
//	PreparedStatement pstm = null; // SQL���� ��Ÿ���� ��ü
//	ResultSet rs = null; // �������� �����Ϳ� ���� ��ȯ���� ���� ��ü
//	Join join;
//	int n=0;
//	public OracleTest() {
//		try {
//			// SQL ������ ����� ���� ������ ���Ǿ�(SELECT��)�̶��
//			// �� ����� ���� ResultSet ��ü�� �غ��� �� �����Ų��.
//			String quary = "INSERT INTO MEMBERS(USERID, PASSWD, USERNM, PHONE, EMAIL) VALUES(?,?,?,?,?)";
//			
//			conn = DBConnection.getConnection();
//			pstm = conn.prepareStatement(quary);
//			String userid =join.getId();
//			String passwd = join.getPw();
//			String usernm = join.getName();
//			String phone = join.getPhone();
//			String email = join.getEmail();
//			System.out.println("asdasd"+userid);
//			pstm.setString(1, userid);
//			pstm.setString(2, passwd);
//			pstm.setString(3, usernm);
//			pstm.setString(4, phone);
//			pstm.setString(5, email);
//			
//			n=pstm.executeUpdate();
//			
//		} catch (SQLException sqle) {
//			
//			System.out.println("SELECT������ ���� �߻�");
//			sqle.printStackTrace();
//		} finally {
//			//DB ������ �����Ѵ�.
//			try {
//				if( pstm != null) {pstm.close();}
//				if( conn != null) {conn.close();}
//			}catch (Exception e) {
//				throw new RuntimeException(e.getMessage());
//			}
//			
//		}
//		if(n>0) {
//			System.out.println("��ϿϷ�");
//		}else {
//			System.out.println("����");
//		}
//	
//	}
//	
//	public static void main(String[] args) {
//		new OracleTest();
//
//	}
//
//}

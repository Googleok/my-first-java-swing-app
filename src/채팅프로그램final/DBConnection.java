package ä�����α׷�final;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public static Connection dbConn;
	
	public static Connection getConnection() {
		Connection conn =null;
		try {
			final String user = "system";
			final String pw ="1234";
			final String url = "jdbc:oracle:thin:@192.168.0.29:1521:orcl";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pw);
			
		} catch (ClassNotFoundException cnfe) {
			System.out.println("DB ����̹� �ε� ���� :"+cnfe.toString());
		} catch (SQLException sqle) {
			System.out.println("DB ���ӽ��� : "+sqle.toString());
		} catch (Exception e) {
			System.out.println("Unknown error");
			e.printStackTrace();
		}
		return conn;
	}
}

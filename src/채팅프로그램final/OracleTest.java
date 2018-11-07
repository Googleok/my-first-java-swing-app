package 채팅프로그램final;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class OracleTest {
//	Connection conn = null; // DB연결된 상태(세션)을 담은 객체
//	PreparedStatement pstm = null; // SQL문을 나타내는 객체
//	ResultSet rs = null; // 쿼리문을 날린것에 대한 반환값을 담을 객체
//	Join join;
//	int n=0;
//	public OracleTest() {
//		try {
//			// SQL 문장을 만들고 만약 문장이 질의어(SELECT문)이라면
//			// 그 결과를 담을 ResultSet 객체를 준비한 후 실행시킨다.
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
//			System.out.println("SELECT문에서 예외 발생");
//			sqle.printStackTrace();
//		} finally {
//			//DB 연결을 종료한다.
//			try {
//				if( pstm != null) {pstm.close();}
//				if( conn != null) {conn.close();}
//			}catch (Exception e) {
//				throw new RuntimeException(e.getMessage());
//			}
//			
//		}
//		if(n>0) {
//			System.out.println("등록완료");
//		}else {
//			System.out.println("실패");
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

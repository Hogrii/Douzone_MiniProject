package UTILS;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnHelper {
	private static Connection conn;
	private ConnHelper() {}
	
	public static Connection getConnection() { // 연결객체 싱글톤패턴 적용
		if(conn != null) return conn;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "TEAM1", "1004");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}		
		return conn;
	}
}

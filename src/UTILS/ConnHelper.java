package UTILS;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnHelper {	
	public ConnHelper() { // 생성자 호출시 연결객체 생성
		getConnection();
	}
	
	private Connection getConnection() { // 연결객체
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "TEAM1", "1004");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}		
		return conn;
	}
}

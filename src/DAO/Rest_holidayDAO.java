package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import UTILS.ConnHelper;

public class Rest_holidayDAO {
	// 휴가 잔여일 처리 -> 사용
	public int restVacationApply(int empno, int vacationDay) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int row = 0;
		
		try {
			conn = ConnHelper.getConnection();
			String sql = "update rest_holiday set restday = restday - ? where empno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vacationDay);
			pstmt.setInt(2, empno);
			
			row = pstmt.executeUpdate();
			if(row > 0) {
				System.out.println("update row count : " + row);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			ConnHelper.close(pstmt);
		}		
		return row;
	}
}

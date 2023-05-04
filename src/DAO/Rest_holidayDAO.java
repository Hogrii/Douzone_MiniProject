package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
	
	// 휴가 잔여 일수 얻기
	public int getRestDay(int empno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int dateCal = 0;
		
		try {
			conn = ConnHelper.getConnection();
			String sql = "select restday from rest_holiday where empno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empno);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dateCal = rs.getInt(1);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			ConnHelper.close(rs);
			ConnHelper.close(pstmt);
		}
		return dateCal;
	}
}

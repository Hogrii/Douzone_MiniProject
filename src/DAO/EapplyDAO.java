package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DTO.EapplyDTO;
import UTILS.ConnHelper;

public class EapplyDAO {
	// 전체 휴가 조회
	public List<EapplyDTO> getAllVacationList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<EapplyDTO> eapplyList = new ArrayList<EapplyDTO>();
		
		try {
			conn = ConnHelper.getConnection();
			String sql = "select applyno, empno, holidayno, stateno, start_date, end_date, reason from eapply";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				do {
					EapplyDTO apdto = new EapplyDTO();
					apdto.setApplyno(rs.getInt(1));
					apdto.setEmpno(rs.getInt(2));
					apdto.setHolidayno(rs.getInt(3));
					apdto.setStateno(rs.getInt(4));
					apdto.setStart_date(rs.getDate(5));
					apdto.setEnd_date(rs.getDate(6));
					apdto.setReason(rs.getString(7));
					eapplyList.add(apdto);
				}while(rs.next());
			}else {
				System.out.println("조회된 데이터가 없습니다.");
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			ConnHelper.close(rs);
			ConnHelper.close(pstmt);
		}		
		return eapplyList;
	}
	
	// 휴가 신청 목록 -> 사용
	public List<EapplyDTO> getMyVacationList(int empno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<EapplyDTO> eapplyList = new ArrayList<EapplyDTO>();
		
		try {
			conn = ConnHelper.getConnection();
			String sql = "select applyno, empno, holidayno, stateno, start_date, end_date, reason from eapply where empno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empno);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				EapplyDTO apdto = new EapplyDTO();
				apdto.setApplyno(rs.getInt(1));
				apdto.setEmpno(rs.getInt(2));
				apdto.setHolidayno(rs.getInt(3));
				apdto.setStateno(rs.getInt(4));
				apdto.setStart_date(rs.getDate(5));
				apdto.setEnd_date(rs.getDate(6));
				apdto.setReason(rs.getString(7));
				eapplyList.add(apdto);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			ConnHelper.close(rs);
			ConnHelper.close(pstmt);
		}		
		return eapplyList; 
	}
	
	// 휴가 결제 -> 사용
	public List<EapplyDTO> vacationList(int empno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<EapplyDTO> eapplyList = new ArrayList<EapplyDTO>();
		
		try {
			conn = ConnHelper.getConnection();
			String sql = "select a.applyno, a.empno, a.holidayno, a.stateno, a.start_date, a.end_date, a.reason"
					+ " from eapply a join emp e on (a.empno = e.empno)"
					+ " where e.mgr = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empno);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				EapplyDTO apdto = new EapplyDTO();
				apdto.setApplyno(rs.getInt(1));
				apdto.setEmpno(rs.getInt(2));
				apdto.setHolidayno(rs.getInt(3));
				apdto.setStateno(rs.getInt(4));
				apdto.setStart_date(rs.getDate(5));
				apdto.setEnd_date(rs.getDate(6));
				apdto.setReason(rs.getString(7));
				eapplyList.add(apdto);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			ConnHelper.close(rs);
			ConnHelper.close(pstmt);
		}		
		return eapplyList; 
	}
	
	// 휴가 신청 -> 사용
	public void applyVacation(EapplyDTO apdto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int row = 0;

		try {
			conn = ConnHelper.getConnection();
			String sql = "insert into eapply(applyno, empno, holidayno, stateno, start_date, end_date, reason) values(eapply_seq.nextval, ?, ?, 0, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, apdto.getEmpno());
			pstmt.setInt(2, apdto.getHolidayno());
			pstmt.setDate(3, apdto.getStart_date());
			pstmt.setDate(4, apdto.getEnd_date());
			pstmt.setString(5, apdto.getReason());
			
			row = pstmt.executeUpdate();
			if(row > 0) {
				System.out.println("insert row count : " + row);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			ConnHelper.close(pstmt);
		}
	}
	
	// 결재 처리 -> 사용
	public int vacationConfirm(EapplyDTO apdto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int row = 0;
		
		try {
			conn = ConnHelper.getConnection();
			String sql = "update eapply set start_date = ?, end_date = ?, reason = ? where applyno = ? and empno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, apdto.getStart_date());
			pstmt.setDate(2, apdto.getEnd_date());
			pstmt.setString(3, apdto.getReason());
			pstmt.setInt(4, apdto.getApplyno());
			pstmt.setInt(5, apdto.getEmpno());
			
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
	
	// 삭제
	public int empDelete(int empno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int row = 0;
		
		try {
			conn = ConnHelper.getConnection();
			String sql = "delete from eapply where empno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empno);
			row = pstmt.executeUpdate();
			if(row > 0) {
				System.out.println("delete row count : " + row);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			ConnHelper.close(pstmt);
		}		
		return row;
	}
	

}

package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DTO.EmpDTO;
import UTILS.ConnHelper;

public class EmpDAO {
	
	// 로그인 체크
	public boolean checkLogin(int empno, String ename) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		EmpDTO edto = new EmpDTO();


		try {
			conn = ConnHelper.getConnection();
			String sql = "select ename from emp where empno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empno);
			
			rs = pstmt.executeQuery();
			if (rs.next()) edto.setEname(rs.getString(1));
			if(ename.equals(edto.getEname())) return true;
			else return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnHelper.close(rs);
			ConnHelper.close(pstmt);
		}
		return false;
	}

	// 전체 조회
	public List<EmpDTO> getSelectAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<EmpDTO> empList = new ArrayList<EmpDTO>();

		try {
			conn = ConnHelper.getConnection();
			String sql = "select empno, rankno, deptno, mgr, ename, id_number, age, tel, hiredate, email, addr from emp";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					EmpDTO edto = new EmpDTO();
					edto.setEmpno(rs.getInt(1));
					edto.setRankno(rs.getInt(2));
					edto.setDeptno(rs.getInt(3));
					edto.setMgr(rs.getInt(4));
					edto.setEname(rs.getString(5));
					edto.setId_number(rs.getString(6));
					edto.setAge(rs.getInt(7));
					edto.setTel(rs.getString(8));
					edto.setHiredate(rs.getDate(9));
					edto.setEmail(rs.getString(10));
					edto.setAddr(rs.getString(11));
					empList.add(edto);
				} while (rs.next());
			} else {
				System.out.println("조회된 데이터가 없습니다.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnHelper.close(rs);
			ConnHelper.close(pstmt);
		}
		return empList;
	}

	// 조건 조회
	public EmpDTO getSelectOne(int empno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		EmpDTO edto = new EmpDTO();

		try {
			conn = ConnHelper.getConnection();
			String sql = "select empno, rankno, deptno, mgr, ename, id_number, age, tel, hiredate, email, addr from emp where empno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				edto.setEmpno(rs.getInt(1));
				edto.setRankno(rs.getInt(2));
				edto.setDeptno(rs.getInt(3));
				edto.setMgr(rs.getInt(4));
				edto.setEname(rs.getString(5));
				edto.setId_number(rs.getString(6));
				edto.setAge(rs.getInt(7));
				edto.setTel(rs.getString(8));
				edto.setHiredate(rs.getDate(9));
				edto.setEmail(rs.getString(10));
				edto.setAddr(rs.getString(11));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnHelper.close(rs);
			ConnHelper.close(pstmt);
		}
		return edto;
	}

	// 삽입
	public int empInsert(EmpDTO edto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int row = 0;

		try {
			conn = ConnHelper.getConnection();
			String sql = "insert into emp(empno, rankno, deptno, mgr, ename, id_number, age, tel, hiredate, email, addr) values(emp_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, edto.getRankno());
			pstmt.setInt(2, edto.getDeptno());
			pstmt.setInt(3, edto.getMgr());
			pstmt.setString(4, edto.getEname());
			pstmt.setString(5, edto.getId_number());
			pstmt.setInt(6, edto.getAge());
			pstmt.setString(7, edto.getTel());
			pstmt.setDate(8, edto.getHiredate());
			pstmt.setString(9, edto.getEmail());
			pstmt.setString(10, edto.getAddr());

			row = pstmt.executeUpdate();
			if (row > 0) {
				System.out.println("insert row count : " + row);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
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
			String sql = "delete from emp where empno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empno);

			row = pstmt.executeUpdate();
			if (row > 0) {
				System.out.println("delete row count : " + row);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnHelper.close(pstmt);
		}
		return row;
	}

	// 수정
	public int empUpdate(EmpDTO edto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int row = 0;

		try {
			conn = ConnHelper.getConnection();
			String sql = "update emp set rankno = ?, deptno = ?, mgr = ?, ename = ?, id_number = ?, age = ?, tel = ?, hiredate = ?, email = ?, addr = ? where empno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, edto.getRankno());
			pstmt.setInt(2, edto.getDeptno());
			pstmt.setInt(3, edto.getMgr());
			pstmt.setString(4, edto.getEname());
			pstmt.setString(5, edto.getId_number());
			pstmt.setInt(6, edto.getAge());
			pstmt.setString(7, edto.getTel());
			pstmt.setDate(8, edto.getHiredate());
			pstmt.setString(9, edto.getEmail());
			pstmt.setString(10, edto.getAddr());

			row = pstmt.executeUpdate();
			if (row > 0) {
				System.out.println("update row count : " + row);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			ConnHelper.close(pstmt);
		}
		return row;
	}
}

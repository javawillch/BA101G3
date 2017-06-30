package com.question.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.*;


public class Answer_ReportDAO implements Answer_ReportDAO_interface {
	
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
			private static DataSource ds = null;
			static {
				try {
					Context ctx = new InitialContext();
					ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
	// 新增資料
	private static final String INSERT_STMT = "INSERT INTO answer_report (ans_no, mem_no, ansrpt_date, ansrpt_rsn, ansrpt_is_cert, ansrpt_unrsn ) VALUES (?, ?, SYSDATE, ?, '0', ?)";
	// 查詢資料
	private static final String GET_ALL_STMT = "SELECT * FROM answer_report";
	private static final String GET_ONE_STMT = "SELECT * FROM answer_report WHERE ans_no = ? AND mem_no=? ";
	// 修改資料
	private static final String UPDATE = "UPDATE answer_report SET ansrpt_is_cert=?, ansrpt_unrsn=? WHERE ans_no=? AND mem_no=?";

	@Override
	public void insert(Answer_ReportVO answer_reportVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT); 
			pstmt.setString(1, answer_reportVO.getAns_no());
			pstmt.setString(2, answer_reportVO.getMem_no());
			pstmt.setString(3, answer_reportVO.getAnsrpt_rsn());
			pstmt.setString(4, answer_reportVO.getAnsrpt_unrsn());
			
			pstmt.executeUpdate();

			// Handle any DRIVER errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(Answer_ReportVO answer_reportVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, answer_reportVO.getAnsrpt_is_cert());
			pstmt.setString(2, answer_reportVO.getAnsrpt_unrsn());
			pstmt.setString(3, answer_reportVO.getAns_no());
			pstmt.setString(4, answer_reportVO.getMem_no());
			pstmt.executeUpdate();

			// Handle any DRIVER errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}


	@Override
	public Answer_ReportVO findByPrimaryKey(String ans_no, String mem_no) {

		Answer_ReportVO answer_reportVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, ans_no);
			pstmt.setString(2, mem_no);


			rs = pstmt.executeQuery();

			while (rs.next()) {
				answer_reportVO = new Answer_ReportVO();
				answer_reportVO.setAns_no(rs.getString("ans_no"));
				answer_reportVO.setMem_no(rs.getString("mem_no"));
				answer_reportVO.setAnsrpt_date(rs.getTimestamp("ansrpt_date"));
				answer_reportVO.setAnsrpt_rsn(rs.getString("ansrpt_rsn"));
				answer_reportVO.setAnsrpt_is_cert(rs.getString("ansrpt_is_cert"));
				answer_reportVO.setAnsrpt_unrsn(rs.getString("ansrpt_unrsn"));
			}

			// Handle any DRIVER errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return answer_reportVO;
	}

	@Override
	public List<Answer_ReportVO> getAll() {
		List<Answer_ReportVO> list = new ArrayList<Answer_ReportVO>();
		Answer_ReportVO answer_reportVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				answer_reportVO = new Answer_ReportVO();
				answer_reportVO.setAns_no(rs.getString("ans_no"));
				answer_reportVO.setMem_no(rs.getString("mem_no"));
				answer_reportVO.setAnsrpt_date(rs.getTimestamp("ansrpt_date"));
				answer_reportVO.setAnsrpt_rsn(rs.getString("ansrpt_rsn"));
				answer_reportVO.setAnsrpt_is_cert(rs.getString("ansrpt_is_cert"));
				answer_reportVO.setAnsrpt_unrsn(rs.getString("ansrpt_unrsn"));
				list.add(answer_reportVO); // Store the row in the list
			}

			// Handle any DRIVER errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
}
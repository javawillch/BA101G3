package com.question.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.*;


public class AnswerDAO implements AnswerDAO_interface {
	
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
	private static final String INSERT_STMT = "INSERT INTO answer (ans_no, mem_no, qu_no, ans_date, ans_cnt, ans_like, ans_is_hide) VALUES ('ANS'||LPAD(to_char(ans_no_seq.NEXTVAL),5,'0'), ?, ?, SYSDATE, ?, ?, ?)";
	// 查詢資料
	private static final String GET_ALL_STMT = "SELECT * FROM answer";
	private static final String GET_ONE_STMT = "SELECT * FROM answer WHERE ans_no = ?";
	// 修改資料
	private static final String UPDATE = "UPDATE answer set ans_cnt=?, ans_like=?, ans_is_hide=? WHERE ans_no = ?";

	@Override
	public void insert(AnswerVO answerVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			String[] cols = { "ans_no" }; // 有使用sequence產生編號的話才要寫
			pstmt = con.prepareStatement(INSERT_STMT, cols); // 有使用sequence產生編號的話才要寫第二個參數
			pstmt.setString(1, answerVO.getMem_no());
			pstmt.setString(2, answerVO.getQu_no());
			pstmt.setString(3, answerVO.getAns_cnt());
			pstmt.setInt(4, answerVO.getAns_like());
			pstmt.setString(5, answerVO.getAns_is_hide());
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
	public void update(AnswerVO answerVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, answerVO.getAns_cnt());
			pstmt.setInt(2, answerVO.getAns_like());
			pstmt.setString(3, answerVO.getAns_is_hide());
			pstmt.setString(4, answerVO.getAns_no());

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
	public AnswerVO findByPrimaryKey(String ans_no) {

		AnswerVO answerVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ans_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				answerVO = new AnswerVO();
				answerVO.setAns_no(rs.getString("ans_no"));
				answerVO.setMem_no(rs.getString("mem_no"));
				answerVO.setQu_no(rs.getString("qu_no"));
				answerVO.setAns_date(rs.getTimestamp("ans_date"));
				answerVO.setAns_cnt(rs.getString("ans_cnt"));
				answerVO.setAns_like(rs.getInt("ans_like"));
				answerVO.setAns_is_hide(rs.getString("ans_is_hide"));
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
		return answerVO;
	}

	@Override
	public List<AnswerVO> getAll() {
		List<AnswerVO> list = new ArrayList<AnswerVO>();
		AnswerVO answerVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				answerVO = new AnswerVO();
				answerVO.setAns_no(rs.getString("ans_no"));
				answerVO.setMem_no(rs.getString("mem_no"));
				answerVO.setQu_no(rs.getString("qu_no"));
				answerVO.setAns_date(rs.getTimestamp("ans_date"));
				answerVO.setAns_cnt(rs.getString("ans_cnt"));
				answerVO.setAns_like(rs.getInt("ans_like"));
				answerVO.setAns_is_hide(rs.getString("ans_is_hide"));
				list.add(answerVO); // Store the row in the list
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
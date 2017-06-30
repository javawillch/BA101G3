package com.question.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


import com.question.model.QuestionVO;

import java.sql.*;


public class Question_ClassificationDAO implements Question_ClassificationDAO_interface {
	
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
	private static final String INSERT_STMT = "INSERT INTO question_classification (quec_no, quec_name) VALUES ('QC'||LPAD(to_char(quec_no_seq.NEXTVAL),2,'0'), ?)";
	// 查詢資料
	private static final String GET_ALL_STMT = "SELECT * FROM question_classification";
	private static final String GET_ONE_STMT = "SELECT * FROM question_classification WHERE quec_no = ?";
	// 修改資料
	private static final String UPDATE = "UPDATE question_classification SET quec_name=? WHERE quec_no = ?";
	//JOIN查詢
	private static final String GET_Questions_ByQuec_no_STMT = "SELECT qu_no, mem_no,quec_no, qu_title,to_char(qu_date,'yyyy-mm-dd hh:mm:ss') qu_date, qu_cnt FROM question where quec_no = ? order by qu_no";
	
	@Override
	public void insert(Question_ClassificationVO question_classificationVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			String[] cols = { "quec_no" }; // 有使用sequence產生編號的話才要寫
			pstmt = con.prepareStatement(INSERT_STMT, cols); // 有使用sequence產生編號的話才要寫第二個參數
			pstmt.setString(1, question_classificationVO.getQuec_name());

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
	public void update(Question_ClassificationVO question_classificationVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, question_classificationVO.getQuec_name());
			pstmt.setString(2, question_classificationVO.getQuec_no());

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
	public Question_ClassificationVO findByPrimaryKey(String quec_no) {

		Question_ClassificationVO question_classificationVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, quec_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				question_classificationVO = new Question_ClassificationVO();
				question_classificationVO.setQuec_no(rs.getString("quec_no"));
				question_classificationVO.setQuec_name(rs.getString("quec_name"));
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
		return question_classificationVO;
	}

	@Override
	public List<Question_ClassificationVO> getAll() {
		List<Question_ClassificationVO> list = new ArrayList<Question_ClassificationVO>();
		Question_ClassificationVO question_classificationVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				question_classificationVO = new Question_ClassificationVO();
				question_classificationVO.setQuec_no(rs.getString("quec_no"));
				question_classificationVO.setQuec_name(rs.getString("quec_name"));
				list.add(question_classificationVO); // Store the row in the list
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
	
	@Override
	public Set<QuestionVO> getQuestionsByQuec_no(String quec_no) {
		Set<QuestionVO> set = new LinkedHashSet<QuestionVO>();
		QuestionVO questionVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Questions_ByQuec_no_STMT);
			pstmt.setString(1, quec_no);
			rs = pstmt.executeQuery();
		//	qu_no, mem_no,quec_no, quec_name, qu_title,to_char(qu_date,'yyyy-mm-dd') qu_date, qu_cnt 
			while (rs.next()) {
				questionVO = new QuestionVO();
				questionVO.setQu_no(rs.getString("qu_no"));
				questionVO.setMem_no(rs.getString("mem_no"));
				questionVO.setQuec_no(rs.getString("quec_no"));
				questionVO.setQu_date(rs.getTimestamp("qu_date"));
				questionVO.setQu_cnt(rs.getString("qu_cnt"));
				set.add(questionVO); // Store the row in the vector
			}
	
			// Handle any SQL errors
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
		return set;
	}



}
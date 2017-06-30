package com.question.model;

import java.util.*;

import com.question.model.QuestionVO;

import java.sql.*;


public class Question_ClassificationJDBCDAO implements Question_ClassificationDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "ba101g3";
	private static final String PASSWORD = "baby";
	// 新增資料
	private static final String INSERT_STMT = "INSERT INTO question_classification (quec_no, quec_name) VALUES ('QC'||LPAD(to_char(quec_no_seq.NEXTVAL),2,'0'), ?)";
	// 查詢資料
	private static final String GET_ALL_STMT = "SELECT * FROM question_classification";
	private static final String GET_ONE_STMT = "SELECT * FROM question_classification WHERE quec_no = ?";
	// 修改資料
	private static final String UPDATE = "UPDATE question_classification SET quec_name=? WHERE quec_no = ?";

	@Override
	public void insert(Question_ClassificationVO question_classificationVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			String[] cols = { "quec_no" }; // 有使用sequence產生編號的話才要寫
			pstmt = con.prepareStatement(INSERT_STMT, cols); // 有使用sequence產生編號的話才要寫第二個參數
			pstmt.setString(1, question_classificationVO.getQuec_name());

			pstmt.executeUpdate();

			// Handle any DRIVER errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database DRIVER. "
					+ e.getMessage());
			// Handle any SQL errors
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

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, question_classificationVO.getQuec_name());
			pstmt.setString(2, question_classificationVO.getQuec_no());

			pstmt.executeUpdate();

			// Handle any DRIVER errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database DRIVER. "
					+ e.getMessage());
			// Handle any SQL errors
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

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, quec_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				question_classificationVO = new Question_ClassificationVO();
				question_classificationVO.setQuec_no(rs.getString("quec_no"));
				question_classificationVO.setQuec_name(rs.getString("quec_name"));
			}

			// Handle any DRIVER errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database DRIVER. "
					+ e.getMessage());
			// Handle any SQL errors
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

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				question_classificationVO = new Question_ClassificationVO();
				question_classificationVO.setQuec_no(rs.getString("quec_no"));
				question_classificationVO.setQuec_name(rs.getString("quec_name"));
				list.add(question_classificationVO); // Store the row in the list
			}

			// Handle any DRIVER errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database DRIVER. "
					+ e.getMessage());
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
		return list;
	}
	
	@Override
	public Set<QuestionVO> getQuestionsByQuec_no(String quec_no) {
		// JDBC版本沒用到
		return null;
	}


	public static void main(String[] args) {

		Question_ClassificationJDBCDAO dao = new Question_ClassificationJDBCDAO();
		// 測試看看每個指令是否可以使用
		// 新增 OK
//		Question_ClassificationVO question_classificationVO1 = new Question_ClassificationVO();
//		question_classificationVO1.setQuec_name("問題分類回來嚕");
//		dao.insert(question_classificationVO1);
//		System.out.println("新增成功");

		// 修改 OK
//		Question_ClassificationVO question_classificationVO2 = new Question_ClassificationVO();
//		question_classificationVO2.setQuec_no("QC06");
//		question_classificationVO2.setQuec_name("嬰兒食品");
//		dao.update(question_classificationVO2);
//		System.out.println("修改成功");
		
		// 查詢 OK
//		Question_ClassificationVO question_classificationVO3 = dao.findByPrimaryKey("QC01");
//		System.out.print(question_classificationVO3.getQuec_no() + ",");
//		System.out.println(question_classificationVO3.getQuec_name());
//		System.out.println("---------------------");

		// 查詢全部 OK
//		List<Question_ClassificationVO> list = dao.getAll();
//		for (Question_ClassificationVO question_classificationVO : list) {
//			System.out.print(question_classificationVO.getQuec_no() + ",");
//			System.out.print(question_classificationVO.getQuec_name());
//			System.out.println();
//		}
		
	}


}
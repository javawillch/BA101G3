package com.question.model;

import java.util.*;
import java.sql.*;


public class AnswerJDBCDAO implements AnswerDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "ba101g3";
	private static final String PASSWORD = "baby";
	// 新增資料
	private static final String INSERT_STMT = "INSERT INTO answer (ans_no, mem_no, qu_no, ans_date, ans_cnt, ans_like, ans_is_hide) VALUES ('ANS'||LPAD(to_char(ans_no_seq.NEXTVAL),5,'0'), ?, ?, SYSDATE, ?, ?, ?)";
	// 查詢資料
	private static final String GET_ALL_STMT = "SELECT * FROM answer";
	private static final String GET_ONE_STMT = "SELECT * FROM answer WHERE ans_no = ?";
	// 修改資料
	private static final String UPDATE = "UPDATE answer set ans_cnt=?, ans_like=?, ans_is_hide=? WHERE ans_no = ?";
	
	private static final String GET_Answer_Reports_ByAns_no_STMT = "SELECT ans_no, mem_no, to_char(ansrpt_date,'yyyy-mm-dd hh:mm:ss') ansrpt_date, ansrpt_rsn, ansrpt_is_cert, ansrpt__unrsn FROM answer WHERE ans_no = ? ORDER BY ans_date DESC";

	@Override
	public void insert(AnswerVO answerVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			String[] cols = { "ans_no" }; // 有使用sequence產生編號的話才要寫
			pstmt = con.prepareStatement(INSERT_STMT, cols); // 有使用sequence產生編號的話才要寫第二個參數
			pstmt.setString(1, answerVO.getMem_no());
			pstmt.setString(2, answerVO.getQu_no());
			pstmt.setString(3, answerVO.getAns_cnt());
			pstmt.setInt(4, answerVO.getAns_like());
			pstmt.setString(5, answerVO.getAns_is_hide());
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
	public void update(AnswerVO answerVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, answerVO.getAns_cnt());
			pstmt.setInt(2, answerVO.getAns_like());
			pstmt.setString(3, answerVO.getAns_is_hide());
			pstmt.setString(4, answerVO.getAns_no());

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
	public AnswerVO findByPrimaryKey(String ans_no) {

		AnswerVO answerVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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


	public static void main(String[] args) {

		AnswerJDBCDAO dao = new AnswerJDBCDAO();
		// 測試看看每個指令是否可以使用
		// 新增 OK
//		AnswerVO answerVO1 = new AnswerVO();
//		answerVO1.setMem_no("M0000001");
//		answerVO1.setQu_no("QU000002");
//		answerVO1.setAns_cnt("我是這樣覺得拉2");
//		answerVO1.setAns_like(413);
//		answerVO1.setAns_is_hide("0");
//		dao.insert(answerVO1);
//		System.out.println("新增回答成功");

		// 修改 OK
//		AnswerVO answerVO2 = new AnswerVO();
//		answerVO2.setAns_no("ANS00003");
//		answerVO2.setAns_cnt("我改我改我改改改");
//		answerVO2.setAns_like(500);
//		answerVO2.setAns_is_hide("0");
//		dao.update(answerVO2);
//		System.out.println("修改成功");

		// 查詢  OK
//		AnswerVO answerVO3 = dao.findByPrimaryKey("ANS00003");
//		System.out.print(answerVO3.getAns_no() + ",");
//		System.out.print(answerVO3.getMem_no() + ",");
//		System.out.print(answerVO3.getQu_no() + ",");
//		System.out.print(answerVO3.getAns_date() + ",");
//		System.out.print(answerVO3.getAns_cnt() + ",");
//		System.out.print(answerVO3.getAns_like() + ",");
//		System.out.println(answerVO3.getAns_is_hide());
//		System.out.println("---------------------");

		// 查詢全部 OK
//		List<AnswerVO> list = dao.getAll();
//		for (AnswerVO answerVO : list) {
//			System.out.print(answerVO.getAns_no() + ",");
//			System.out.print(answerVO.getMem_no() + ",");
//			System.out.print(answerVO.getQu_no() + ",");
//			System.out.print(answerVO.getAns_date() + ",");
//			System.out.print(answerVO.getAns_cnt() + ",");
//			System.out.print(answerVO.getAns_like() + ",");
//			System.out.print(answerVO.getAns_is_hide());
//			System.out.println();
//		}
		
	}
}
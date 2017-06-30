package com.article.model;

import java.util.*;
import java.sql.*;


public class Article_Message_ReportJDBCDAO implements Article_Message_ReportDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "ba101g3";
	private static final String PASSWORD = "baby";
	// 新增資料
	private static final String INSERT_STMT = "INSERT INTO article_message_report (amsg_no, mem_no, amrpt_date, amrpt_rsn, amrpt_is_cert, amrpt_unrsn ) VALUES (?, ?, SYSDATE, ?, '0', null)";
	// 查詢資料
	private static final String GET_ALL_STMT = "SELECT * FROM article_message_report";
	private static final String GET_ONE_STMT = "SELECT * FROM article_message_report WHERE amsg_no = ? and mem_no=? ";
	// 修改資料
	private static final String UPDATE_STMT = "UPDATE article_message_report set amrpt_is_cert=?, amrpt_unrsn=? WHERE amsg_no=? AND mem_no=?";

	@Override
	public void insert(Article_Message_ReportVO article_message_reportVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT); 
			pstmt.setString(1, article_message_reportVO.getAmsg_no());
			pstmt.setString(2, article_message_reportVO.getMem_no());
			pstmt.setString(3, article_message_reportVO.getAmrpt_rsn());

			
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

	@Override()
	public void update(Article_Message_ReportVO answer_message_reportVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, answer_message_reportVO.getAmrpt_is_cert());
			pstmt.setString(2, answer_message_reportVO.getAmrpt_unrsn());
			pstmt.setString(3, answer_message_reportVO.getAmsg_no());
			pstmt.setString(4, answer_message_reportVO.getMem_no());

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
	public Article_Message_ReportVO findByPrimaryKey(String amsg_no, String mem_no) {

		Article_Message_ReportVO answer_reportVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, amsg_no);
			pstmt.setString(2, mem_no);


			rs = pstmt.executeQuery();

			while (rs.next()) {
				answer_reportVO = new Article_Message_ReportVO();
				answer_reportVO.setAmsg_no(rs.getString("amsg_no"));
				answer_reportVO.setMem_no(rs.getString("mem_no"));
				answer_reportVO.setAmrpt_date(rs.getTimestamp("amrpt_date"));
				answer_reportVO.setAmrpt_rsn(rs.getString("amrpt_rsn"));
				answer_reportVO.setAmrpt_is_cert(rs.getString("amrpt_is_cert"));
				answer_reportVO.setAmrpt_unrsn(rs.getString("amrpt_unrsn"));
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
		return answer_reportVO;
	}

	@Override
	public List<Article_Message_ReportVO> getAll() {
		List<Article_Message_ReportVO> list = new ArrayList<Article_Message_ReportVO>();
		Article_Message_ReportVO answer_reportVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				answer_reportVO = new Article_Message_ReportVO();
				answer_reportVO.setAmsg_no(rs.getString("amsg_no"));
				answer_reportVO.setMem_no(rs.getString("mem_no"));
				answer_reportVO.setAmrpt_date(rs.getTimestamp("amrpt_date"));
				answer_reportVO.setAmrpt_rsn(rs.getString("amrpt_rsn"));
				answer_reportVO.setAmrpt_is_cert(rs.getString("amrpt_is_cert"));
				answer_reportVO.setAmrpt_unrsn(rs.getString("amrpt_unrsn"));
				list.add(answer_reportVO); // Store the row in the list
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

		Article_Message_ReportJDBCDAO dao = new Article_Message_ReportJDBCDAO();
		// 測試看看每個指令是否可以使用
		// 新增 OK
		Article_Message_ReportVO article_message_reportVO1 = new Article_Message_ReportVO();
		article_message_reportVO1.setAmsg_no("AMSG0002");
		article_message_reportVO1.setMem_no("M0000006");
		article_message_reportVO1.setAmrpt_rsn("不雅文字，新增測試");
		dao.insert(article_message_reportVO1);
		System.out.println("新增一筆檢舉成功");

		// 修改 OK
//		Article_Message_ReportVO article_message_reportVO2 = new Article_Message_ReportVO();
//		article_message_reportVO2.setAmsg_no("AMSG0002");
//		article_message_reportVO2.setMem_no("M0000006");
//		article_message_reportVO2.setAmrpt_is_cert("1");
//		article_message_reportVO2.setAmrpt_unrsn("通過");
//		dao.update(article_message_reportVO2);

		// 查詢 OK
//		Article_Message_ReportVO article_message_reportVO3 = dao.findByPrimaryKey("AMSG0002","M0000004");
//		System.out.print(article_message_reportVO3.getAmsg_no() + ",");
//		System.out.print(article_message_reportVO3.getMem_no() + ",");
//		System.out.print(article_message_reportVO3.getAmrpt_date() + ",");
//		System.out.print(article_message_reportVO3.getAmrpt_rsn() + ",");
//		System.out.print(article_message_reportVO3.getAmrpt_is_cert() + ",");
//		System.out.println(article_message_reportVO3.getAmrpt_unrsn());
//		System.out.println("---------------------");

		// 查詢全部
//		List<Article_Message_ReportVO> list = dao.getAll();
//		for (Article_Message_ReportVO article_message_reportVO : list) {
//			System.out.print(article_message_reportVO.getAmsg_no() + ",");
//			System.out.print(article_message_reportVO.getMem_no() + ",");
//			System.out.print(article_message_reportVO.getAmrpt_date() + ",");
//			System.out.print(article_message_reportVO.getAmrpt_rsn() + ",");
//			System.out.print(article_message_reportVO.getAmrpt_is_cert() + ",");
//			System.out.print(article_message_reportVO.getAmrpt_unrsn());
//			System.out.println();
//		}
		

	}
}
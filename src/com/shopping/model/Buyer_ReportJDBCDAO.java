package com.shopping.model;

import java.util.*;
import java.sql.*;


public class Buyer_ReportJDBCDAO implements Buyer_ReportDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "ba101g3";
	private static final String PASSWORD = "baby";
	// 新增資料
	private static final String INSERT_STMT = "INSERT INTO buyer_report (ord_no, mem_no, buyrpt_date, buyrpt_rsn, buyrpt_is_cert, buyrpt_unrsn) VALUES (?, ?, SYSDATE, ?, '0', ?)";
	// 查詢資料
	private static final String GET_ALL_STMT = "SELECT * FROM buyer_report";
	private static final String GET_ONE_STMT = "SELECT * FROM buyer_report WHERE ord_no = ? AND mem_no = ?";
	// 修改資料
	private static final String UPDATE = "UPDATE buyer_report set buyrpt_date=?, buyrpt_rsn=?, buyrpt_is_cert=?, buyrpt_unrsn=? where ord_no=? AND mem_no=?";
	//訂單編號查賣家會員編號
	private static final String FIND_SELLER_MEM_NO_BY_ORDER_NO = "SELECT mem_no FROM product WHERE pro_no IN (SELECT pro_noFROM orderlist WHERE ord_no = ?)";

	@Override
	public void insert(Buyer_ReportVO buyer_reportVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			String[] cols = { "ord_no" }; // 有使用sequence產生編號的話才要寫
			pstmt = con.prepareStatement(INSERT_STMT, cols); // 有使用sequence產生編號的話才要寫第二個參數
			pstmt.setString(1, buyer_reportVO.getOrd_no());
			pstmt.setString(2, buyer_reportVO.getMem_no());
			pstmt.setString(3, buyer_reportVO.getBuyrpt_rsn());
			pstmt.setString(4, buyer_reportVO.getBuyrpt_unrsn());

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
	public void update(Buyer_ReportVO buyer_reportVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setTimestamp(1, buyer_reportVO.getBuyrpt_date());
			pstmt.setString(2, buyer_reportVO.getBuyrpt_rsn());
			pstmt.setString(3, buyer_reportVO.getBuyrpt_is_cert());
			pstmt.setString(4, buyer_reportVO.getBuyrpt_unrsn());
			pstmt.setString(5, buyer_reportVO.getOrd_no());
			pstmt.setString(6, buyer_reportVO.getMem_no());

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
	public Buyer_ReportVO findByPrimaryKey(String ord_no, String mem_no) {

		Buyer_ReportVO buyer_reportVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ord_no);
			pstmt.setString(2, mem_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				buyer_reportVO = new Buyer_ReportVO();
				buyer_reportVO.setOrd_no(rs.getString("ord_no"));
				buyer_reportVO.setMem_no(rs.getString("mem_no"));
				buyer_reportVO.setBuyrpt_date(rs.getTimestamp("buyrpt_date"));
				buyer_reportVO.setBuyrpt_rsn(rs.getString("buyrpt_rsn"));
				buyer_reportVO.setBuyrpt_is_cert(rs.getString("buyrpt_is_cert"));
				buyer_reportVO.setBuyrpt_unrsn(rs.getString("buyrpt_unrsn"));
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
		return buyer_reportVO;
	}

	@Override
	public List<Buyer_ReportVO> getAll() {
		List<Buyer_ReportVO> list = new ArrayList<Buyer_ReportVO>();
		Buyer_ReportVO buyer_reportVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				buyer_reportVO = new Buyer_ReportVO();
				buyer_reportVO.setOrd_no(rs.getString("ord_no"));
				buyer_reportVO.setMem_no(rs.getString("mem_no"));
				buyer_reportVO.setBuyrpt_date(rs.getTimestamp("buyrpt_date"));
				buyer_reportVO.setBuyrpt_rsn(rs.getString("buyrpt_rsn"));
				buyer_reportVO.setBuyrpt_is_cert(rs.getString("buyrpt_is_cert"));
				buyer_reportVO.setBuyrpt_unrsn(rs.getString("buyrpt_unrsn"));
				list.add(buyer_reportVO); // Store the row in the list
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

		Buyer_ReportJDBCDAO dao = new Buyer_ReportJDBCDAO();
		// 測試看看每個指令是否可以使用
		// 新增 OK
//		Buyer_ReportVO buyer_reportVO1 = new Buyer_ReportVO();
//		buyer_reportVO1.setOrd_no("ORD00002");
//		buyer_reportVO1.setMem_no("M0000001");
//		buyer_reportVO1.setBuyrpt_rsn("東西有缺陷");
//		dao.insert(buyer_reportVO1);

		// 修改 OK
//		Buyer_ReportVO buyer_reportVO2 = new Buyer_ReportVO();
//		buyer_reportVO2.setOrd_no("ORD00001");
//		buyer_reportVO2.setMem_no("M0000001");
//		buyer_reportVO2.setBuyrpt_date(java.sql.Timestamp.valueOf("2016-1-23 04:22:55"));
//		buyer_reportVO2.setBuyrpt_rsn("東西有缺陷");
//		buyer_reportVO2.setBuyrpt_is_cert("1");
//		buyer_reportVO2.setBuyrpt_unrsn("不能同意更多");
//		dao.update(buyer_reportVO2);

		// 查詢 OK
//		Buyer_ReportVO buyer_reportVO3 = dao.findByPrimaryKey("ORD00001", "M0000001");
//		System.out.print(buyer_reportVO3.getOrd_no() + ",");
//		System.out.print(buyer_reportVO3.getMem_no() + ",");
//		System.out.println(buyer_reportVO3.getBuyrpt_rsn());
//		System.out.println("---------------------");

		// 查詢全部 OK
//		List<Buyer_ReportVO> list = dao.getAll();
//		for (Buyer_ReportVO buyer_report : list) {
//			System.out.print(buyer_report.getOrd_no() + ",");
//			System.out.print(buyer_report.getMem_no()+ ",");
//			System.out.print(buyer_report.getBuyrpt_rsn());
//			System.out.println();
//		}
		
	}

	//透過訂單編號找賣家會員編號
	@Override
	public String findSellerMem_noByOrder_no(String ord_no) {
		String seller_mem_no = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_SELLER_MEM_NO_BY_ORDER_NO);

			pstmt.setString(1, ord_no);

			rs = pstmt.executeQuery();
			seller_mem_no = rs.getString("mem_no");


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
		return seller_mem_no;
	}
}
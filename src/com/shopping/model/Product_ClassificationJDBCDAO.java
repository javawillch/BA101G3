package com.shopping.model;

import java.util.*;
import java.sql.*;


public class Product_ClassificationJDBCDAO implements Product_ClassificationDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "ba101g3";
	private static final String PASSWORD = "baby";
	// 新增資料
	private static final String INSERT_STMT = "INSERT INTO product_classification (proc_no, proc_name) VALUES ('PC'||LPAD(to_char(proc_no_seq.NEXTVAL), 2, '0'), ?)";
	// 查詢資料
	private static final String GET_ALL_STMT = "SELECT proc_no , proc_name FROM product_classification";
	private static final String GET_ONE_STMT = "SELECT proc_no, proc_name FROM product_classification WHERE proc_no = ?";
	// 修改資料
	private static final String UPDATE = "UPDATE product_classification set proc_name=? WHERE proc_no = ?";

	@Override
	public void insert(Product_ClassificationVO product_classificationVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			String[] cols = { "proc_no" }; // 有使用sequence產生編號的話才要寫
			pstmt = con.prepareStatement(INSERT_STMT, cols); // 有使用sequence產生編號的話才要寫第二個參數
			pstmt.setString(1, product_classificationVO.getProc_name());

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
	public void update(Product_ClassificationVO product_classificationVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, product_classificationVO.getProc_name());
			pstmt.setString(2, product_classificationVO.getProc_no());

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
	public Product_ClassificationVO findByPrimaryKey(String proc_no) {

		Product_ClassificationVO product_classificationVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, proc_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				product_classificationVO = new Product_ClassificationVO();
				product_classificationVO.setProc_no(rs.getString("proc_no"));
				product_classificationVO.setProc_name(rs.getString("proc_name"));
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
		return product_classificationVO;
	}

	@Override
	public List<Product_ClassificationVO> getAll() {
		List<Product_ClassificationVO> list = new ArrayList<Product_ClassificationVO>();
		Product_ClassificationVO product_classificationVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				product_classificationVO = new Product_ClassificationVO();
				product_classificationVO.setProc_no(rs.getString("proc_no"));
				product_classificationVO.setProc_name(rs.getString("proc_name"));
				list.add(product_classificationVO); // Store the row in the list
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

		Product_ClassificationJDBCDAO dao = new Product_ClassificationJDBCDAO();
		// 測試看看每個指令是否可以使用
		// 新增 OK
//		Product_ClassificationVO product_classificationVO1 = new Product_ClassificationVO();
//		product_classificationVO1.setProc_name("會叫的玩具");
//		dao.insert(product_classificationVO1);

		// 修改 OK
//		Product_ClassificationVO product_classificationVO2 = new Product_ClassificationVO();
//		product_classificationVO2.setProc_no("PC02");
//		product_classificationVO2.setProc_name("修改看看");
//		dao.update(product_classificationVO2);


		// 查詢 OK
//		Product_ClassificationVO product_classificationVO3 = dao.findByPrimaryKey("PC04");
//		System.out.print(product_classificationVO3.getProc_no() + ",");
//		System.out.println(product_classificationVO3.getProc_name());
//		System.out.println("---------------------");

//		 查詢全部 OK
//		List<Product_ClassificationVO> list = dao.getAll();
//		for (Product_ClassificationVO proc : list) {
//			System.out.print(proc.getProc_no() + ",");
//			System.out.print(proc.getProc_name());
//			System.out.println();
//		}
		
	}

	@Override
	public Set<ProductVO> getProductsByProc_no(String proc_no) {
		// TODO Auto-generated method stub
		return null;
	}
}
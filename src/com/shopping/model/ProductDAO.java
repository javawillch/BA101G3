package com.shopping.model;

import java.util.*;
import java.sql.*;


public class ProductDAO implements ProductDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "ba101g3";
	private static final String PASSWORD = "baby";
	// 新增資料
	private static final String INSERT_STMT = "INSERT INTO product (pro_no, mem_no, proc_no, pro_date, pro_name, pro_price, pro_intro, pro_photo, pro_photo1, pro_photo2, pro_photo3, pro_photo4, pro_photo5, pro_stat, pro_pay, pro_get) VALUES ('PRO'||LPAD(to_char(pro_no_seq.NEXTVAL), 5, '0'), ?, ?, SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?, ?, '0', ?, ?)";
	// 查詢資料
	private static final String GET_ALL_STMT = "SELECT * FROM product";
	private static final String GET_ONE_STMT = "SELECT * FROM product WHERE pro_no = ?";
	// 刪除資料
	private static final String DELETE_BUYRPTs = "DELETE FROM buyer_report WHERE ord_no IN (SELECT ord_no FROM orderlist WHERE pro_no=?)";
	private static final String DELETE_ORDERLISTs = "DELETE FROM orderlist WHERE pro_no=?";
	private static final String DELETE_PRORPTs = "DELETE FROM product_report WHERE pro_no=?";
	private static final String DELETE_PMSGRPTs = "DELETE FROM product_message_report WHERE pmsg_no IN (SELECT pmsg_no FROM product_message WHERE pro_no=?)";
	private static final String DELETE_PMSGs = "DELETE FROM product_message WHERE pro_no=?";
	private static final String DELETE_SELLRATINGs = "DELETE FROM seller_rating WHERE pro_no=?";
	private static final String DELETE_SELLRPTs = "DELETE FROM seller_report WHERE pro_no=?";


	private static final String DELETE_PRO = "DELETE FROM product where pro_no = ?";	
	// 修改資料
	private static final String UPDATE = "UPDATE product set mem_no=?, proc_no=?, pro_date=?, pro_name=?, pro_price=?, pro_intro=?, pro_photo=?, pro_photo1=?, pro_photo2=?, pro_photo3=?, pro_photo4=?, pro_photo5=?, pro_stat=?, pro_pay=?, pro_get=? WHERE pro_no = ?";
	@Override
	public void insert(ProductVO productVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			String[] cols = { "pro_no" }; // 有使用sequence產生編號的話才要寫
			pstmt = con.prepareStatement(INSERT_STMT, cols); // 有使用sequence產生編號的話才要寫第二個參數
			pstmt.setString(1, productVO.getMem_no());
			pstmt.setString(2, productVO.getProc_no());
			pstmt.setString(3, productVO.getPro_name());
			pstmt.setInt(4, productVO.getPro_price());
			pstmt.setString(5, productVO.getPro_intro());
			pstmt.setBytes(6, productVO.getPro_photo());
			pstmt.setBytes(7, productVO.getPro_photo1());
			pstmt.setBytes(8, productVO.getPro_photo2());
			pstmt.setBytes(9, productVO.getPro_photo3());
			pstmt.setBytes(10, productVO.getPro_photo4());
			pstmt.setBytes(11, productVO.getPro_photo5());
			pstmt.setString(12, productVO.getPro_pay());
			pstmt.setString(13, productVO.getPro_get());

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
	public void update(ProductVO productVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, productVO.getMem_no());
			pstmt.setString(2, productVO.getProc_no());
			pstmt.setTimestamp(3, productVO.getPro_date());
			pstmt.setString(4, productVO.getPro_name());
			pstmt.setInt(5, productVO.getPro_price());
			pstmt.setString(6, productVO.getPro_intro());
			pstmt.setBytes(7, productVO.getPro_photo());
			pstmt.setBytes(8, productVO.getPro_photo1());
			pstmt.setBytes(9, productVO.getPro_photo2());
			pstmt.setBytes(10, productVO.getPro_photo3());
			pstmt.setBytes(11, productVO.getPro_photo4());
			pstmt.setBytes(12, productVO.getPro_photo5());
			pstmt.setString(13, productVO.getPro_stat());
			pstmt.setString(14, productVO.getPro_pay());
			pstmt.setString(15, productVO.getPro_get());
			pstmt.setString(16, productVO.getPro_no());

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
	public void delete(String pro_no) {
		int updateCount_BUYER_REPORTs = 0;
		int updateCount_ORDERLISTs = 0;
		int updateCount_PRODUCT_REPORTs = 0;
		int updateCount_PRODUCT_MESSAGE_REPORTs = 0;
		int updateCount_PRODUCT_MESSAGEs = 0;
		int updateCount_SELLER_REPORTs = 0;
		int updateCount_SELLER_RATINGs = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);

			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);

			// 先刪除商品評分、商品檢舉、商品訂單、商品留言、賣家評分、賣家檢舉(多)
			pstmt = con.prepareStatement(DELETE_BUYRPTs);
			pstmt.setString(1, pro_no);
			updateCount_BUYER_REPORTs = pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(DELETE_ORDERLISTs);
			pstmt.setString(1, pro_no);
			updateCount_ORDERLISTs = pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(DELETE_PRORPTs);
			pstmt.setString(1, pro_no);
			updateCount_PRODUCT_REPORTs = pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(DELETE_PMSGRPTs);
			pstmt.setString(1, pro_no);
			updateCount_PRODUCT_MESSAGE_REPORTs = pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(DELETE_PMSGs);
			pstmt.setString(1, pro_no);
			updateCount_PRODUCT_MESSAGEs = pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(DELETE_SELLRATINGs);
			pstmt.setString(1, pro_no);
			updateCount_SELLER_REPORTs = pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(DELETE_SELLRPTs);
			pstmt.setString(1, pro_no);
			updateCount_SELLER_RATINGs = pstmt.executeUpdate();
			
			// 再刪除商品(一)
			pstmt = con.prepareStatement(DELETE_PRO);
			pstmt.setString(1, pro_no);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除商品" + pro_no + "時,共有商品訂單" + updateCount_ORDERLISTs 
					+ "個、買家檢舉" + updateCount_BUYER_REPORTs + "個、商品檢舉" + updateCount_PRODUCT_REPORTs
					+ "個、商品留言" + updateCount_PRODUCT_MESSAGEs + "個、商品留言檢舉" + updateCount_PRODUCT_MESSAGE_REPORTs
					+ "個、賣家檢舉" + updateCount_SELLER_REPORTs + "個、賣家評分" + updateCount_SELLER_RATINGs  + "個，同時被刪除");
			
			// Handle any DRIVER errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database DRIVER. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public ProductVO findByPrimaryKey(String pro_no) {

		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, pro_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				productVO = new ProductVO();
				productVO.setPro_no(rs.getString("pro_no"));
				productVO.setMem_no(rs.getString("mem_no"));
				productVO.setProc_no(rs.getString("proc_no"));
				productVO.setPro_date(rs.getTimestamp("pro_date"));
				productVO.setPro_name(rs.getString("pro_name"));
				productVO.setPro_price(rs.getInt("pro_price"));
				productVO.setPro_intro(rs.getString("pro_intro"));
				productVO.setPro_photo(rs.getBytes("pro_photo"));
				productVO.setPro_photo1(rs.getBytes("pro_photo1"));
				productVO.setPro_photo2(rs.getBytes("pro_photo2"));
				productVO.setPro_photo3(rs.getBytes("pro_photo3"));
				productVO.setPro_photo4(rs.getBytes("pro_photo4"));
				productVO.setPro_photo5(rs.getBytes("pro_photo5"));
				productVO.setPro_stat(rs.getString("pro_stat"));
				productVO.setPro_pay(rs.getString("pro_pay"));
				productVO.setPro_get(rs.getString("pro_get"));
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
		return productVO;
	}

	@Override
	public List<ProductVO> getAll() {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				productVO = new ProductVO();
				productVO.setPro_no(rs.getString("pro_no"));
				productVO.setMem_no(rs.getString("mem_no"));
				productVO.setProc_no(rs.getString("proc_no"));
				productVO.setPro_date(rs.getTimestamp("pro_date"));
				productVO.setPro_name(rs.getString("pro_name"));
				productVO.setPro_price(rs.getInt("pro_price"));
				productVO.setPro_intro(rs.getString("pro_intro"));
				productVO.setPro_photo(rs.getBytes("pro_photo"));
				productVO.setPro_photo1(rs.getBytes("pro_photo1"));
				productVO.setPro_photo2(rs.getBytes("pro_photo2"));
				productVO.setPro_photo3(rs.getBytes("pro_photo3"));
				productVO.setPro_photo4(rs.getBytes("pro_photo4"));
				productVO.setPro_photo5(rs.getBytes("pro_photo5"));
				productVO.setPro_stat(rs.getString("pro_stat"));
				productVO.setPro_pay(rs.getString("pro_pay"));
				productVO.setPro_get(rs.getString("pro_get"));
				list.add(productVO); // Store the row in the list
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

	
	public static void readPictureFromDB(byte[] bytes){
		
	}

}
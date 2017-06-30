package com.shopping.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.shopping.model.ProductVO;

import java.sql.*;


public class Product_ClassificationDAO implements Product_ClassificationDAO_interface {
	
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static{
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	// 新增資料
	private static final String INSERT_STMT = "INSERT INTO product_classification (proc_no, proc_name) VALUES ('PC'||LPAD(to_char(proc_no_seq.NEXTVAL), 2, '0'), ?)";
	// 查詢資料
	private static final String GET_ALL_STMT = "SELECT proc_no , proc_name FROM product_classification";
	private static final String GET_ONE_STMT = "SELECT proc_no, proc_name FROM product_classification where proc_no = ?";
	// 修改資料
	private static final String UPDATE = "UPDATE product_classification SET proc_name=? WHERE proc_no = ?";
	//在一找多的資料
	private static final String GET_Products_ByProc_no_STMT = "SELECT * FROM product WHERE proc_no = ? ORDER BY pro_no";

	@Override
	public void insert(Product_ClassificationVO product_classificationVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			String[] cols = { "proc_no" }; // 有使用sequence產生編號的話才要寫
			pstmt = con.prepareStatement(INSERT_STMT, cols); // 有使用sequence產生編號的話才要寫第二個參數
			pstmt.setString(1, product_classificationVO.getProc_name());

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
	public void update(Product_ClassificationVO product_classificationVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, product_classificationVO.getProc_name());
			pstmt.setString(2, product_classificationVO.getProc_no());

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
	public Product_ClassificationVO findByPrimaryKey(String proc_no) {

		Product_ClassificationVO product_classificationVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, proc_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				product_classificationVO = new Product_ClassificationVO();
				product_classificationVO.setProc_no(rs.getString("proc_no"));
				product_classificationVO.setProc_name(rs.getString("proc_name"));
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				product_classificationVO = new Product_ClassificationVO();
				product_classificationVO.setProc_no(rs.getString("proc_no"));
				product_classificationVO.setProc_name(rs.getString("proc_name"));
				list.add(product_classificationVO); // Store the row in the list
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
	public Set<ProductVO> getProductsByProc_no(String proc_no) {
		Set<ProductVO> set = new LinkedHashSet<ProductVO>();
		ProductVO productVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Products_ByProc_no_STMT);
			pstmt.setString(1, proc_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				productVO = new ProductVO();

				productVO.setPro_no(rs.getString("pro_no"));  // Not Null (PK)
				productVO.setMem_no(rs.getString("mem_no"));  // Not Null (FK)
				productVO.setProc_no(rs.getString("proc_no")); // Not Null (FK)
				productVO.setPro_date(rs.getTimestamp("pro_date"));   // Not Null
				productVO.setPro_name(rs.getString("pro_name"));   // Not Null
				productVO.setPro_price(rs.getInt("pro_price")); // Not Null
				productVO.setPro_intro(rs.getString("pro_intro")); // Not Null
				productVO.setPro_photo(rs.getBytes("pro_photo")); // Not Null
				productVO.setPro_photo1(rs.getBytes("pro_photo1"));
				productVO.setPro_photo2(rs.getBytes("pro_photo2"));
				productVO.setPro_photo3(rs.getBytes("pro_photo3"));
				productVO.setPro_photo4(rs.getBytes("pro_photo4"));
				productVO.setPro_photo5(rs.getBytes("pro_photo5"));
				productVO.setPro_stat(rs.getString("pro_stat"));   // Not Null 0：上架中 1：下架 2：完成交易未付款 3：完成交易已付款
				productVO.setPro_pay(rs.getString("pro_pay"));     // Not Null 0：貨到付款 1：ATM轉帳 2：兩者皆可
				productVO.setPro_get(rs.getString("pro_get"));     // Not Null 0：便利商店取貨 1：快遞送貨 2：兩者皆可
				set.add(productVO); // Store the row in the vector
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
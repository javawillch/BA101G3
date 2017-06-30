package com.article.model;

import java.util.*;
import java.sql.*;


public class Article_ClassificationJDBCDAO implements Article_ClassificationDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "ba101g3";
	private static final String PASSWORD = "baby";
	// 新增資料
	private static final String INSERT_STMT = "INSERT INTO article_classification (artc_no, artc_name) VALUES ('AC'||LPAD(to_char(artc_no_seq.NEXTVAL),2,'0'), ?)";
	// 查詢資料
	private static final String GET_ALL_STMT = "SELECT * FROM article_classification";
	private static final String GET_ONE_STMT = "SELECT * FROM article_classification WHERE artc_no = ?";
	// 修改資料
	private static final String UPDATE = "UPDATE article_classification set artc_name=? WHERE artc_no = ?";

	@Override
	public void insert(Article_ClassificationVO article_classificationVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			String[] cols = { "artc_no" }; // 有使用sequence產生編號的話才要寫
			pstmt = con.prepareStatement(INSERT_STMT, cols); // 有使用sequence產生編號的話才要寫第二個參數
			pstmt.setString(1, article_classificationVO.getArtc_name());

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
	public void update(Article_ClassificationVO article_classificationVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, article_classificationVO.getArtc_name());
			pstmt.setString(2, article_classificationVO.getArtc_no());

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
	public Article_ClassificationVO findByPrimaryKey(String artc_no) {

		Article_ClassificationVO article_classificationVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, artc_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				article_classificationVO = new Article_ClassificationVO();
				article_classificationVO.setArtc_no(rs.getString("artc_no"));
				article_classificationVO.setArtc_name(rs.getString("artc_name"));
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
		return article_classificationVO;
	}

	@Override
	public List<Article_ClassificationVO> getAll() {
		List<Article_ClassificationVO> list = new ArrayList<Article_ClassificationVO>();
		Article_ClassificationVO article_classificationVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				article_classificationVO = new Article_ClassificationVO();
				article_classificationVO.setArtc_no(rs.getString("artc_no"));
				article_classificationVO.setArtc_name(rs.getString("artc_name"));
				list.add(article_classificationVO); // Store the row in the list
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

		Article_ClassificationJDBCDAO dao = new Article_ClassificationJDBCDAO();
		// 測試看看每個指令是否可以使用
		// 新增 OK
//		Article_ClassificationVO article_classificationVO1 = new Article_ClassificationVO();
//		article_classificationVO1.setArtc_name("測試分類");
//		dao.insert(article_classificationVO1);
//		System.out.println("新增分類成功");

		// 修改  OK
//		Article_ClassificationVO article_classificationVO2 = new Article_ClassificationVO();
//		article_classificationVO2.setArtc_no("AC07");
//		article_classificationVO2.setArtc_name("修改看看");
//		dao.update(article_classificationVO2);
//		System.out.println("修改看看成功");

		// 查詢 OK
//		Article_ClassificationVO article_classificationVO3 = dao.findByPrimaryKey("AC01");
//		System.out.print(article_classificationVO3.getArtc_no() + ",");
//		System.out.println(article_classificationVO3.getArtc_name());
//		System.out.println("---------------------");

		// 查詢全部  OK
//		List<Article_ClassificationVO> list = dao.getAll();
//		for (Article_ClassificationVO article_classificationVO : list) {
//			System.out.print(article_classificationVO.getArtc_no() + ",");
//			System.out.print(article_classificationVO.getArtc_name());
//			System.out.println();
//		}
		
	}

	@Override
	public Set<ArticleVO> getArticlesByArtc_no(String artc_no) {
		// TODO Auto-generated method stub
		return null;
	}
}
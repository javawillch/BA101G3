package com.article.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.article.model.ArticleVO;

import java.sql.*;


public class Article_ClassificationDAO implements Article_ClassificationDAO_interface {

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
	private static final String INSERT_STMT = "INSERT INTO article_classification (artc_no, artc_name) VALUES (LPAD(to_char(artc_no_seq.NEXTVAL),4,'0'), ?)";
	// 查詢資料
	private static final String GET_ALL_STMT = "SELECT artc_no , artc_name FROM article_classification";
	private static final String GET_ONE_STMT = "SELECT artc_no , artc_name FROM article_classification where artc_no = ?";
	// 刪除資料
	private static final String DELETE_ARTICLEs = "DELETE FROM article WHERE artc_no = ?";	
	private static final String DELETE_ARTC = "DELETE FROM article_classification WHERE artc_no = ?";	
	// 修改資料
	private static final String UPDATE = "UPDATE article_classification set artc_name=? WHERE artc_no = ?";
	//在一找多的資料
	private static final String GET_Articles_ByArtc_no_STMT = "SELECT art_no, mem_no, artc_no, art_title,to_char(art_date,'yyyy-mm-dd hh:mm:ss') art_date, art_cnt, art_vcnt FROM article where artc_no = ? order by art_no";

	@Override
	public void insert(Article_ClassificationVO article_classificationVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			String[] cols = { "artc_no" }; // 有使用sequence產生編號的話才要寫
			pstmt = con.prepareStatement(INSERT_STMT, cols); // 有使用sequence產生編號的話才要寫第二個參數
			pstmt.setString(1, article_classificationVO.getArtc_name());

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
	public void update(Article_ClassificationVO article_classificationVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, article_classificationVO.getArtc_name());
			pstmt.setString(2, article_classificationVO.getArtc_no());

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

//	@Override
//	public void delete(String artc_no) {
//		int updateCount_ARTICLEs = 0;
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//			con = ds.getConnection();
//			// 1●設定於 pstm.executeUpdate()之前
//			con.setAutoCommit(false);
//
//			// 先刪除文章(多) 
//			pstmt = con.prepareStatement(DELETE_ARTICLEs);
//			pstmt.setString(1, artc_no);
//			updateCount_ARTICLEs = pstmt.executeUpdate();
//			// 再刪除文章類別(一)
//			pstmt = con.prepareStatement(DELETE_ARTC);
//			pstmt.setString(1, artc_no);
//			pstmt.executeUpdate();
//
//			// 2●設定於 pstm.executeUpdate()之後
//			con.commit();
//			con.setAutoCommit(true);
//			System.out.println("刪除文章類別編號" + artc_no + "時,共有商品" + updateCount_ARTICLEs
//					+ "個同時被刪除");
//			
//			// Handle any DRIVER errors
//		} catch (SQLException se) {
//			if (con != null) {
//				try {
//					// 3●設定於當有exception發生時之catch區塊內
//					con.rollback();
//				} catch (SQLException excep) {
//					throw new RuntimeException("rollback error occured. "
//							+ excep.getMessage());
//				}
//			}
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//
//	}

	@Override
	public Article_ClassificationVO findByPrimaryKey(String artc_no) {

		Article_ClassificationVO article_classificationVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, artc_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				article_classificationVO = new Article_ClassificationVO();
				article_classificationVO.setArtc_no(rs.getString("artc_no"));
				article_classificationVO.setArtc_name(rs.getString("artc_name"));
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				article_classificationVO = new Article_ClassificationVO();
				article_classificationVO.setArtc_no(rs.getString("artc_no"));
				article_classificationVO.setArtc_name(rs.getString("artc_name"));
				list.add(article_classificationVO); // Store the row in the list
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
	public Set<ArticleVO> getArticlesByArtc_no(String artc_no) {
		Set<ArticleVO> set = new LinkedHashSet<ArticleVO>();
		ArticleVO articleVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Articles_ByArtc_no_STMT);
			pstmt.setString(1, artc_no);
			rs = pstmt.executeQuery();
		//	art_no, mem_no,artc_no, art_title,to_char(qu_date,'yyyy-mm-dd hh:mm:ss') art_date, art_cnt
			while (rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArt_no(rs.getString("art_no"));
				articleVO.setMem_no(rs.getString("mem_no"));
				articleVO.setArt_title(rs.getString("art_title"));
				articleVO.setArtc_no(rs.getString("artc_no"));
				articleVO.setArt_date(rs.getTimestamp("Art_date"));
				articleVO.setArt_cnt(rs.getString("art_cnt"));
				articleVO.setArt_vcnt(rs.getInt("art_vcnt"));
				set.add(articleVO); // Store the row in the vector
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
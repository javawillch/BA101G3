package com.article.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.*;


public class ArticleDAO implements ArticleDAO_interface {
	
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
	private static final String INSERT_STMT = "INSERT INTO article (art_no, mem_no, artc_no, art_title, art_date, art_cnt, art_vcnt) VALUES (('AT'||LPAD(to_char(art_no_seq.NEXTVAL),6,'0')), ?,?,?,?,?,?)";
	// 查詢資料
	private static final String GET_ALL_STMT = "SELECT * FROM article ORDER BY art_no DESC";
	private static final String GET_ONE_STMT = "SELECT * FROM article WHERE art_no = ?";
	// 刪除資料
	private static final String DELETE = "DELETE FROM article WHERE art_no = ?";	
	// 修改資料
	private static final String UPDATE = "UPDATE article SET mem_no= ?, artc_no= ?, art_title= ?, art_date= ?, art_cnt= ?, art_vcnt= ? WHERE art_no = ?";
	//增加瀏覽數
	private static final String UPDATE_Art_vcnt = "UPDATE article SET art_vcnt= ? WHERE art_no = ?";
	//查某類所有文章
	private static final String GET_Articles_ByArtc_no_STMT = "SELECT art_no, mem_no, artc_no, art_title,to_char(art_date,'yyyy-mm-dd hh:mm:ss') art_date, art_cnt, art_vcnt FROM article where artc_no = ? ORDER BY art_no DESC";
	//關鍵字搜尋
	private static final String SEARCH = "SELECT * FROM article WHERE (art_cnt LIKE ? OR art_title LIKE ?) ";
	//查某類所有文章
	private static final String GET_Articles_ByMem_no_STMT = "SELECT art_no, mem_no, artc_no, art_title,to_char(art_date,'yyyy-mm-dd hh:mm:ss') art_date, art_cnt, art_vcnt FROM article where mem_no = ? ORDER BY art_no DESC";
	
	@Override
	public void insert(ArticleVO articleVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			String[] cols = { "art_no" }; // 有使用sequence產生編號的話才要寫
			pstmt = con.prepareStatement(INSERT_STMT, cols); // 有使用sequence產生編號的話才要寫第二個參數
			pstmt.setString(1, articleVO.getMem_no());
			pstmt.setString(2, articleVO.getArtc_no());
			pstmt.setString(3, articleVO.getArt_title());
			pstmt.setTimestamp(4, articleVO.getArt_date());
			pstmt.setString(5, articleVO.getArt_cnt());
			pstmt.setInt(6, articleVO.getArt_vcnt());
			
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
	public void update(ArticleVO articleVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, articleVO.getMem_no());
			pstmt.setString(2, articleVO.getArtc_no());
			pstmt.setString(3, articleVO.getArt_title());
			pstmt.setTimestamp(4, articleVO.getArt_date());
			pstmt.setString(5, articleVO.getArt_cnt());
			pstmt.setInt(6, articleVO.getArt_vcnt());
			pstmt.setString(7, articleVO.getArt_no());
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
	public void delete(String art_no) {
		int updateCount_PRODUCTs = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);

			// 先刪除文章留言(多) --->尚未建product，因此先註解
//			pstmt = con.prepareStatement(DELETE_PRODUCTs);
//			pstmt.setString(1, proc_no);
//			updateCount_PRODUCTs = pstmt.executeUpdate();
			// 再刪除文章(一)
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, art_no);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除商品類別編號" + art_no + "時,共有商品" + updateCount_PRODUCTs
					+ "個同時被刪除");
			
			// Handle any DRIVER errors
		}  catch (SQLException se) {
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
	public ArticleVO findByPrimaryKey(String art_no) {

		ArticleVO articleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, art_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArt_no(rs.getString("art_no"));
				articleVO.setMem_no(rs.getString("mem_no"));
				articleVO.setArtc_no(rs.getString("artc_no"));
				articleVO.setArt_title(rs.getString("art_title"));
				articleVO.setArt_date(rs.getTimestamp("art_date"));
				articleVO.setArt_cnt(rs.getString("art_cnt"));
				articleVO.setArt_vcnt(rs.getInt("art_vcnt"));
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
		return articleVO;
	}

	@Override
	public List<ArticleVO> getAll() {
		List<ArticleVO> list = new ArrayList<ArticleVO>();
		ArticleVO articleVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArt_no(rs.getString("art_no"));
				articleVO.setMem_no(rs.getString("mem_no"));
				articleVO.setArtc_no(rs.getString("artc_no"));
				articleVO.setArt_title(rs.getString("art_title"));
				articleVO.setArt_date(rs.getTimestamp("art_date"));
				articleVO.setArt_cnt(rs.getString("art_cnt"));
				articleVO.setArt_vcnt(rs.getInt("art_vcnt"));
				list.add(articleVO); // Store the row in the list
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
	public void update_art_vcnt(Integer art_vcnt , String art_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_Art_vcnt);

			pstmt.setInt(1, art_vcnt);
			pstmt.setString(2, art_no);
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
	public List<ArticleVO> getAllByArtc_no(String artc_no) {
		List<ArticleVO> list = new ArrayList<ArticleVO>();
		ArticleVO articleVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Articles_ByArtc_no_STMT);
			pstmt.setString(1, artc_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArt_no(rs.getString("art_no"));
				articleVO.setMem_no(rs.getString("mem_no"));
				articleVO.setArtc_no(rs.getString("artc_no"));
				articleVO.setArt_title(rs.getString("art_title"));
				articleVO.setArt_date(rs.getTimestamp("art_date"));
				articleVO.setArt_cnt(rs.getString("art_cnt"));
				articleVO.setArt_vcnt(rs.getInt("art_vcnt"));
				list.add(articleVO); // Store the row in the list
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
	public List<ArticleVO> searchPattern(String pattern) {
		List<ArticleVO> list = new ArrayList<ArticleVO>();
		ArticleVO articleVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCH);
			pstmt.setString(1, pattern);
			pstmt.setString(2, pattern);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArt_no(rs.getString("art_no"));
				articleVO.setMem_no(rs.getString("mem_no"));
				articleVO.setArtc_no(rs.getString("artc_no"));
				articleVO.setArt_title(rs.getString("art_title"));
				articleVO.setArt_date(rs.getTimestamp("art_date"));
				articleVO.setArt_cnt(rs.getString("art_cnt"));
				articleVO.setArt_vcnt(rs.getInt("art_vcnt"));
				list.add(articleVO); // Store the row in the list
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
	public List<ArticleVO> getAllByMem_no(String mem_no) {
		List<ArticleVO> list = new ArrayList<ArticleVO>();
		ArticleVO articleVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Articles_ByMem_no_STMT);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArt_no(rs.getString("art_no"));
				articleVO.setMem_no(rs.getString("mem_no"));
				articleVO.setArtc_no(rs.getString("artc_no"));
				articleVO.setArt_title(rs.getString("art_title"));
				articleVO.setArt_date(rs.getTimestamp("art_date"));
				articleVO.setArt_cnt(rs.getString("art_cnt"));
				articleVO.setArt_vcnt(rs.getInt("art_vcnt"));
				list.add(articleVO); // Store the row in the list
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
	
}
package com.article.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.*;


public class Article_FavoriteDAO implements Article_FavoriteDAO_interface {
	// �@�����ε{����,�w��@�Ӹ�Ʈw ,�@�Τ@��DataSource�Y�i
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	// �s�W���
	private static final String INSERT_STMT = "INSERT INTO article_favorite (mem_no, art_no) VALUES (?, ?)";
	// �d�߸��
	private static final String GET_ALL_STMT = "SELECT mem_no, art_no FROM article_favorite";
	private static final String GET_ONE_STMT = "SELECT mem_no, art_no FROM article_favorite WHERE mem_no = ? and art_no =?";
	private static final String GET_BY_MEM_NO_STMT = "SELECT mem_no, art_no FROM article_favorite WHERE mem_no =?";
	private static final String GET_BY_ART_NO_STMT = "SELECT mem_no, art_no FROM article_favorite WHERE art_no =?";
	// �R�����
	private static final String DELETE_ARTICLE_FAVORITE = "DELETE FROM article_favorite WHERE mem_no = ? and art_no =?";	

	@Override
	public void insert(Article_FavoriteVO article_favoriteVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT); 
System.out.println("���X��MEMBER_NO = "+article_favoriteVO.getMem_no());		
System.out.println("���X��ARTICLE_NO = "+article_favoriteVO.getArt_no());	
			pstmt.setString(1, article_favoriteVO.getMem_no());
			pstmt.setString(2, article_favoriteVO.getArt_no());
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
	public void delete(String mem_no, String art_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();

			// 1���]�w�� pstm.executeUpdate()���e
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_ARTICLE_FAVORITE);
			pstmt.setString(1, mem_no);
			pstmt.setString(2, art_no);
			pstmt.executeUpdate();

			// 2���]�w�� pstm.executeUpdate()����
			con.commit();
			con.setAutoCommit(true);
			System.out.println("�R���|���s��" + mem_no + "���`���峹�s��" + art_no);
			
			// Handle any DRIVER errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3���]�w���exception�o�ͮɤ�catch�϶���
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
	public Article_FavoriteVO findByPrimaryKey(String mem_no , String art_no) {

		Article_FavoriteVO article_favoriteVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mem_no);
			pstmt.setString(2, art_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				article_favoriteVO = new Article_FavoriteVO();
				article_favoriteVO.setMem_no(rs.getString("mem_no"));
				article_favoriteVO.setArt_no(rs.getString("art_no"));
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
		return article_favoriteVO;
	}

	@Override
	public List<Article_FavoriteVO> getAll() {
		List<Article_FavoriteVO> list = new ArrayList<Article_FavoriteVO>();
		Article_FavoriteVO article_favoriteVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				article_favoriteVO = new Article_FavoriteVO();
				article_favoriteVO.setMem_no(rs.getString("mem_no"));
				article_favoriteVO.setArt_no(rs.getString("art_no"));
				list.add(article_favoriteVO); // Store the row in the list
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
	public List<Article_FavoriteVO> findByMemNo(String mem_no) {
		List<Article_FavoriteVO> list = new ArrayList<Article_FavoriteVO>();
		Article_FavoriteVO article_favoriteVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_MEM_NO_STMT);

			pstmt.setString(1, mem_no);

			rs = pstmt.executeQuery();
			

			while (rs.next()) {
				article_favoriteVO = new Article_FavoriteVO();
				article_favoriteVO.setMem_no(rs.getString("mem_no"));
				article_favoriteVO.setArt_no(rs.getString("art_no"));
				list.add(article_favoriteVO);
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
		return list;
	}

	@Override
	public List<Article_FavoriteVO> findByArtNo(String art_no) {
		List<Article_FavoriteVO> list = new ArrayList<Article_FavoriteVO>();
		Article_FavoriteVO article_favoriteVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_ART_NO_STMT);

			pstmt.setString(1, art_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				article_favoriteVO = new Article_FavoriteVO();
				article_favoriteVO.setMem_no(rs.getString("mem_no"));
				article_favoriteVO.setArt_no(rs.getString("art_no"));
				list.add(article_favoriteVO);
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
		return list;
	}
}
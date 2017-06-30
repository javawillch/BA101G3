package com.article.model;

import java.util.*;
import java.sql.*;


public class ArticleJDBCDAO implements ArticleDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "ba101g3";
	private static final String PASSWORD = "baby";
	// �s�W���
	private static final String INSERT_STMT = "INSERT INTO article (art_no, mem_no, artc_no, art_title, art_date, art_cnt, art_vcnt) VALUES ('ART'||LPAD(to_char(art_no_seq.NEXTVAL),5,'0'), ?, ?, ?, SYSDATE, ?, 0)";
	// �d�߸��
	private static final String GET_ALL_STMT = "SELECT * FROM article";
	private static final String GET_ONE_STMT = "SELECT * FROM article WHERE art_no = ?";
	// �R�����
	private static final String DELETE_ARTICLE_MESSAGE_REPORTs = "DELETE FROM article_message_report WHERE amsg_no IN (SELECT amsg_no FROM article_message WHERE art_no=?)";
	private static final String DELETE_ARTICLE_MESSAGEs = "DELETE FROM article_message WHERE art_no = ?";
	private static final String DELETE_ARTICLE_FAVORITEs = "DELETE FROM article_favorite WHERE art_no = ?";
	private static final String DELETE_ARTICLE = "DELETE FROM article WHERE art_no = ?";	
	// �ק���
	private static final String UPDATE = "UPDATE article SET artc_no= ?, art_title= ?, art_date= ?, art_cnt= ?, art_vcnt= ? WHERE art_no = ?";
	//����r�j�M
	private static final String SEARCH = "SELECT * FROM article WHERE (art_cnt LIKE ? OR art_title LIKE ?) ORDER BY art_date DESC";
	
	@Override
	public void insert(ArticleVO articleVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			String[] cols = { "art_no" }; // ���ϥ�sequence���ͽs�����ܤ~�n�g
			pstmt = con.prepareStatement(INSERT_STMT, cols); // ���ϥ�sequence���ͽs�����ܤ~�n�g�ĤG�ӰѼ�
			pstmt.setString(1, articleVO.getMem_no());
			pstmt.setString(2, articleVO.getArtc_no());
			pstmt.setString(3, articleVO.getArt_title());
			pstmt.setString(4, articleVO.getArt_cnt());
			
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
	public void update(ArticleVO articleVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, articleVO.getArtc_no());
			pstmt.setString(2, articleVO.getArt_title());
			pstmt.setTimestamp(3, articleVO.getArt_date());
			pstmt.setString(4, articleVO.getArt_cnt());
			pstmt.setInt(5, articleVO.getArt_vcnt());
			pstmt.setString(6, articleVO.getArt_no());
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
	public void delete(String art_no) {
		int updateCount_ARTICLE_MESSAGE_REPORTs = 0;
		int updateCount_ARTICLE_MESSAGEs = 0;
		int updateCount_ARTICLE_FAVORITEs = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);

			// 1���]�w�� pstm.executeUpdate()���e
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_ARTICLE_MESSAGE_REPORTs);
			pstmt.setString(1, art_no);
			updateCount_ARTICLE_MESSAGE_REPORTs = pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(DELETE_ARTICLE_MESSAGEs);
			pstmt.setString(1, art_no);
			updateCount_ARTICLE_MESSAGEs = pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(DELETE_ARTICLE_FAVORITEs);
			pstmt.setString(1, art_no);
			updateCount_ARTICLE_FAVORITEs = pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(DELETE_ARTICLE);
			pstmt.setString(1, art_no);
			pstmt.executeUpdate();

			// 2���]�w�� pstm.executeUpdate()����
			con.commit();
			con.setAutoCommit(true);
			System.out.println("�R���峹�s��" + art_no + "��,�@���峹�d��" + updateCount_ARTICLE_MESSAGEs
					+ "�ӡB�峹�d�����|" + updateCount_ARTICLE_MESSAGE_REPORTs + "�ӡB���`�峹"
					+ updateCount_ARTICLE_FAVORITEs + "�ӡA�P�ɳQ�R��");
			
			// Handle any DRIVER errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database DRIVER. "
					+ e.getMessage());
			// Handle any SQL errors
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
	public ArticleVO findByPrimaryKey(String art_no) {

		ArticleVO articleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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

		ArticleJDBCDAO dao = new ArticleJDBCDAO();
		// ���լݬݨC�ӫ��O�O�_�i�H�ϥ�
		// �s�W OK
//		ArticleVO answerVO1 = new ArticleVO();
//		answerVO1.setMem_no("M0000001");
//		answerVO1.setArtc_no("AC02");
//		answerVO1.setArt_title("�s�W���D����XXX");
//		answerVO1.setArt_cnt("�s�W���e����XXX");
//		dao.insert(answerVO1);
//		System.out.println("�s�W�@����Ƨ���");

		// �ק� OK
//		ArticleVO answerVO2 = new ArticleVO();
//		answerVO2.setArt_no("ART00009");
//		answerVO2.setArtc_no("AC06");
//		answerVO2.setArt_title("�ק���D����");
//		answerVO2.setArt_date(java.sql.Timestamp.valueOf("2005-01-01 03:33:33"));
//		answerVO2.setArt_cnt("�ק鷺�e����");
//		answerVO2.setArt_vcnt(1001);
//		dao.update(answerVO2);
//		System.out.println("�ק令�\");

		// �R�� OK
//		dao.delete("ART00002");

		// �d�� OK
//		ArticleVO articleVO3 = dao.findByPrimaryKey("ART00005");
//		System.out.print(articleVO3.getArt_no() + ",");
//		System.out.print(articleVO3.getMem_no() + ",");
//		System.out.print(articleVO3.getArtc_no() + ",");
//		System.out.print(articleVO3.getArt_title() + ",");
//		System.out.print(articleVO3.getArt_date() + ",");
//		System.out.print(articleVO3.getArt_cnt() + ",");
//		System.out.println(articleVO3.getArt_vcnt());
//		System.out.println("---------------------");

		// �d�ߥ��� OK
//		List<ArticleVO> list = dao.getAll();
//		for (ArticleVO articleVO : list) {
//			System.out.print(articleVO.getArt_no() + ",");
//			System.out.print(articleVO.getMem_no() + ",");
//			System.out.print(articleVO.getArtc_no() + ",");
//			System.out.print(articleVO.getArt_title() + ",");
//			System.out.print(articleVO.getArt_date() + ",");
//			System.out.print(articleVO.getArt_cnt() + ",");
//			System.out.print(articleVO.getArt_vcnt());
//			System.out.println();
//		}
		
		//����r�d��   
//		List<ArticleVO> list = dao.searchPattern("%���U%");
//		for (ArticleVO articleVO : list) {
//			System.out.print(articleVO.getArt_no() + ",");
//			System.out.print(articleVO.getMem_no() + ",");
//			System.out.print(articleVO.getArtc_no() + ",");
//			System.out.print(articleVO.getArt_title() + ",");
//			System.out.print(articleVO.getArt_date() + ",");
//			System.out.print(articleVO.getArt_cnt() + ",");
//			System.out.print(articleVO.getArt_vcnt());
//			System.out.println();
//		}
		
	}

	@Override
	public void update_art_vcnt(Integer art_vcnt, String art_no) {
		// �W�u�֭p�峹�s���ƨϥ�
		
	}

	@Override
	public List<ArticleVO> getAllByArtc_no(String artc_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ArticleVO> searchPattern(String pattern) {
		List<ArticleVO> list = new ArrayList<ArticleVO>();
		ArticleVO articleVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		// TODO Auto-generated method stub
		return null;
	}
}
package com.article.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.*;


public class Article_MessageDAO implements Article_MessageDAO_interface {
	
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
	private static final String INSERT_STMT = "INSERT INTO article_message (amsg_no, art_no, mem_no, amsg_date, amsg_cnt) VALUES ('AMSG'||LPAD(to_char(amsg_no_seq.NEXTVAL),4,'0'), ?, ?, SYSDATE, ?)";
	// �d�߸��
	private static final String GET_ALL_STMT = "SELECT * FROM article_message";
	private static final String GET_ONE_STMT = "SELECT * FROM article_message WHERE amsg_no = ?";
	// �R�����
	private static final String DELETE_ARTICLE_MESSAGE_REPORTs = "DELETE FROM article_message_report WHERE amsg_no=?";
	private static final String DELETE_ARTICLE_MESSAGE = "DELETE FROM article_message WHERE amsg_no = ?";	

	@Override
	public void insert(Article_MessageVO article_MessageVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			String[] cols = { "amsg_no" }; // ���ϥ�sequence���ͽs�����ܤ~�n�g
			pstmt = con.prepareStatement(INSERT_STMT, cols); // ���ϥ�sequence���ͽs�����ܤ~�n�g�ĤG�ӰѼ�
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, article_MessageVO.getArt_no());
			pstmt.setString(2, article_MessageVO.getMem_no());
			pstmt.setString(3, article_MessageVO.getAmsg_cnt());
		
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
	public void delete(String amsg_no) {
		int updateCount_ARTICLE_MESSAGE_REPORTs = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();

			// 1���]�w�� pstm.executeUpdate()���e
			con.setAutoCommit(false);

			// ���R���峹�d�����|(�h)
			pstmt = con.prepareStatement(DELETE_ARTICLE_MESSAGE_REPORTs);
			pstmt.setString(1, amsg_no);
			updateCount_ARTICLE_MESSAGE_REPORTs = pstmt.executeUpdate();
			// �A�R���峹�d��(�@)
			pstmt = con.prepareStatement(DELETE_ARTICLE_MESSAGE);
			pstmt.setString(1, amsg_no);
			pstmt.executeUpdate();

			// 2���]�w�� pstm.executeUpdate()����
			con.commit();
			con.setAutoCommit(true);
			System.out.println("�R���峹�d���s��" + amsg_no + "��,�@���峹�d�����|" + updateCount_ARTICLE_MESSAGE_REPORTs
					+ "�ӦP�ɳQ�R��");
			
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
	public Article_MessageVO findByPrimaryKey(String amsg_no) {

		Article_MessageVO article_MessageVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, amsg_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				article_MessageVO = new Article_MessageVO();
				article_MessageVO.setAmsg_no(rs.getString("amsg_no"));
				article_MessageVO.setArt_no(rs.getString("art_no"));
				article_MessageVO.setMem_no(rs.getString("mem_no"));
				article_MessageVO.setAmsg_date(rs.getTimestamp("amsg_date"));
				article_MessageVO.setAmsg_cnt(rs.getString("amsg_cnt"));
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
		return article_MessageVO;
	}

	@Override
	public List<Article_MessageVO> getAll() {
		List<Article_MessageVO> list = new ArrayList<Article_MessageVO>();
		Article_MessageVO article_MessageVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				article_MessageVO = new Article_MessageVO();
				article_MessageVO.setAmsg_no(rs.getString("amsg_no"));
				article_MessageVO.setArt_no(rs.getString("art_no"));
				article_MessageVO.setMem_no(rs.getString("mem_no"));
				article_MessageVO.setAmsg_date(rs.getTimestamp("amsg_date"));
				article_MessageVO.setAmsg_cnt(rs.getString("amsg_cnt"));
				list.add(article_MessageVO); // Store the row in the list
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
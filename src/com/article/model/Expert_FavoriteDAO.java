package com.article.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.*;


public class Expert_FavoriteDAO implements Expert_FavoriteDAO_interface {
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
	private static final String INSERT_STMT = "INSERT INTO expert_favorite (mem_no_s, mem_no_e) VALUES (?, ?)";
	// �d�߸��
	private static final String GET_ALL_STMT = "SELECT * FROM expert_favorite";
	private static final String GET_ONE_STMT = "SELECT * FROM expert_favorite WHERE mem_no_s = ? and mem_no_e =?";
	private static final String GET_BY_MEM_NO_S_STMT = "SELECT * FROM expert_favorite WHERE mem_no_s=?";
	private static final String GET_BY_MEM_NO_E_STMT = "SELECT * FROM expert_favorite WHERE mem_no_e=?";
	// �R�����
	private static final String DELETE_STMT = "DELETE FROM expert_favorite WHERE mem_no_s = ? and mem_no_e =?";	

	@Override
	public void insert(Expert_FavoriteVO expert_favoriteVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT); 
			pstmt.setString(1, expert_favoriteVO.getMem_no_s());
			pstmt.setString(2, expert_favoriteVO.getMem_no_e());

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
	public void delete(String mem_no_s, String mem_no_e) {
		int updateCount_PRODUCTs = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();

			// 1���]�w�� pstm.executeUpdate()���e
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setString(1, mem_no_s);
			pstmt.setString(2, mem_no_e);
			pstmt.executeUpdate();

			// 2���]�w�� pstm.executeUpdate()����
			con.commit();
			con.setAutoCommit(true);
			System.out.println("�R���|���s��" + mem_no_s + "���`���@�a�s��" + mem_no_e);
			
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
	public Expert_FavoriteVO findByPrimaryKey(String mem_no_s , String mem_no_e) {

		Expert_FavoriteVO product_classificationVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mem_no_s);
			pstmt.setString(2, mem_no_e);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				product_classificationVO = new Expert_FavoriteVO();
				product_classificationVO.setMem_no_s(rs.getString("mem_no_s"));
				product_classificationVO.setMem_no_e(rs.getString("mem_no_e"));
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
	public List<Expert_FavoriteVO> findByMemNoS(String mem_no_s) {
		List<Expert_FavoriteVO> list = new ArrayList<Expert_FavoriteVO>();
		Expert_FavoriteVO expert_favoriteVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_MEM_NO_S_STMT);
			pstmt.setString(1, mem_no_s);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				expert_favoriteVO = new Expert_FavoriteVO();
				expert_favoriteVO.setMem_no_s(rs.getString("mem_no_s"));
				expert_favoriteVO.setMem_no_e(rs.getString("mem_no_e"));
				list.add(expert_favoriteVO); // Store the row in the list
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
	public List<Expert_FavoriteVO> findByMemNoE(String mem_no_e) {
		List<Expert_FavoriteVO> list = new ArrayList<Expert_FavoriteVO>();
		Expert_FavoriteVO expert_favoriteVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_MEM_NO_E_STMT);
			pstmt.setString(1, mem_no_e);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				expert_favoriteVO = new Expert_FavoriteVO();
				expert_favoriteVO.setMem_no_s(rs.getString("mem_no_s"));
				expert_favoriteVO.setMem_no_e(rs.getString("mem_no_e"));
				list.add(expert_favoriteVO); // Store the row in the list
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
	public List<Expert_FavoriteVO> getAll() {
		List<Expert_FavoriteVO> list = new ArrayList<Expert_FavoriteVO>();
		Expert_FavoriteVO expert_favoriteVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				expert_favoriteVO = new Expert_FavoriteVO();
				expert_favoriteVO.setMem_no_s(rs.getString("mem_no_s"));
				expert_favoriteVO.setMem_no_e(rs.getString("mem_no_e"));
				list.add(expert_favoriteVO); // Store the row in the list
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
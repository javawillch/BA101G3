package com.diary.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Growing_DiaryJDBCDAO implements Growing_DiaryDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "ba101g3";
	private static final String PASSWORD = "baby";

	private static final String INSERT_STMT = "INSERT INTO growing_diary (gd_no, baby_no, gd_title, gd_date, gd_cnt, gd_shr) VALUES ('GD'||LPAD(to_char(gd_no_seq.NEXTVAL), 6, '0'), ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM growing_diary ORDER BY gd_no DESC";
	private static final String GET_ONE_STMT = "SELECT * FROM growing_diary WHERE gd_no = ?";
	private static final String UPDATE_STMT = "UPDATE growing_diary SET gd_title=? , gd_date=?, gd_cnt=?, gd_shr=? WHERE gd_no= ?";
	private static final String DELETE_GROWING_DIARY = "DELETE FROM growing_diary WHERE gd_no= ?";

	@Override
	public void insert(Growing_DiaryVO growing_diaryVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, growing_diaryVO.getBaby_no());
			pstmt.setString(2, growing_diaryVO.getGd_title());
			pstmt.setTimestamp(3, growing_diaryVO.getGd_date());
			pstmt.setString(4, growing_diaryVO.getGd_cnt());
			pstmt.setString(5, growing_diaryVO.getGd_shr());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(Growing_DiaryVO growing_diaryVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);

			
			pstmt.setString(1, growing_diaryVO.getGd_title());
			pstmt.setTimestamp(2, growing_diaryVO.getGd_date());
			pstmt.setString(3, growing_diaryVO.getGd_cnt());
			pstmt.setString(4, growing_diaryVO.getGd_shr());
			pstmt.setString(5, growing_diaryVO.getGd_no());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void delete(String gd_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_GROWING_DIARY);

			pstmt.setString(1, gd_no);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public Growing_DiaryVO findByPrimaryKey(String gd_no) {
		Growing_DiaryVO growing_diaryVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, gd_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				growing_diaryVO = new Growing_DiaryVO();
				growing_diaryVO.setGd_no(rs.getString("gd_no"));
				growing_diaryVO.setBaby_no(rs.getString("baby_no"));
				growing_diaryVO.setGd_title(rs.getString("gd_title"));
				growing_diaryVO.setGd_date(rs.getTimestamp("gd_date"));
				growing_diaryVO.setGd_cnt(rs.getString("gd_cnt"));
				growing_diaryVO.setGd_shr(rs.getString("gd_shr"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return growing_diaryVO;
	}

	@Override
	public List<Growing_DiaryVO> getAll() {
		List<Growing_DiaryVO> list = new ArrayList<Growing_DiaryVO>();
		Growing_DiaryVO growing_diaryVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				growing_diaryVO = new Growing_DiaryVO();
				growing_diaryVO.setGd_no(rs.getString("gd_no"));
				growing_diaryVO.setBaby_no(rs.getString("baby_no"));
				growing_diaryVO.setGd_title(rs.getString("gd_title"));
				growing_diaryVO.setGd_date(rs.getTimestamp("gd_date"));
				growing_diaryVO.setGd_cnt(rs.getString("gd_cnt"));
				growing_diaryVO.setGd_shr(rs.getString("gd_shr"));
				list.add(growing_diaryVO);
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	public static void main(String[] args) {
		Growing_DiaryJDBCDAO dao = new Growing_DiaryJDBCDAO();
		 // �s�W OK
//		Growing_DiaryVO growing_diaryVO1 = new Growing_DiaryVO();
//		growing_diaryVO1.setBaby_no("BABY0001");
//		growing_diaryVO1.setGd_title("�_�_�Ĥ@���Ƿ|�����F");
//		growing_diaryVO1.setGd_date(java.sql.Timestamp.valueOf("2018-01-01 13:11:30"));
//		growing_diaryVO1.setGd_cnt("so cute");
//		growing_diaryVO1.setGd_shr("1");
//		dao.insert(growing_diaryVO1);
//		System.out.print("��Ʒs�W����");
		
		 // �ק� OK
//		 Growing_DiaryVO growing_diaryVO2 = new Growing_DiaryVO();
//		 growing_diaryVO2.setGd_no("GD000003");
//		 growing_diaryVO2.setBaby_no("BABY0001");
//		 growing_diaryVO2.setGd_title("�_�_�Ĥ@���ܥ�");
//		 growing_diaryVO2.setGd_date(java.sql.Timestamp.valueOf("2018-01-01 13:11:30"));
//		 growing_diaryVO2.setGd_cnt("�ܫܦh�O");
//		 growing_diaryVO2.setGd_shr("1");
//		 dao.update(growing_diaryVO2);
//		 System.out.print("��ƭק粒��");
		
		 // �R�� OK
//		 dao.delete("GD000002");
//		 System.out.println("�R�����\");
		
		 // �d�� OK
//		 Growing_DiaryVO growing_diaryVO3 = dao.findByPrimary("GD000002");
//		 System.out.print(growing_diaryVO3.getGd_no() + ",");
//		 System.out.print(growing_diaryVO3.getBaby_no() + ",");
//		 System.out.print(growing_diaryVO3.getGd_title() + ",");
//		 System.out.print(growing_diaryVO3.getGd_date() + ",");
//		 System.out.print(growing_diaryVO3.getGd_cnt() + ",");
//		 System.out.println(growing_diaryVO3.getGd_shr() + ",");
//		 System.out.println("---------------------");
		
		 // �d�ߥ��� OK
//		 List<Growing_DiaryVO> list = dao.getAll();
//		 for (Growing_DiaryVO growing_diaryVO : list) {
//		 System.out.print(growing_diaryVO.getGd_no() + ",");
//		 System.out.print(growing_diaryVO.getBaby_no() + ",");
//		 System.out.print(growing_diaryVO.getGd_title() + ",");
//		 System.out.print(growing_diaryVO.getGd_date() + ",");
//		 System.out.print(growing_diaryVO.getGd_cnt() + ",");
//		 System.out.print(growing_diaryVO.getGd_shr() + ",");
//		 System.out.println();
//		 }
	}
}
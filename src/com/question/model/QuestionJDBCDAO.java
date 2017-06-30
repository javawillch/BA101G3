package com.question.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;


public class QuestionJDBCDAO implements QuestionDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "ba101g3";
	private static final String PASSWORD = "baby";
	
	private static final String INSERT_STMT = "INSERT INTO question(qu_no, mem_no, quec_no, qu_title, qu_date, qu_cnt)"
			+ "VALUES('QU'||LPAD(to_char(qu_no_seq.NEXTVAL),6,'0'), ?, ?, ?, SYSDATE, ?)";
	private static final String FIND_BY_PK = "SELECT * FROM question WHERE qu_no = ?";
	private static final String GET_ALL = "SELECT * FROM question";
	private static final String DELETE_ANSWER_REPORTs = "DELETE FROM answer_report WHERE ans_no IN (SELECT ans_no FROM answer WHERE qu_no=?)";
	private static final String DELETE_ANSWERs = "DELETE FROM answer WHERE qu_no = ?";
	private static final String DELETE_QUESTION = "DELETE FROM question WHERE qu_no = ?";
	private static final String GET_Answers_ByQu_no_STMT = "SELECT ans_no, mem_no,qu_no,to_char(ans_date,'yyyy-mm-dd hh:mm:ss') ans_date, ans_cnt, ans_like, and_is_hide FROM answer where qu_no = ? order by ans_date DESC";

	
	@Override
	public void insert(QuestionVO questionVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, questionVO.getMem_no());
			pstmt.setString(2, questionVO.getQuec_no());
			pstmt.setString(3, questionVO.getQu_title());
			pstmt.setString(4, questionVO.getQu_cnt());
			
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
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
	public void delete(String qu_no) {
		int updateCount_ANSWER_REPORTs = 0;
		int updateCount_ANSWERs = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			
			pstmt = con.prepareStatement(DELETE_ANSWER_REPORTs);
			pstmt.setString(1, qu_no);
			updateCount_ANSWER_REPORTs = pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(DELETE_ANSWERs);
			pstmt.setString(1, qu_no);
			updateCount_ANSWERs = pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(DELETE_QUESTION);
			pstmt.setString(1, qu_no);
			pstmt.executeUpdate();
			
			System.out.println("刪除問題" + qu_no + "時,共有問題回覆" + updateCount_ANSWERs 
					+ "個、問題回覆檢舉"+ updateCount_ANSWER_REPORTs  + "個，同時被刪除");
			
			

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
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
	public QuestionVO findByPrimaryKey(String qu_no) {
		QuestionVO questionVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);

			pstmt.setString(1, qu_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				questionVO = new QuestionVO();
				questionVO.setQu_no(rs.getString("qu_no"));
				questionVO.setMem_no(rs.getString("mem_no"));
				questionVO.setQuec_no(rs.getString("quec_no"));
				questionVO.setQu_title(rs.getString("qu_title"));
				questionVO.setQu_date(rs.getTimestamp("qu_date"));
				questionVO.setQu_cnt(rs.getString("qu_cnt"));

			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} 
		finally {
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
		return questionVO;
		
	}

	@Override
	public List<QuestionVO> getAll() {
		List<QuestionVO> list = new ArrayList<QuestionVO>();
		QuestionVO questionVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				questionVO = new QuestionVO();
				questionVO.setQu_no(rs.getString("qu_no"));
				questionVO.setMem_no(rs.getString("mem_no"));
				questionVO.setQuec_no(rs.getString("quec_no"));
				questionVO.setQu_title(rs.getString("qu_title"));
				questionVO.setQu_date(rs.getTimestamp("qu_date"));
				questionVO.setQu_cnt(rs.getString("qu_cnt"));
				list.add(questionVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} 
		finally {
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


	public static void main(String[] args) throws IOException, SQLException {

		QuestionJDBCDAO dao = new QuestionJDBCDAO();

		// 新增  OK
//		QuestionVO questionVO1 = new QuestionVO();
//		questionVO1.setMem_no("M0000005");
//		questionVO1.setQuec_no("QC03");
//		questionVO1.setQu_title("寶寶幾歲可以吃大人的食物?");
//		questionVO1.setQu_cnt("兩歲多的寶貝，在主食方面幾乎可以和大人吃的一樣了，米飯、饅頭、麵條，這些主食，大人幾乎不用再特別加工，就可以給寶貝享用了，蔬菜也不用再做成菜泥了，只要切成塊狀寶貝就可以咀嚼了。但對於纖維過多過粗的蔬菜，比如芹菜等，還有豆類的食物，媽咪還是要特別處理一下。粗纖維的蔬菜要加工成比較小的塊，而豆類食物，依然要軟爛或者是泥狀，避免寶貝發生危險。媽咪對寶貝依然要堅持少油少鹽的飲食，大人可以在菜熟了時先將寶貝的盛出來，然後再加比較濃重的調料。");
//		dao.insert(questionVO1);
//		System.out.println("新增一筆資料完成");


		// 刪除 OK
//		dao.delete("QU000001");
//		System.out.println("完成刪除");

		// 查詢 OK
//		QuestionVO questionVO3 = dao.findByPrimaryKey("QU000001");
//		System.out.println("問題編號: "+questionVO3.getQu_no() + ",");
//		System.out.println("會員編號(提問者): "+questionVO3.getMem_no() + ",");
//		System.out.println("問題分類編號: "+questionVO3.getQuec_no() + ",");
//		System.out.println("問題主旨: "+questionVO3.getQu_title() + ",");
//		System.out.println("問題詢問時間: "+questionVO3.getQu_date() + ",");
//		System.out.println("問題內容: "+questionVO3.getQu_cnt());
//		System.out.println("---------------------");

		// 查詢全部 OK
//		List<QuestionVO> list = dao.getAll();
//		for (QuestionVO questionVO : list) {
//			System.out.print(questionVO.getQu_no() + ",");
//			System.out.print(questionVO.getMem_no() + ",");
//			System.out.print(questionVO.getQuec_no() + ",");
//			System.out.print(questionVO.getQu_title() + ",");
//			System.out.print(questionVO.getQu_date() + ",");
//			System.out.print(questionVO.getQu_cnt());
//			System.out.println();
//		}
	}


	@Override
	public Set<AnswerVO> getAnswersByQu_no(String qu_no) {
		// TODO Auto-generated method stub
		return null;
	}
	


	
}

package com.question.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class QuestionDAO implements QuestionDAO_interface{
	//問題不可以修改，沒有修改功能
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, questionVO.getMem_no());
			pstmt.setString(2, questionVO.getQuec_no());
			pstmt.setString(3, questionVO.getQu_title());
			pstmt.setString(4, questionVO.getQu_cnt());
			
			pstmt.executeUpdate();

			// Handle any driver errors
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

			con = ds.getConnection();
			
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

			con = ds.getConnection();
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

			con = ds.getConnection();
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

	
	//額外寫的CLOB轉String(沒用到)
	public static String readString(Clob clob) throws IOException, SQLException {
		
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(clob.getCharacterStream());
		String str;
		while((str = br.readLine()) != null) {
			sb.append(str);
			sb.append("\n");
		}
		br.close();

		return sb.toString();
	}

	@Override
	public Set<AnswerVO> getAnswersByQu_no(String qu_no) {
		Set<AnswerVO> set = new LinkedHashSet<AnswerVO>();
		AnswerVO answerVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Answers_ByQu_no_STMT);
			pstmt.setString(1, qu_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				answerVO = new AnswerVO();
				answerVO.setAns_no(rs.getString("ans_no"));
				answerVO.setMem_no(rs.getString("mem_no"));
				answerVO.setQu_no(rs.getString("qu_no"));
				answerVO.setAns_date(rs.getTimestamp("ans_date"));
				answerVO.setAns_cnt(rs.getString("ans_cnt"));
				answerVO.setAns_like(rs.getInt("ans_like"));
				answerVO.setAns_is_hide(rs.getString("ans_is_hide"));
				set.add(answerVO); // Store the row in the vector
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

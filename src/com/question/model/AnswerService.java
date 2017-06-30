package com.question.model;


import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.question.model.*;

public class AnswerService {

	private AnswerDAO_interface dao;

	public AnswerService() {
		dao = new AnswerDAO();
	}
//	private String ans_no; // Not Null (PK)
//	private String mem_no; // Not Null (FK)
//	private String qu_no; // Not Null (FK)
//	private Timestamp ans_date; // Not Null
//	private String ans_cnt; // Not Null
//	private Integer ans_like; // Not Null
//	private String ans_is_hide; // Not Null
	public AnswerVO addAnswer(String ans_no, String mem_no, String qu_no, Timestamp ans_date, 
			String ans_cnt, Integer ans_like, String ans_is_hide )  {
		

		AnswerVO answerVO = new AnswerVO();

		answerVO.setAns_no(ans_no);
		answerVO.setMem_no(mem_no);
		answerVO.setQu_no(qu_no);
		answerVO.setAns_date(ans_date);
		answerVO.setAns_cnt(ans_cnt);
		answerVO.setAns_like(ans_like);
		answerVO.setAns_is_hide(ans_is_hide);
		dao.insert(answerVO);

		return answerVO;
	}

	public AnswerVO updateAnswer(String ans_no, String mem_no, String qu_no, Timestamp ans_date, 
			String ans_cnt, Integer ans_like, String ans_is_hide ) {

		AnswerVO answerVO = new AnswerVO();

		answerVO.setAns_no(ans_no);
		answerVO.setMem_no(mem_no);
		answerVO.setQu_no(qu_no);
		answerVO.setAns_date(ans_date);
		answerVO.setAns_cnt(ans_cnt);
		answerVO.setAns_like(ans_like);
		answerVO.setAns_is_hide(ans_is_hide);
		dao.update(answerVO);

		return answerVO;
	}
	

	public AnswerVO getOneAnswer(String ans_no) {
		return dao.findByPrimaryKey(ans_no);
	}

	public List<AnswerVO> getAll() {
		return dao.getAll();
	}
	
//	要思考一下需不需要這個
//	public Set<Answer_ReportVO> getAnswer_ReportByAns_no(String ans_no){
//		return dao.getAnswer_ReportByAns_no(ans_no);
//	}
}

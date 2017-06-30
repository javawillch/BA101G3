package com.question.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public class QuestionService {

	private QuestionDAO_interface dao;

	public QuestionService() {
		dao = new QuestionDAO();
	}

	public QuestionVO addQuestion(String qu_no, String mem_no, String quec_no,
			String qu_title, Timestamp qu_date, String qu_cnt) {

		QuestionVO questionVO = new QuestionVO();

		questionVO.setQu_no(qu_no);
		questionVO.setMem_no(mem_no);
		questionVO.setQuec_no(quec_no);
		questionVO.setQu_title(qu_title);
		questionVO.setQu_date(qu_date);
		questionVO.setQu_cnt(qu_cnt);
		dao.insert(questionVO);

		return questionVO;
	}
	

	public void deleteQuestion(String qu_no) {
		dao.delete(qu_no);
	}

	public QuestionVO getOneQuestion(String qu_no) {
		return dao.findByPrimaryKey(qu_no);
	}

	public List<QuestionVO> getAll() {
		return dao.getAll();
	}
	
	public Set<AnswerVO> getAnswersByQu_no(String qu_no){
		return dao.getAnswersByQu_no(qu_no);
	}
	
}

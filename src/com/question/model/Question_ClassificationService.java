package com.question.model;


import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.question.model.*;

public class Question_ClassificationService {

	private Question_ClassificationDAO_interface dao;

	public Question_ClassificationService() {
		dao = new Question_ClassificationDAO();
	}

	public Question_ClassificationVO addQuestion_Classification(String quec_no, String quec_name)  {

		Question_ClassificationVO question_classificationVO = new Question_ClassificationVO();

		question_classificationVO.setQuec_no(quec_no);
		question_classificationVO.setQuec_name(quec_name);
		dao.insert(question_classificationVO);

		return question_classificationVO;
	}

	public Question_ClassificationVO updateQuestion_Classification(String quec_no, String quec_name) {

		Question_ClassificationVO question_classificationVO = new Question_ClassificationVO();

		question_classificationVO.setQuec_no(quec_no);
		question_classificationVO.setQuec_name(quec_name);
		dao.update(question_classificationVO);

		return question_classificationVO;
	}
	

	public Question_ClassificationVO getOneQuestion_Classifiction(String quec_no) {
		return dao.findByPrimaryKey(quec_no);
	}

	public List<Question_ClassificationVO> getAll() {
		return dao.getAll();
	}
	
	
	public Set<QuestionVO> getQuestionsByQuec_no(String quec_no){
		return dao.getQuestionsByQuec_no(quec_no);
	}
}

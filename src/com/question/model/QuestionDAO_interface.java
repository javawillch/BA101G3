package com.question.model;

import java.util.*;

public interface QuestionDAO_interface {
	 public void insert(QuestionVO questionVO);
     public void delete(String qu_no);
     public QuestionVO findByPrimaryKey(String qu_no);
     public List<QuestionVO> getAll();
     //查詢某類問題的問題(一對多)(回傳set)
     public Set<AnswerVO> getAnswersByQu_no(String qu_no);

     //萬用複合查詢(傳入參數型態Map)(回傳 List)
     // public List<QuestionVO> getAll(Map<String, String[]> map);
}





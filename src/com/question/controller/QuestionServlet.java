package com.question.controller;

import java.io.*;
import java.sql.Clob;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.question.model.*;

public class QuestionServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		System.out.println(action);
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("qu_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入問題編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/question/question/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String qu_no = null;
				try {
					qu_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("問題編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/question/question/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				QuestionService questionSvc = new QuestionService();
				QuestionVO questionVO = questionSvc.getOneQuestion(qu_no);
				if (questionVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/question/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("questionVO", questionVO); // 資料庫取出的questionVO物件,存入req
				String url = "/frontend/question/question/listOneQuestion.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/question/question/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/listAllQuestion.jsp】 或  【/listQuestions_ByQuec_no.jsp】 或 【 /listAllQuestion_Classification.jsp】	
			
			try {
				/***************************1.接收請求參數****************************************/
				String qu_no = new String(req.getParameter("qu_no"));
				
				/***************************2.開始查詢資料****************************************/
				QuestionService questionSvc = new QuestionService();
				QuestionVO questionVO = questionSvc.getOneQuestion(qu_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("questionVO", questionVO);         // 資料庫取出的empVO物件,存入req
				String url = "/frontend/question/question/update_question_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
System.out.println("有進來insert區快喔");
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String qu_no = null;
				try {
					qu_no = new String(req.getParameter("qu_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("問題編號請填寫.");
				}
				
				String mem_no = null;
				try {
					mem_no = new String(req.getParameter("mem_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("會員編號請填寫.");
				}
				
				
				String qu_title = null;
				try {
					qu_title = new String(req.getParameter("qu_title").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("問題標題請填寫.");
				}
				
				String qu_cnt = null;
				try {
					qu_cnt = new String(req.getParameter("qu_cnt").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("問題內容請填寫.");
				}
				
				
				java.sql.Timestamp qu_date = null;
				try {
					qu_date = java.sql.Timestamp.valueOf(req.getParameter("qu_date").trim());
				} catch (IllegalArgumentException e) {
					qu_date=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				String quec_no = null;
				try {
					quec_no = new String(req.getParameter("quec_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("問題分類請填寫.");
				}
				

				QuestionVO questionVO = new QuestionVO();
				questionVO.setQu_no(qu_no);
				questionVO.setMem_no(mem_no);
				questionVO.setQuec_no(quec_no);
				questionVO.setQu_title(qu_title);
				questionVO.setQu_date(qu_date);
				questionVO.setQu_cnt(qu_cnt);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("questionVO", questionVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/question/question/addQuestion.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				QuestionService questionSvc = new QuestionService();
				questionVO = questionSvc.addQuestion(qu_no, mem_no, quec_no, qu_title, qu_date, qu_cnt) ;
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/frontend/question/question/listAllQuestion.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				System.out.println("我現在從insert進來到其他可能的錯誤處理");
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/question/question/addQuestion.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
			System.out.println("跑來delete區塊");
			try {
				/***************************1.接收請求參數***************************************/
				String qu_no = new String(req.getParameter("qu_no"));
				
				/***************************2.開始刪除資料***************************************/
				QuestionService questionSvc = new QuestionService();
				QuestionVO questionVO = questionSvc.getOneQuestion(qu_no);
				questionSvc.deleteQuestion(qu_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				Question_ClassificationService question_classificationSvc = new Question_ClassificationService();
				if(requestURL.equals("/frontend/question/question_classification/listQuestions_ByQuec_no.jsp")||requestURL.equals("/frontend/question_classification/listAllQuestion_Classification.jsp"))
					req.setAttribute("listQuestions_ByQuec_no", question_classificationSvc.getQuestionsByQuec_no(questionVO.getQuec_no()));
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
	}
}

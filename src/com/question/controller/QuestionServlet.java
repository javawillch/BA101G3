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
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("qu_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J���D�s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/question/question/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				String qu_no = null;
				try {
					qu_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("���D�s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/question/question/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				QuestionService questionSvc = new QuestionService();
				QuestionVO questionVO = questionSvc.getOneQuestion(qu_no);
				if (questionVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/question/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("questionVO", questionVO); // ��Ʈw���X��questionVO����,�s�Jreq
				String url = "/frontend/question/question/listOneQuestion.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/question/question/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|: �i�ର�i/listAllQuestion.jsp�j ��  �i/listQuestions_ByQuec_no.jsp�j �� �i /listAllQuestion_Classification.jsp�j	
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				String qu_no = new String(req.getParameter("qu_no"));
				
				/***************************2.�}�l�d�߸��****************************************/
				QuestionService questionSvc = new QuestionService();
				QuestionVO questionVO = questionSvc.getOneQuestion(qu_no);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("questionVO", questionVO);         // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/frontend/question/question/update_question_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_emp_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		

        if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
System.out.println("���i��insert�ϧֳ�");
			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
				String qu_no = null;
				try {
					qu_no = new String(req.getParameter("qu_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("���D�s���ж�g.");
				}
				
				String mem_no = null;
				try {
					mem_no = new String(req.getParameter("mem_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�|���s���ж�g.");
				}
				
				
				String qu_title = null;
				try {
					qu_title = new String(req.getParameter("qu_title").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("���D���D�ж�g.");
				}
				
				String qu_cnt = null;
				try {
					qu_cnt = new String(req.getParameter("qu_cnt").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("���D���e�ж�g.");
				}
				
				
				java.sql.Timestamp qu_date = null;
				try {
					qu_date = java.sql.Timestamp.valueOf(req.getParameter("qu_date").trim());
				} catch (IllegalArgumentException e) {
					qu_date=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
				}
				
				String quec_no = null;
				try {
					quec_no = new String(req.getParameter("quec_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("���D�����ж�g.");
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
					req.setAttribute("questionVO", questionVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/question/question/addQuestion.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				QuestionService questionSvc = new QuestionService();
				questionVO = questionSvc.addQuestion(qu_no, mem_no, quec_no, qu_title, qu_date, qu_cnt) ;
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/frontend/question/question/listAllQuestion.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				System.out.println("�ڲ{�b�qinsert�i�Ө��L�i�઺���~�B�z");
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/question/question/addQuestion.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
			System.out.println("�]��delete�϶�");
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				String qu_no = new String(req.getParameter("qu_no"));
				
				/***************************2.�}�l�R�����***************************************/
				QuestionService questionSvc = new QuestionService();
				QuestionVO questionVO = questionSvc.getOneQuestion(qu_no);
				questionSvc.deleteQuestion(qu_no);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
				Question_ClassificationService question_classificationSvc = new Question_ClassificationService();
				if(requestURL.equals("/frontend/question/question_classification/listQuestions_ByQuec_no.jsp")||requestURL.equals("/frontend/question_classification/listAllQuestion_Classification.jsp"))
					req.setAttribute("listQuestions_ByQuec_no", question_classificationSvc.getQuestionsByQuec_no(questionVO.getQuec_no()));
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
	}
}

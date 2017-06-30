package com.question.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


import com.question.model.*;

public class Question_ClassificationServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");

		    // �Ӧ�select_page.jsp���ШD                                  // �Ӧ� dept/listAllQuestion_Classification.jsp���ШD
		if ("listQuestions_ByQuec_no_A".equals(action) || "listQuestions_ByQuec_no_B".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				String quec_no = new String(req.getParameter("quec_no"));

				/*************************** 2.�}�l�d�߸�� ****************************************/
				Question_ClassificationService question_classificationSvc = new Question_ClassificationService();
				Set<QuestionVO> set = question_classificationSvc.getQuestionsByQuec_no(quec_no);

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
				req.setAttribute("listQuestions_ByQuec_no", set);    // ��Ʈw���X��set����,�s�Jrequest

				String url = null;
				if ("listQuestions_ByQuec_no_A".equals(action))
					url = "/frontend/question/question_classification/listQuestions_ByQuec_no.jsp";        // ���\��� /frontend/question_classification/listQuestions_ByQuec_no.jsp
				else if ("listQuestions_ByQuec_no_B".equals(action))
					url = "/frontend/question/question_classification/listAllQuestion_Classification.jsp";              // ���\��� /frontend/question_classification/listAllQuestion_Classification.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
	}
}

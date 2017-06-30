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

		    // 來自select_page.jsp的請求                                  // 來自 dept/listAllQuestion_Classification.jsp的請求
		if ("listQuestions_ByQuec_no_A".equals(action) || "listQuestions_ByQuec_no_B".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String quec_no = new String(req.getParameter("quec_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				Question_ClassificationService question_classificationSvc = new Question_ClassificationService();
				Set<QuestionVO> set = question_classificationSvc.getQuestionsByQuec_no(quec_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listQuestions_ByQuec_no", set);    // 資料庫取出的set物件,存入request

				String url = null;
				if ("listQuestions_ByQuec_no_A".equals(action))
					url = "/frontend/question/question_classification/listQuestions_ByQuec_no.jsp";        // 成功轉交 /frontend/question_classification/listQuestions_ByQuec_no.jsp
				else if ("listQuestions_ByQuec_no_B".equals(action))
					url = "/frontend/question/question_classification/listAllQuestion_Classification.jsp";              // 成功轉交 /frontend/question_classification/listAllQuestion_Classification.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
	}
}

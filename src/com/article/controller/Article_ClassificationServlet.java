package com.article.controller;


import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.article.model.*;

public class Article_ClassificationServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");
System.out.println("�i�Ӥ峹�������: "+action);		

		    // �Ӧ�select_page.jsp���ШD                                  // �Ӧ� dept/listAllDept.jsp���ШD
		if ("listArticles_ByArtc_no_A".equals(action) || "listArticles_ByArtc_no_B".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				String artc_no = new String(req.getParameter("artc_no"));

				/*************************** 2.�}�l�d�߸�� ****************************************/
				Article_ClassificationService article_classificationSvc = new Article_ClassificationService();
				Set<ArticleVO> set = article_classificationSvc.getArticlesByArtc_no(artc_no);
System.out.println("-----------------");
				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
				req.setAttribute("listArticles_ByArtc_no", set);    // ��Ʈw���X��set����,�s�Jrequest

				String url = null;
				if ("listArticles_ByArtc_no_A".equals(action))
					url = "/frontend/article/article_classification/listArticles_ByArtc_no.jsp";        // ���\��� /frontend/question_classification/listQuestions_ByQuec_no.jsp
				else if ("listArticles_ByArtc_no_B".equals(action))
					url = "/frontend/article/article_classification/listAllArticle_Classification.jsp";              // ���\��� /frontend/question_classification/listAllQuestion_Classification.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		
//		if ("delete_Article_Classification".equals(action)) { // �Ӧ�/dept/listAllDept.jsp���ШD
//
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//	
//			try {
//				/***************************1.�����ШD�Ѽ�***************************************/
//				String artc_no = new String(req.getParameter("artc_no"));
//				
//				/***************************2.�}�l�R�����***************************************/
//				Article_ClassificationService article_classificationSvc = new Article_ClassificationService();
//				article_classificationSvc.deleteArticle_Classification(artc_no);
//				
//				/***************************3.�R������,�ǳ����(Send the Success view)***********/
//				String url = "/frontend/article/article_classification/listAllArticle_Classification.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��, ���\��� �^�� /dept/listAllDept.jsp
//				successView.forward(req, res);
//				
//				/***************************��L�i�઺���~�B�z***********************************/
//			} catch (Exception e) {
//				errorMsgs.add("�R����ƥ���:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/frontend/article/article_classification/listAllArticle_Classification.jsp");
//				failureView.forward(req, res);
//			}
//		}

	}
}

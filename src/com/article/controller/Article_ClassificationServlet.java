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
System.out.println("進來文章分類控制器: "+action);		

		    // 來自select_page.jsp的請求                                  // 來自 dept/listAllDept.jsp的請求
		if ("listArticles_ByArtc_no_A".equals(action) || "listArticles_ByArtc_no_B".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String artc_no = new String(req.getParameter("artc_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				Article_ClassificationService article_classificationSvc = new Article_ClassificationService();
				Set<ArticleVO> set = article_classificationSvc.getArticlesByArtc_no(artc_no);
System.out.println("-----------------");
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listArticles_ByArtc_no", set);    // 資料庫取出的set物件,存入request

				String url = null;
				if ("listArticles_ByArtc_no_A".equals(action))
					url = "/frontend/article/article_classification/listArticles_ByArtc_no.jsp";        // 成功轉交 /frontend/question_classification/listQuestions_ByQuec_no.jsp
				else if ("listArticles_ByArtc_no_B".equals(action))
					url = "/frontend/article/article_classification/listAllArticle_Classification.jsp";              // 成功轉交 /frontend/question_classification/listAllQuestion_Classification.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		
//		if ("delete_Article_Classification".equals(action)) { // 來自/dept/listAllDept.jsp的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//	
//			try {
//				/***************************1.接收請求參數***************************************/
//				String artc_no = new String(req.getParameter("artc_no"));
//				
//				/***************************2.開始刪除資料***************************************/
//				Article_ClassificationService article_classificationSvc = new Article_ClassificationService();
//				article_classificationSvc.deleteArticle_Classification(artc_no);
//				
//				/***************************3.刪除完成,準備轉交(Send the Success view)***********/
//				String url = "/frontend/article/article_classification/listAllArticle_Classification.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後, 成功轉交 回到 /dept/listAllDept.jsp
//				successView.forward(req, res);
//				
//				/***************************其他可能的錯誤處理***********************************/
//			} catch (Exception e) {
//				errorMsgs.add("刪除資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/frontend/article/article_classification/listAllArticle_Classification.jsp");
//				failureView.forward(req, res);
//			}
//		}

	}
}

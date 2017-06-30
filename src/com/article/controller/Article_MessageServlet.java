package com.article.controller;

import java.io.*;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.article.model.*;

public class Article_MessageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
System.out.println("送進Article_Message控制器的action ="+ action);
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

//			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("art_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入文章編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/article/article/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String art_no = null;
				try {
					art_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("文章編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/article/article/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				ArticleService articleSvc = new ArticleService();
System.out.println(art_no);
				ArticleVO articleVO = articleSvc.getOneArticle(art_no);
System.out.println(articleVO);

				if (articleVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/article/article/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
System.out.println("-----------------");				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("articleVO", articleVO); // 資料庫取出的questionVO物件,存入req
				String url = "/frontend/article/article/listOneArticle.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/frontend/article/article/select_page.jsp");
//				failureView.forward(req, res);
//			}
		}
		
		
        if ("insert".equals(action)) { // 來自addArticle.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			
//			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String amsg_no = null;             //用sequence
			
				String art_no = null;
				try {
					art_no = new String(req.getParameter("art_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("文章編號請填寫.");
				}
				
				String mem_no = null;
				try {
					mem_no = new String(req.getParameter("mem_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("會員編號請填寫.");
				}
				
				java.sql.Timestamp amsg_date = new java.sql.Timestamp(System.currentTimeMillis());
				
				
				String amsg_cnt = null;
				try {
					amsg_cnt = new String(req.getParameter("amsg_cnt").trim());

				} catch (NumberFormatException e) {
					errorMsgs.add("文章留言請填寫.");
				}
				
	
				Article_MessageVO article_messageVO = new Article_MessageVO();
				article_messageVO.setAmsg_no(amsg_no);
				article_messageVO.setArt_no(art_no);
				article_messageVO.setMem_no(mem_no);
				article_messageVO.setAmsg_date(amsg_date);
				article_messageVO.setAmsg_cnt(amsg_cnt);



				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("article_messageVO", article_messageVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/article/article/addArticle.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				ArticleService articleSvc = new ArticleService();
				ArticleVO articleVO = articleSvc.getOneArticle(art_no);

				Article_MessageService article_messageSvc = new Article_MessageService();
				article_messageVO = article_messageSvc.addArticle_Message(amsg_no, art_no, mem_no, amsg_date,
						amsg_cnt) ;
System.out.println("-----------------");				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				req.setAttribute("articleVO", articleVO);
				
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
					
				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				System.out.println("我現在從insert進來到其他可能的錯誤處理");
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/frontend/article/article/addArticle.jsp");
//				failureView.forward(req, res);
//			}
		}
		
//刪除還沒完全實作       
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
System.out.println("跑來delete區塊");
//			try {
				/***************************1.接收請求參數***************************************/
				String art_no = new String(req.getParameter("art_no"));
				
				/***************************2.開始刪除資料***************************************/
				ArticleService articleSvc = new ArticleService();
				ArticleVO articleVO = articleSvc.getOneArticle(art_no);
				articleSvc.deleteArticle(art_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				Article_ClassificationService article_classificationSvc = new Article_ClassificationService();
				if(requestURL.equals("/frontend/article_classification/listArticles_ByArtc_no.jsp")||requestURL.equals("/frontend/question_classification/listAllQuestion_Classification.jsp"))
					req.setAttribute("listArticles_ByArtc_no", article_classificationSvc.getArticlesByArtc_no(articleVO.getArtc_no()));
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
System.out.println("-----------------");	
				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("刪除資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher(requestURL);
//				failureView.forward(req, res);
//			}
		}
	}
}

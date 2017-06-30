package com.article.controller;

import java.io.*;
import java.sql.Clob;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.article.model.*;

public class Article_Message_ReportServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("action");
		
System.out.println("送進Article_Message_Report控制器的action ="+ action);
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
System.out.println("art_no: "+art_no);
				ArticleVO articleVO = articleSvc.getOneArticle(art_no);
System.out.println("articleVO"+articleVO);
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
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/listAllQuestion.jsp】 或  【/listQuestions_ByQuec_no.jsp】 或 【 /listAllQuestion_Classification.jsp】	
			
//			try {
				/***************************1.接收請求參數****************************************/
				String art_no = new String(req.getParameter("art_no"));
				
				/***************************2.開始查詢資料****************************************/
				ArticleService articleSvc = new ArticleService();
				ArticleVO articleVO = articleSvc.getOneArticle(art_no);
System.out.println("-----------------");									
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("articleVO", articleVO);         // 資料庫取出的empVO物件,存入req
				String url = "/frontend/article/article/update_article_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher(requestURL);
//				failureView.forward(req, res);
//			}
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/listAllQuestion.jsp】 或  【/listQuestions_ByQuec_no.jsp】 或 【 /listAllQuestion_Classification.jsp】	

//			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String art_no = new String(req.getParameter("art_no").trim());
				String art_title = req.getParameter("art_title").trim();
				String art_cnt = req.getParameter("art_cnt").trim();	
System.out.println("art_no = "+ art_no);
				
				java.sql.Timestamp art_date = new java.sql.Timestamp(System.currentTimeMillis());
//				try {
//					qu_date = java.sql.Timestamp.valueOf(req.getParameter("qu_date").trim());
//				} catch (IllegalArgumentException e) {
//					qu_date=new java.sql.Timestamp(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}

				String mem_no = null;
				try {
					mem_no = new String(req.getParameter("mem_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("會員編號請填寫.");
				}

				String artc_no = null;
				try {
					artc_no = new String(req.getParameter("artc_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("文章分類編號請填寫.");
				}
				
				Integer art_vcnt = null;
				art_vcnt = new Integer(req.getParameter("art_vcnt"));


				ArticleVO articleVO = new ArticleVO();
				articleVO.setArt_no(art_no);
				articleVO.setMem_no(mem_no);
				articleVO.setArtc_no(artc_no);
				articleVO.setArt_title(art_title);
				articleVO.setArt_date(art_date);
				articleVO.setArt_cnt(art_cnt);
				articleVO.setArt_vcnt(art_vcnt);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("articleVO", articleVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/article/article/update_article_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}

	
				/***************************2.開始修改資料*****************************************/
				ArticleService articleSvc = new ArticleService();
				articleVO = articleSvc.updateArticle(art_no, mem_no, artc_no, art_title, art_date, art_cnt, art_vcnt);
System.out.println("-----------------");			
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				Article_ClassificationService article_classificationSvc = new Article_ClassificationService();
				if(requestURL.equals("/frontend/article/article_classification/listArticles_ByArtc_no.jsp")||requestURL.equals("/frontend/article/article_classification/listAllArticle_Classification.jsp"))
					req.setAttribute("listArticles_ByArtc_no", article_classificationSvc.getArticlesByArtc_no(artc_no));
//req.setAttribute("articleVO", articleVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneArticle.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("修改資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/frontend/article/article/update_article_input.jsp");
//				failureView.forward(req, res);
//			}
		}

        if ("insert".equals(action)) { // 來自addArticle.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
System.out.println("requestURL= "+ requestURL);			

//			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String amsg_no = null;
				try {
					amsg_no = new String(req.getParameter("amsg_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("沒有留言編號.");
				}
System.out.println("要對留言編號amsg_no= "+amsg_no + " 新增一則檢舉");
				String mem_no = null;
				try {
					mem_no = new String(req.getParameter("mem_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("會員編號請填寫.");
				}
				
				java.sql.Timestamp amrpt_date = new java.sql.Timestamp(System.currentTimeMillis());

				
				String amrpt_rsn = null;
				try {
					amrpt_rsn = new String(req.getParameter("amrpt_rsn").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("文章留言檢舉內容請填寫.");
				}
				
				String amrpt_is_cert = "0";

				String amrpt_unrsn = null;

				

				Article_Message_ReportVO article_message_reportVO = new Article_Message_ReportVO();
				article_message_reportVO.setAmsg_no(amsg_no);
				article_message_reportVO.setMem_no(mem_no);
				article_message_reportVO.setAmrpt_date(amrpt_date);
				article_message_reportVO.setAmrpt_rsn(amrpt_rsn);
				article_message_reportVO.setAmrpt_is_cert(amrpt_is_cert);
				article_message_reportVO.setAmrpt_unrsn(amrpt_unrsn);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("article_message_reportVO", article_message_reportVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/article/article/listOneArticle.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				Article_Message_ReportService article_message_reportSvc = new Article_Message_ReportService();
				article_message_reportVO = article_message_reportSvc.addArticle_Message_Report(amsg_no, mem_no, amrpt_date, amrpt_rsn, amrpt_is_cert, amrpt_is_cert) ;
System.out.println("-----------------");				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/frontend/article/article/listAllArticle.jsp";
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
		
		

	}
}

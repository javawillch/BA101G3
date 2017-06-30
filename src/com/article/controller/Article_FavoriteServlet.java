package com.article.controller;

import java.io.*;
import java.sql.Clob;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.article.model.*;

public class Article_FavoriteServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
System.out.println("送進Article_Favorite控制器的action ="+ action);

//		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
////			try {
//				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
//				String str = req.getParameter("art_no");
//				if (str == null || (str.trim()).length() == 0) {
//					errorMsgs.add("請輸入文章編號");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/frontend/article/article/select_page.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
//				String art_no = null;
//				try {
//					art_no = new String(str);
//				} catch (Exception e) {
//					errorMsgs.add("文章編號格式不正確");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/frontend/article/article/select_page.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
//				/***************************2.開始查詢資料*****************************************/
//				ArticleService articleSvc = new ArticleService();
//System.out.println(art_no);
//				ArticleVO articleVO = articleSvc.getOneArticle(art_no);
//System.out.println(articleVO);
//				if (articleVO == null) {
//					errorMsgs.add("查無資料");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/frontend/article/article/select_page.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
//				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
//				req.setAttribute("articleVO", articleVO); // 資料庫取出的questionVO物件,存入req
//				String url = "/frontend/article/article/listOneArticle.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
//				successView.forward(req, res);
//
//				/***************************其他可能的錯誤處理*************************************/
////			} catch (Exception e) {
////				errorMsgs.add("無法取得資料:" + e.getMessage());
////				RequestDispatcher failureView = req
////						.getRequestDispatcher("/frontend/article/article/select_page.jsp");
////				failureView.forward(req, res);
////			}
//		}
		

        if ("insert_ByListOneArticle".equals(action) || "insert_ByListAllArticle".equals(action)) { // 來自addArticle.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
//			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String art_no = new String(req.getParameter("art_no").trim());
		
				String mem_no = new String(req.getParameter("mem_no").trim());

				Article_FavoriteVO article_favoriteVO = new Article_FavoriteVO();
				article_favoriteVO.setArt_no(art_no);
				article_favoriteVO.setMem_no(mem_no);

System.out.println("art_no"+art_no);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("article_favoriteVO", article_favoriteVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("...");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				ArticleService articleSvc = new ArticleService();
				ArticleVO articleVO = articleSvc.getOneArticle(art_no);
				
				Article_FavoriteService article_favoriteSvc = new Article_FavoriteService();
				article_favoriteVO = article_favoriteSvc.addArticle_Favorite(mem_no , art_no) ;
System.out.println("-----------------");				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				req.setAttribute("articleVO", articleVO);
				String url = requestURL;
//				if("insert_ByListOneArticle".equals(action))
//					url ="/frontend/article/article/listOneArticle.jsp";
//				else if("insert_ByListAllArticle".equals(action))	
//					url ="/frontend/article/article/listAllArticle.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllArticle.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				System.out.println("我現在從insert進來到其他可能的錯誤處理");
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("xxxxxxxx");
//				failureView.forward(req, res);
//			}
		}
		
		
		if ("delete_ByListOneArticle".equals(action) || "delete_ByListAllArticle".equals(action) ) { // 來自listAllArticle.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
System.out.println("跑來Article_Favorite的delete區塊");
//			try {
				/***************************1.接收請求參數***************************************/
				String art_no = new String(req.getParameter("art_no").trim());
				String mem_no = new String(req.getParameter("mem_no").trim());
				
				/***************************2.開始刪除資料***************************************/
				ArticleService articleSvc = new ArticleService();
				ArticleVO articleVO = articleSvc.getOneArticle(art_no);
				
				Article_FavoriteService article_favoriteSvc = new Article_FavoriteService();
				Article_FavoriteVO article_favoriteVO = article_favoriteSvc.getOneArticle_Favorite(art_no, mem_no);
				article_favoriteSvc.deleteArticle_Favorite(mem_no, art_no);
System.out.println("-----------------");				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/	
				req.setAttribute("articleVO", articleVO);
				
				Article_ClassificationService article_classificationSvc = new Article_ClassificationService();
				
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

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

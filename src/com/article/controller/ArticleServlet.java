package com.article.controller;

import java.io.*;
import java.sql.Clob;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.article.model.*;

public class ArticleServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
//rfff
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("action");
System.out.println("送進Article控制器的action ="+ action);

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
System.out.println("111111111 ");	
//			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("art_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入文章編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/article/article/articleHome.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String art_no = null;
				try {
					art_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("文章編號格式不正確");
				}
				
				Integer art_vcnt= new Integer(req.getParameter("art_vcnt"));
				if(art_vcnt==null){

				}
System.out.println("瀏覽數art_vcnt= "+art_vcnt);				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/article/article/articleHome.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				/***************************2-1.開始累加瀏覽數資料*****************************************/
				ArticleService articleSvc = new ArticleService();
				articleSvc.update_art_vcnt(art_vcnt, art_no);
				/***************************2-2.開始查詢資料*****************************************/
				
System.out.println("art_no: "+art_no);
				ArticleVO articleVO = articleSvc.getOneArticle(art_no);
System.out.println("articleVO"+articleVO);
				if (articleVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/article/article/articleHome.jsp");
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
				String art_no    = req.getParameter("art_no").trim();
				String mem_no    = req.getParameter("mem_no").trim();
				String art_title = req.getParameter("art_title").trim();
				String art_cnt   = req.getParameter("art_cnt");	
System.out.println("art_no = "+ art_no);
				
				java.sql.Timestamp art_date = new java.sql.Timestamp(System.currentTimeMillis());


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
System.out.println("跑來修改區塊 修改成功");
System.out.println("-----------------");
				/***************************2.開始修改資料*****************************************/
				ArticleService articleSvc = new ArticleService();
				articleVO = articleSvc.updateArticle(art_no, mem_no, artc_no, art_title, art_date, art_cnt, art_vcnt);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
//目前只會走else(2017.6.28)				
				Article_ClassificationService article_classificationSvc = new Article_ClassificationService();
				if(requestURL.equals("/frontend/article/article_classification/listArticles_ByArtc_no.jsp")||requestURL.equals("/frontend/article/article_classification/listAllArticle_Classification.jsp")){
					req.setAttribute("articleVO", article_classificationSvc.getArticlesByArtc_no(artc_no));
				}else{
					req.setAttribute("articleVO", articleVO); // 資料庫update成功後,正確的的articleVO物件,存入req
				}
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

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
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
				
				
				String art_title = null;
				try {
					art_title = new String(req.getParameter("art_title").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("問題標題請填寫.");
				}
				
				String art_cnt = null;
				try {
					art_cnt = new String(req.getParameter("art_cnt").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("文章內容請填寫.");
				}
				
				
				java.sql.Timestamp art_date = null;
				try {
					art_date = java.sql.Timestamp.valueOf(req.getParameter("art_date").trim());
				} catch (IllegalArgumentException e) {
					art_date=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				String artc_no = null;
				try {
					artc_no = new String(req.getParameter("artc_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("文章分類請填寫.");
				}
				
				Integer art_vcnt = null;
				art_vcnt = new Integer(req.getParameter("art_vcnt"));
				
				//此為驗證碼比對
				String identity = null;
				try {
					identity = new String(req.getParameter("identity").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("請輸入驗證碼");
				}
System.out.println("browser輸入驗證碼     = "+identity);	
System.out.println("randomString驗證碼= "+req.getSession().getAttribute("randomString"));
				if(!identity.equals(req.getSession().getAttribute("randomString"))){
					errorMsgs.add("驗證碼輸入錯誤，請重新驗證.");
				}


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
							.getRequestDispatcher("/frontend/article/article/addArticle.jsp");
					failureView.forward(req, res);
					return;
				}
System.out.println("-----------------");				
				/***************************2.開始新增資料***************************************/
				ArticleService articleSvc = new ArticleService();
				articleVO = articleSvc.addArticle(art_no, mem_no, artc_no, art_title, art_date, art_cnt , art_vcnt) ;
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/frontend/article/article/articleHome.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				System.out.println("我現在從insert進來到其他可能的錯誤處理");
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/article/article/addArticle.jsp");
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

				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("刪除資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher(requestURL);
//				failureView.forward(req, res);
//			}
		}
	
	
	//依造分類列所有分類文章
	if ("listArticlesByArtc_no".equals(action)) { // 來自articleHome.jsp的請求

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);	
//		try {
			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			String artc_no = req.getParameter("artc_no");
System.out.println(artc_no);
			req.setAttribute("artc_no", artc_no);
			
			
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/article/article/articleHome.jsp");
				failureView.forward(req, res);
				return;//程式中斷
			}

			/***************************2-2.開始查詢資料*****************************************/
			

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/article/article/articleHome.jsp");
				failureView.forward(req, res);
				return;//程式中斷
			}
System.out.println("-----------------");				
			/***************************3.查詢完成,準備轉交(Send the Success view)*************/

			String url = "/frontend/article/article/articleHome.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);

			/***************************其他可能的錯誤處理*************************************/
	//		} catch (Exception e) {
	//			errorMsgs.add("無法取得資料:" + e.getMessage());
	//			RequestDispatcher failureView = req
	//					.getRequestDispatcher("/frontend/article/article/select_page.jsp");
	//			failureView.forward(req, res);
	//		}
		}
	
	//關鍵字搜尋列所有相關分類
	if ("pattern".equals(action)) { // 來自articleHome.jsp的請求

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);	
//		try {
			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/

	//		String artc_no= "pattern";
	//	    req.setAttribute("artc_no", artc_no);    //為了讓首頁判斷要用哪邊的資料
		String artc_no = req.getParameter("artc_no");
System.out.println(artc_no);
			req.setAttribute("artc_no", artc_no);
		
			String pattern2 = null;
			pattern2 = new String(req.getParameter("pattern").trim());
			//要想辦法串成類似%照顧%
			StringBuffer strbuf = new StringBuffer();
			strbuf.append('%');
			strbuf.append(pattern2);
			strbuf.append('%');
			String pattern = strbuf.toString();

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/article/article/articleHome.jsp");
				failureView.forward(req, res);
				return;//程式中斷
			}

			/***************************2-2.開始查詢資料*****************************************/
			List<ArticleVO> list = null;
			ArticleService articleSvc = new ArticleService();
			list = articleSvc.searchPattern(pattern);
			req.setAttribute("list", list);
System.out.println(list.toString());
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/article/article/articleHome.jsp");
				failureView.forward(req, res);
				return;//程式中斷
			}
System.out.println("-----------------");				
			/***************************3.查詢完成,準備轉交(Send the Success view)*************/

			String url = "/frontend/article/article/articleHome.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);

			/***************************其他可能的錯誤處理*************************************/
	//		} catch (Exception e) {
	//			errorMsgs.add("無法取得資料:" + e.getMessage());
	//			RequestDispatcher failureView = req
	//					.getRequestDispatcher("/frontend/article/article/select_page.jsp");
	//			failureView.forward(req, res);
	//		}
		}
	
	
	
	}
}

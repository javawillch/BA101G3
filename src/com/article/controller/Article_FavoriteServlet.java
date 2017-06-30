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
		
System.out.println("�e�iArticle_Favorite�����action ="+ action);

//		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
////			try {
//				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
//				String str = req.getParameter("art_no");
//				if (str == null || (str.trim()).length() == 0) {
//					errorMsgs.add("�п�J�峹�s��");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/frontend/article/article/select_page.jsp");
//					failureView.forward(req, res);
//					return;//�{�����_
//				}
//				
//				String art_no = null;
//				try {
//					art_no = new String(str);
//				} catch (Exception e) {
//					errorMsgs.add("�峹�s���榡�����T");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/frontend/article/article/select_page.jsp");
//					failureView.forward(req, res);
//					return;//�{�����_
//				}
//				
//				/***************************2.�}�l�d�߸��*****************************************/
//				ArticleService articleSvc = new ArticleService();
//System.out.println(art_no);
//				ArticleVO articleVO = articleSvc.getOneArticle(art_no);
//System.out.println(articleVO);
//				if (articleVO == null) {
//					errorMsgs.add("�d�L���");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/frontend/article/article/select_page.jsp");
//					failureView.forward(req, res);
//					return;//�{�����_
//				}
//				
//				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
//				req.setAttribute("articleVO", articleVO); // ��Ʈw���X��questionVO����,�s�Jreq
//				String url = "/frontend/article/article/listOneArticle.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
//				successView.forward(req, res);
//
//				/***************************��L�i�઺���~�B�z*************************************/
////			} catch (Exception e) {
////				errorMsgs.add("�L�k���o���:" + e.getMessage());
////				RequestDispatcher failureView = req
////						.getRequestDispatcher("/frontend/article/article/select_page.jsp");
////				failureView.forward(req, res);
////			}
//		}
		

        if ("insert_ByListOneArticle".equals(action) || "insert_ByListAllArticle".equals(action)) { // �Ӧ�addArticle.jsp���ШD  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
//			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
				String art_no = new String(req.getParameter("art_no").trim());
		
				String mem_no = new String(req.getParameter("mem_no").trim());

				Article_FavoriteVO article_favoriteVO = new Article_FavoriteVO();
				article_favoriteVO.setArt_no(art_no);
				article_favoriteVO.setMem_no(mem_no);

System.out.println("art_no"+art_no);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("article_favoriteVO", article_favoriteVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("...");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				ArticleService articleSvc = new ArticleService();
				ArticleVO articleVO = articleSvc.getOneArticle(art_no);
				
				Article_FavoriteService article_favoriteSvc = new Article_FavoriteService();
				article_favoriteVO = article_favoriteSvc.addArticle_Favorite(mem_no , art_no) ;
System.out.println("-----------------");				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				req.setAttribute("articleVO", articleVO);
				String url = requestURL;
//				if("insert_ByListOneArticle".equals(action))
//					url ="/frontend/article/article/listOneArticle.jsp";
//				else if("insert_ByListAllArticle".equals(action))	
//					url ="/frontend/article/article/listAllArticle.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllArticle.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
//			} catch (Exception e) {
//				System.out.println("�ڲ{�b�qinsert�i�Ө��L�i�઺���~�B�z");
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("xxxxxxxx");
//				failureView.forward(req, res);
//			}
		}
		
		
		if ("delete_ByListOneArticle".equals(action) || "delete_ByListAllArticle".equals(action) ) { // �Ӧ�listAllArticle.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
System.out.println("�]��Article_Favorite��delete�϶�");
//			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				String art_no = new String(req.getParameter("art_no").trim());
				String mem_no = new String(req.getParameter("mem_no").trim());
				
				/***************************2.�}�l�R�����***************************************/
				ArticleService articleSvc = new ArticleService();
				ArticleVO articleVO = articleSvc.getOneArticle(art_no);
				
				Article_FavoriteService article_favoriteSvc = new Article_FavoriteService();
				Article_FavoriteVO article_favoriteVO = article_favoriteSvc.getOneArticle_Favorite(art_no, mem_no);
				article_favoriteSvc.deleteArticle_Favorite(mem_no, art_no);
System.out.println("-----------------");				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/	
				req.setAttribute("articleVO", articleVO);
				
				Article_ClassificationService article_classificationSvc = new Article_ClassificationService();
				
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("�R����ƥ���:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher(requestURL);
//				failureView.forward(req, res);
//			}
		}
	}
}

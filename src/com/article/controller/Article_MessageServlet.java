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
		
System.out.println("�e�iArticle_Message�����action ="+ action);
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

//			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("art_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�峹�s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/article/article/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				String art_no = null;
				try {
					art_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("�峹�s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/article/article/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				ArticleService articleSvc = new ArticleService();
System.out.println(art_no);
				ArticleVO articleVO = articleSvc.getOneArticle(art_no);
System.out.println(articleVO);

				if (articleVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/article/article/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
System.out.println("-----------------");				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("articleVO", articleVO); // ��Ʈw���X��questionVO����,�s�Jreq
				String url = "/frontend/article/article/listOneArticle.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("�L�k���o���:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/frontend/article/article/select_page.jsp");
//				failureView.forward(req, res);
//			}
		}
		
		
        if ("insert".equals(action)) { // �Ӧ�addArticle.jsp���ШD  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			
//			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
				String amsg_no = null;             //��sequence
			
				String art_no = null;
				try {
					art_no = new String(req.getParameter("art_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�峹�s���ж�g.");
				}
				
				String mem_no = null;
				try {
					mem_no = new String(req.getParameter("mem_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�|���s���ж�g.");
				}
				
				java.sql.Timestamp amsg_date = new java.sql.Timestamp(System.currentTimeMillis());
				
				
				String amsg_cnt = null;
				try {
					amsg_cnt = new String(req.getParameter("amsg_cnt").trim());

				} catch (NumberFormatException e) {
					errorMsgs.add("�峹�d���ж�g.");
				}
				
	
				Article_MessageVO article_messageVO = new Article_MessageVO();
				article_messageVO.setAmsg_no(amsg_no);
				article_messageVO.setArt_no(art_no);
				article_messageVO.setMem_no(mem_no);
				article_messageVO.setAmsg_date(amsg_date);
				article_messageVO.setAmsg_cnt(amsg_cnt);



				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("article_messageVO", article_messageVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/article/article/addArticle.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				ArticleService articleSvc = new ArticleService();
				ArticleVO articleVO = articleSvc.getOneArticle(art_no);

				Article_MessageService article_messageSvc = new Article_MessageService();
				article_messageVO = article_messageSvc.addArticle_Message(amsg_no, art_no, mem_no, amsg_date,
						amsg_cnt) ;
System.out.println("-----------------");				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				req.setAttribute("articleVO", articleVO);
				
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);				
					
				/***************************��L�i�઺���~�B�z**********************************/
//			} catch (Exception e) {
//				System.out.println("�ڲ{�b�qinsert�i�Ө��L�i�઺���~�B�z");
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/frontend/article/article/addArticle.jsp");
//				failureView.forward(req, res);
//			}
		}
		
//�R���٨S������@       
		if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
System.out.println("�]��delete�϶�");
//			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				String art_no = new String(req.getParameter("art_no"));
				
				/***************************2.�}�l�R�����***************************************/
				ArticleService articleSvc = new ArticleService();
				ArticleVO articleVO = articleSvc.getOneArticle(art_no);
				articleSvc.deleteArticle(art_no);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
				Article_ClassificationService article_classificationSvc = new Article_ClassificationService();
				if(requestURL.equals("/frontend/article_classification/listArticles_ByArtc_no.jsp")||requestURL.equals("/frontend/question_classification/listAllQuestion_Classification.jsp"))
					req.setAttribute("listArticles_ByArtc_no", article_classificationSvc.getArticlesByArtc_no(articleVO.getArtc_no()));
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
System.out.println("-----------------");	
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

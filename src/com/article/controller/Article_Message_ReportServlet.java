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
		
System.out.println("�e�iArticle_Message_Report�����action ="+ action);
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
System.out.println("art_no: "+art_no);
				ArticleVO articleVO = articleSvc.getOneArticle(art_no);
System.out.println("articleVO"+articleVO);
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
		
		
		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|: �i�ର�i/listAllQuestion.jsp�j ��  �i/listQuestions_ByQuec_no.jsp�j �� �i /listAllQuestion_Classification.jsp�j	
			
//			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				String art_no = new String(req.getParameter("art_no"));
				
				/***************************2.�}�l�d�߸��****************************************/
				ArticleService articleSvc = new ArticleService();
				ArticleVO articleVO = articleSvc.getOneArticle(art_no);
System.out.println("-----------------");									
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("articleVO", articleVO);         // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/frontend/article/article/update_article_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_emp_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher(requestURL);
//				failureView.forward(req, res);
//			}
		}
		
		
		if ("update".equals(action)) { // �Ӧ�update_emp_input.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|: �i�ର�i/listAllQuestion.jsp�j ��  �i/listQuestions_ByQuec_no.jsp�j �� �i /listAllQuestion_Classification.jsp�j	

//			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String art_no = new String(req.getParameter("art_no").trim());
				String art_title = req.getParameter("art_title").trim();
				String art_cnt = req.getParameter("art_cnt").trim();	
System.out.println("art_no = "+ art_no);
				
				java.sql.Timestamp art_date = new java.sql.Timestamp(System.currentTimeMillis());
//				try {
//					qu_date = java.sql.Timestamp.valueOf(req.getParameter("qu_date").trim());
//				} catch (IllegalArgumentException e) {
//					qu_date=new java.sql.Timestamp(System.currentTimeMillis());
//					errorMsgs.add("�п�J���!");
//				}

				String mem_no = null;
				try {
					mem_no = new String(req.getParameter("mem_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�|���s���ж�g.");
				}

				String artc_no = null;
				try {
					artc_no = new String(req.getParameter("artc_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�峹�����s���ж�g.");
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
					req.setAttribute("articleVO", articleVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/article/article/update_article_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}

	
				/***************************2.�}�l�ק���*****************************************/
				ArticleService articleSvc = new ArticleService();
				articleVO = articleSvc.updateArticle(art_no, mem_no, artc_no, art_title, art_date, art_cnt, art_vcnt);
System.out.println("-----------------");			
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				Article_ClassificationService article_classificationSvc = new Article_ClassificationService();
				if(requestURL.equals("/frontend/article/article_classification/listArticles_ByArtc_no.jsp")||requestURL.equals("/frontend/article/article_classification/listAllArticle_Classification.jsp"))
					req.setAttribute("listArticles_ByArtc_no", article_classificationSvc.getArticlesByArtc_no(artc_no));
//req.setAttribute("articleVO", articleVO); // ��Ʈwupdate���\��,���T����empVO����,�s�Jreq
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneArticle.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/frontend/article/article/update_article_input.jsp");
//				failureView.forward(req, res);
//			}
		}

        if ("insert".equals(action)) { // �Ӧ�addArticle.jsp���ШD  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
System.out.println("requestURL= "+ requestURL);			

//			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
				String amsg_no = null;
				try {
					amsg_no = new String(req.getParameter("amsg_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�S���d���s��.");
				}
System.out.println("�n��d���s��amsg_no= "+amsg_no + " �s�W�@�h���|");
				String mem_no = null;
				try {
					mem_no = new String(req.getParameter("mem_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�|���s���ж�g.");
				}
				
				java.sql.Timestamp amrpt_date = new java.sql.Timestamp(System.currentTimeMillis());

				
				String amrpt_rsn = null;
				try {
					amrpt_rsn = new String(req.getParameter("amrpt_rsn").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�峹�d�����|���e�ж�g.");
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
					req.setAttribute("article_message_reportVO", article_message_reportVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/article/article/listOneArticle.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				Article_Message_ReportService article_message_reportSvc = new Article_Message_ReportService();
				article_message_reportVO = article_message_reportSvc.addArticle_Message_Report(amsg_no, mem_no, amrpt_date, amrpt_rsn, amrpt_is_cert, amrpt_is_cert) ;
System.out.println("-----------------");				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/frontend/article/article/listAllArticle.jsp";
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
		
		

	}
}

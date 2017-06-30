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
System.out.println("�e�iArticle�����action ="+ action);

		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
System.out.println("111111111 ");	
//			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("art_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�峹�s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/article/article/articleHome.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				String art_no = null;
				try {
					art_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("�峹�s���榡�����T");
				}
				
				Integer art_vcnt= new Integer(req.getParameter("art_vcnt"));
				if(art_vcnt==null){

				}
System.out.println("�s����art_vcnt= "+art_vcnt);				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/article/article/articleHome.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				/***************************2-1.�}�l�֥[�s���Ƹ��*****************************************/
				ArticleService articleSvc = new ArticleService();
				articleSvc.update_art_vcnt(art_vcnt, art_no);
				/***************************2-2.�}�l�d�߸��*****************************************/
				
System.out.println("art_no: "+art_no);
				ArticleVO articleVO = articleSvc.getOneArticle(art_no);
System.out.println("articleVO"+articleVO);
				if (articleVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/article/article/articleHome.jsp");
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
System.out.println("�]�ӭק�϶� �ק令�\");
System.out.println("-----------------");
				/***************************2.�}�l�ק���*****************************************/
				ArticleService articleSvc = new ArticleService();
				articleVO = articleSvc.updateArticle(art_no, mem_no, artc_no, art_title, art_date, art_cnt, art_vcnt);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
//�ثe�u�|��else(2017.6.28)				
				Article_ClassificationService article_classificationSvc = new Article_ClassificationService();
				if(requestURL.equals("/frontend/article/article_classification/listArticles_ByArtc_no.jsp")||requestURL.equals("/frontend/article/article_classification/listAllArticle_Classification.jsp")){
					req.setAttribute("articleVO", article_classificationSvc.getArticlesByArtc_no(artc_no));
				}else{
					req.setAttribute("articleVO", articleVO); // ��Ʈwupdate���\��,���T����articleVO����,�s�Jreq
				}
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

			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
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
				
				
				String art_title = null;
				try {
					art_title = new String(req.getParameter("art_title").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("���D���D�ж�g.");
				}
				
				String art_cnt = null;
				try {
					art_cnt = new String(req.getParameter("art_cnt").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�峹���e�ж�g.");
				}
				
				
				java.sql.Timestamp art_date = null;
				try {
					art_date = java.sql.Timestamp.valueOf(req.getParameter("art_date").trim());
				} catch (IllegalArgumentException e) {
					art_date=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
				}
				
				String artc_no = null;
				try {
					artc_no = new String(req.getParameter("artc_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�峹�����ж�g.");
				}
				
				Integer art_vcnt = null;
				art_vcnt = new Integer(req.getParameter("art_vcnt"));
				
				//�������ҽX���
				String identity = null;
				try {
					identity = new String(req.getParameter("identity").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�п�J���ҽX");
				}
System.out.println("browser��J���ҽX     = "+identity);	
System.out.println("randomString���ҽX= "+req.getSession().getAttribute("randomString"));
				if(!identity.equals(req.getSession().getAttribute("randomString"))){
					errorMsgs.add("���ҽX��J���~�A�Э��s����.");
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
					req.setAttribute("articleVO", articleVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/article/article/addArticle.jsp");
					failureView.forward(req, res);
					return;
				}
System.out.println("-----------------");				
				/***************************2.�}�l�s�W���***************************************/
				ArticleService articleSvc = new ArticleService();
				articleVO = articleSvc.addArticle(art_no, mem_no, artc_no, art_title, art_date, art_cnt , art_vcnt) ;
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/frontend/article/article/articleHome.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				System.out.println("�ڲ{�b�qinsert�i�Ө��L�i�઺���~�B�z");
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/article/article/addArticle.jsp");
				failureView.forward(req, res);
			}
		}
		
		
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

				/***************************��L�i�઺���~�B�z**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("�R����ƥ���:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher(requestURL);
//				failureView.forward(req, res);
//			}
		}
	
	
	//�̳y�����C�Ҧ������峹
	if ("listArticlesByArtc_no".equals(action)) { // �Ӧ�articleHome.jsp���ШD

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);	
//		try {
			/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
			String artc_no = req.getParameter("artc_no");
System.out.println(artc_no);
			req.setAttribute("artc_no", artc_no);
			
			
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/article/article/articleHome.jsp");
				failureView.forward(req, res);
				return;//�{�����_
			}

			/***************************2-2.�}�l�d�߸��*****************************************/
			

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/article/article/articleHome.jsp");
				failureView.forward(req, res);
				return;//�{�����_
			}
System.out.println("-----------------");				
			/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/

			String url = "/frontend/article/article/articleHome.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
			successView.forward(req, res);

			/***************************��L�i�઺���~�B�z*************************************/
	//		} catch (Exception e) {
	//			errorMsgs.add("�L�k���o���:" + e.getMessage());
	//			RequestDispatcher failureView = req
	//					.getRequestDispatcher("/frontend/article/article/select_page.jsp");
	//			failureView.forward(req, res);
	//		}
		}
	
	//����r�j�M�C�Ҧ���������
	if ("pattern".equals(action)) { // �Ӧ�articleHome.jsp���ШD

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);	
//		try {
			/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/

	//		String artc_no= "pattern";
	//	    req.setAttribute("artc_no", artc_no);    //���F�������P�_�n�έ��䪺���
		String artc_no = req.getParameter("artc_no");
System.out.println(artc_no);
			req.setAttribute("artc_no", artc_no);
		
			String pattern2 = null;
			pattern2 = new String(req.getParameter("pattern").trim());
			//�n�Q��k�ꦨ����%���U%
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
				return;//�{�����_
			}

			/***************************2-2.�}�l�d�߸��*****************************************/
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
				return;//�{�����_
			}
System.out.println("-----------------");				
			/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/

			String url = "/frontend/article/article/articleHome.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
			successView.forward(req, res);

			/***************************��L�i�઺���~�B�z*************************************/
	//		} catch (Exception e) {
	//			errorMsgs.add("�L�k���o���:" + e.getMessage());
	//			RequestDispatcher failureView = req
	//					.getRequestDispatcher("/frontend/article/article/select_page.jsp");
	//			failureView.forward(req, res);
	//		}
		}
	
	
	
	}
}

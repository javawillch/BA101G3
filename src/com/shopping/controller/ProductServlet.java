package com.shopping.controller;

import java.io.*;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.shopping.model.*;

import utility.Pic;

@MultipartConfig(fileSizeThreshold = 5 * 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class ProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
System.out.println("action = "+ action);		
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("pro_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�ӫ~�s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/shopping/product/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				String pro_no = null;
				try {
					pro_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("�ӫ~�s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/shopping/product/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				ProductService productSvc = new ProductService();
				ProductVO productVO = productSvc.getOneProduct(pro_no);
				if (productVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/shopping/product/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("productVO", productVO); // ��Ʈw���X��questionVO����,�s�Jreq
				String url = "/frontend/shopping/product/listOneProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/shopping/product/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|: �i�ର�i/listAllQuestion.jsp�j ��  �i/listQuestions_ByQuec_no.jsp�j �� �i /listAllQuestion_Classification.jsp�j	
			
//			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				String pro_no = new String(req.getParameter("pro_no"));
				
				/***************************2.�}�l�d�߸��****************************************/
				ProductService productSvc = new ProductService();
				ProductVO productVO = productSvc.getOneProduct(pro_no);

								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("productVO", productVO);         // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/frontend/shopping/product/update_product_input.jsp";
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
System.out.println("���i�Ӱӫ~�ק�϶���");
//			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String pro_no = new String(req.getParameter("pro_no").trim());
				String pro_name = req.getParameter("pro_name").trim();	
System.out.println("pro_no = "+ pro_no);
				
				java.sql.Timestamp pro_date = new java.sql.Timestamp(System.currentTimeMillis());
//				try {
//					pro_date = java.sql.Timestamp.valueOf(req.getParameter("pro_date").trim());
//				} catch (IllegalArgumentException e) {
//					pro_date=new java.sql.Timestamp(System.currentTimeMillis());
//					errorMsgs.add("�п�J���!");
//				}

				String mem_no = null;
				try {
					mem_no = new String(req.getParameter("mem_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�|���s���ж�g.");
				}

				String proc_no = null;
				try {
					proc_no = new String(req.getParameter("proc_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�ӫ~�����s���ж�g.");
				}
				
				Integer pro_price = null;
				try {
					pro_price = new Integer(req.getParameter("pro_price"));
				} catch (NumberFormatException e) {
					errorMsgs.add("�ӫ~����ж�g.");
				}
				
				String pro_intro = req.getParameter("pro_intro").trim();
				
				String pro_stat = null;
				try {
					pro_stat = new String(req.getParameter("pro_stat").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�ӫ~���A�ж�g.");
				}
				
				String pro_pay = null;
				try {
					pro_pay = new String(req.getParameter("pro_pay").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�I�ڤ覡�ж�g.");
				}
				
				String pro_get = null;
				try {
					pro_get = new String(req.getParameter("pro_get").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�e�f�覡�ж�g.");
				}
				
				byte[] pro_photo = null;
				byte[] pro_photo1 = null;
				byte[] pro_photo2 = null;
				byte[] pro_photo3 = null;
				byte[] pro_photo4 = null;
				byte[] pro_photo5 = null;
				
				Part part1 = req.getPart("pro_photo");
				
				pro_photo = Pic.getPictureByteArray(part1);


				ProductVO productVO = new ProductVO();
				productVO.setPro_no(pro_no);
				productVO.setMem_no(mem_no);
				productVO.setProc_no(proc_no);
				productVO.setPro_date(pro_date);
				productVO.setPro_name(pro_name);
				productVO.setPro_price(pro_price);
				productVO.setPro_intro(pro_intro);


				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/shopping/product/update_product_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				System.out.println("�]�ӭק�϶� �ק令�\");
				/***************************2.�}�l�ק���*****************************************/
				ProductService productSvc = new ProductService();
productVO = productSvc.updateProduct(pro_no,mem_no, proc_no,
		pro_date, pro_name, pro_price, pro_intro, pro_photo,
		pro_photo1, pro_photo2, pro_photo3, pro_photo4 , pro_photo5,
		pro_stat, pro_pay, pro_get);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				Product_ClassificationService product_classificationSvc = new Product_ClassificationService();
				if(requestURL.equals("/frontend/shopping/product_classification/listProducts_ByProc_no.jsp")||requestURL.equals("/frontend/shopping/product_classification/listAllProduct_Classification.jsp"))
					req.setAttribute("listProducts_ByProc_no", product_classificationSvc.getProductsByProc_no(proc_no));
//req.setAttribute("questionVO", questionVO); // ��Ʈwupdate���\��,���T����empVO����,�s�Jreq
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/frontend/shopping/product/update_product_input.jsp");
//				failureView.forward(req, res);
//			}
		}

        if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
System.out.println("���i�Ӱӫ~insert�϶���");
			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
				String pro_no = null;
				try {
					pro_no = new String(req.getParameter("pro_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("���D�s���ж�g.");
				}
				
				String mem_no = null;
				try {
					mem_no = new String(req.getParameter("mem_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�|���s���ж�g.");
				}
				
				
				String pro_name = null;
				try {
					pro_name = new String(req.getParameter("pro_name").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�ӫ~�W�ٽж�g.");
				}
				
				String pro_intro = null;
				try {
					pro_intro = new String(req.getParameter("pro_intro").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�ӫ~���e�ж�g.");
				}
				
				
				java.sql.Timestamp pro_date = null;
				try {
					pro_date = java.sql.Timestamp.valueOf(req.getParameter("pro_date").trim());
				} catch (IllegalArgumentException e) {
					pro_date=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
				}
				
				String proc_no = null;
				try {
					proc_no = new String(req.getParameter("proc_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("���D�����ж�g.");
				}
				
				Integer pro_price = null;
					pro_price = new Integer(req.getParameter("pro_price"));
					
				String pro_stat = null;
				try {
					pro_stat = new String(req.getParameter("pro_stat").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�ӫ~���A�ж�g.");
				}
				
				String pro_pay = null;
				try {
					pro_pay = new String(req.getParameter("pro_pay").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�I�ڤ覡�ж�g.");
				}
				
				String pro_get = null;
				try {
					pro_get = new String(req.getParameter("pro_get").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("�e�f�覡�ж�g.");
				}
				
				byte[] pro_photo = null;
				byte[] pro_photo1 = null;
				byte[] pro_photo2 = null;
				byte[] pro_photo3 = null;
				byte[] pro_photo4 = null;
				byte[] pro_photo5 = null;
				
				Part part1 = req.getPart("pro_photo");
				pro_photo = Pic.getPictureByteArray(part1);

			
				ProductVO productVO = new ProductVO();
				productVO.setPro_no(pro_no);
				productVO.setMem_no(mem_no);
				productVO.setProc_no(proc_no);
				productVO.setPro_name(pro_name);
				productVO.setPro_date(pro_date);
				productVO.setPro_intro(pro_intro);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/shopping/product/addProduct.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				ProductService productSvc = new ProductService();
				productVO = productSvc.addProduct(pro_no,mem_no, proc_no,
						pro_date, pro_name, pro_price, pro_intro, pro_photo,
						pro_photo1, pro_photo2, pro_photo3, pro_photo4 , pro_photo5,
						pro_stat, pro_pay, pro_get) ;
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/frontend/shopping/product/listAllProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				System.out.println("�ڲ{�b�q�ӫ~insert�i�Ө��L�i�઺���~�B�z");
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/shopping/product/addProduct.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
			System.out.println("�]�Ӱӫ~delete�϶�");
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				String pro_no = new String(req.getParameter("pro_no"));
				
				/***************************2.�}�l�R�����***************************************/
				ProductService productSvc = new ProductService();
				ProductVO productVO = productSvc.getOneProduct(pro_no);
				productSvc.deleteProduct(pro_no);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
				Product_ClassificationService product_classificationSvc = new Product_ClassificationService();
				if(requestURL.equals("/frontend/shopping/product_classification/listProducts_ByProc_no.jsp")||requestURL.equals("/frontend/shopping/product_classification/listAllProduct_Classification.jsp"))
					req.setAttribute("listProducts_ByProc_no", product_classificationSvc.getProductsByProc_no(productVO.getProc_no()));
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
	}
	

	
}

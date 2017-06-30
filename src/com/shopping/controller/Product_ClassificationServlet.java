package com.shopping.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


import com.shopping.model.*;

public class Product_ClassificationServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");
System.out.println("�ӫ~����action=" +action);
		    // �Ӧ�select_page.jsp���ШD                                  // �Ӧ� dept/listAllQuestion_Classification.jsp���ШD
		if ("listProducts_ByProc_no_A".equals(action) || "listProducts_ByProc_no_B".equals(action)) {
	
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

//			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				String proc_no = new String(req.getParameter("proc_no"));
System.out.println("proc_no="+proc_no);
				/*************************** 2.�}�l�d�߸�� ****************************************/
				Product_ClassificationService product_classificationSvc = new Product_ClassificationService();
				Set<ProductVO> set = product_classificationSvc.getProductsByProc_no(proc_no);
System.out.println("set="+set);
				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
				req.setAttribute("listProducts_ByProc_no", set);    // ��Ʈw���X��set����,�s�Jrequest

				String url = null;
				if ("listProducts_ByProc_no_A".equals(action))
					url = "/frontend/shopping/product_classification/listProducts_ByProc_no.jsp";        // ���\��� /frontend/question_classification/listQuestions_ByQuec_no.jsp
				else if ("listProducts_ByProc_no_B".equals(action))
					url = "/frontend/shopping/product_classification/listAllProduct_Classification.jsp";              // ���\��� /frontend/question_classification/listAllQuestion_Classification.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z ***********************************/
//			} catch (Exception e) {
//				throw new ServletException(e);
//			}
		}
		
	}
}

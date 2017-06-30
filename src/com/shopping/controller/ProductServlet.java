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
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("pro_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入商品編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/shopping/product/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String pro_no = null;
				try {
					pro_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("商品編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/shopping/product/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				ProductService productSvc = new ProductService();
				ProductVO productVO = productSvc.getOneProduct(pro_no);
				if (productVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/shopping/product/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productVO", productVO); // 資料庫取出的questionVO物件,存入req
				String url = "/frontend/shopping/product/listOneProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/shopping/product/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/listAllQuestion.jsp】 或  【/listQuestions_ByQuec_no.jsp】 或 【 /listAllQuestion_Classification.jsp】	
			
//			try {
				/***************************1.接收請求參數****************************************/
				String pro_no = new String(req.getParameter("pro_no"));
				
				/***************************2.開始查詢資料****************************************/
				ProductService productSvc = new ProductService();
				ProductVO productVO = productSvc.getOneProduct(pro_no);

								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("productVO", productVO);         // 資料庫取出的empVO物件,存入req
				String url = "/frontend/shopping/product/update_product_input.jsp";
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
System.out.println("有進來商品修改區塊唷");
//			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String pro_no = new String(req.getParameter("pro_no").trim());
				String pro_name = req.getParameter("pro_name").trim();	
System.out.println("pro_no = "+ pro_no);
				
				java.sql.Timestamp pro_date = new java.sql.Timestamp(System.currentTimeMillis());
//				try {
//					pro_date = java.sql.Timestamp.valueOf(req.getParameter("pro_date").trim());
//				} catch (IllegalArgumentException e) {
//					pro_date=new java.sql.Timestamp(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}

				String mem_no = null;
				try {
					mem_no = new String(req.getParameter("mem_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("會員編號請填寫.");
				}

				String proc_no = null;
				try {
					proc_no = new String(req.getParameter("proc_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("商品分類編號請填寫.");
				}
				
				Integer pro_price = null;
				try {
					pro_price = new Integer(req.getParameter("pro_price"));
				} catch (NumberFormatException e) {
					errorMsgs.add("商品價格請填寫.");
				}
				
				String pro_intro = req.getParameter("pro_intro").trim();
				
				String pro_stat = null;
				try {
					pro_stat = new String(req.getParameter("pro_stat").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("商品狀態請填寫.");
				}
				
				String pro_pay = null;
				try {
					pro_pay = new String(req.getParameter("pro_pay").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("付款方式請填寫.");
				}
				
				String pro_get = null;
				try {
					pro_get = new String(req.getParameter("pro_get").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("送貨方式請填寫.");
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
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/shopping/product/update_product_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				System.out.println("跑來修改區塊 修改成功");
				/***************************2.開始修改資料*****************************************/
				ProductService productSvc = new ProductService();
productVO = productSvc.updateProduct(pro_no,mem_no, proc_no,
		pro_date, pro_name, pro_price, pro_intro, pro_photo,
		pro_photo1, pro_photo2, pro_photo3, pro_photo4 , pro_photo5,
		pro_stat, pro_pay, pro_get);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				Product_ClassificationService product_classificationSvc = new Product_ClassificationService();
				if(requestURL.equals("/frontend/shopping/product_classification/listProducts_ByProc_no.jsp")||requestURL.equals("/frontend/shopping/product_classification/listAllProduct_Classification.jsp"))
					req.setAttribute("listProducts_ByProc_no", product_classificationSvc.getProductsByProc_no(proc_no));
//req.setAttribute("questionVO", questionVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
//			} catch (Exception e) {
//				errorMsgs.add("修改資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/frontend/shopping/product/update_product_input.jsp");
//				failureView.forward(req, res);
//			}
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
System.out.println("有進來商品insert區塊喔");
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String pro_no = null;
				try {
					pro_no = new String(req.getParameter("pro_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("問題編號請填寫.");
				}
				
				String mem_no = null;
				try {
					mem_no = new String(req.getParameter("mem_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("會員編號請填寫.");
				}
				
				
				String pro_name = null;
				try {
					pro_name = new String(req.getParameter("pro_name").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("商品名稱請填寫.");
				}
				
				String pro_intro = null;
				try {
					pro_intro = new String(req.getParameter("pro_intro").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("商品內容請填寫.");
				}
				
				
				java.sql.Timestamp pro_date = null;
				try {
					pro_date = java.sql.Timestamp.valueOf(req.getParameter("pro_date").trim());
				} catch (IllegalArgumentException e) {
					pro_date=new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				String proc_no = null;
				try {
					proc_no = new String(req.getParameter("proc_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("問題分類請填寫.");
				}
				
				Integer pro_price = null;
					pro_price = new Integer(req.getParameter("pro_price"));
					
				String pro_stat = null;
				try {
					pro_stat = new String(req.getParameter("pro_stat").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("商品狀態請填寫.");
				}
				
				String pro_pay = null;
				try {
					pro_pay = new String(req.getParameter("pro_pay").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("付款方式請填寫.");
				}
				
				String pro_get = null;
				try {
					pro_get = new String(req.getParameter("pro_get").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("送貨方式請填寫.");
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
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/shopping/product/addProduct.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				ProductService productSvc = new ProductService();
				productVO = productSvc.addProduct(pro_no,mem_no, proc_no,
						pro_date, pro_name, pro_price, pro_intro, pro_photo,
						pro_photo1, pro_photo2, pro_photo3, pro_photo4 , pro_photo5,
						pro_stat, pro_pay, pro_get) ;
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/frontend/shopping/product/listAllProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				System.out.println("我現在從商品insert進來到其他可能的錯誤處理");
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/shopping/product/addProduct.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
			System.out.println("跑來商品delete區塊");
			try {
				/***************************1.接收請求參數***************************************/
				String pro_no = new String(req.getParameter("pro_no"));
				
				/***************************2.開始刪除資料***************************************/
				ProductService productSvc = new ProductService();
				ProductVO productVO = productSvc.getOneProduct(pro_no);
				productSvc.deleteProduct(pro_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				Product_ClassificationService product_classificationSvc = new Product_ClassificationService();
				if(requestURL.equals("/frontend/shopping/product_classification/listProducts_ByProc_no.jsp")||requestURL.equals("/frontend/shopping/product_classification/listAllProduct_Classification.jsp"))
					req.setAttribute("listProducts_ByProc_no", product_classificationSvc.getProductsByProc_no(productVO.getProc_no()));
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
	}
	

	
}

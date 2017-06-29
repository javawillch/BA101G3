package com.map.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import com.map.model.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)


public class Map_MechanismServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String map_no = req.getParameter("map_no");
				if (map_no == null || (map_no.trim()).length() == 0) {
					errorMsgs.add("�п�J�a�Ͼ��c�s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/map/select_map_mechanism_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/map/select_map_mechanism_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				Map_MechanismService map_mechanismSvc = new Map_MechanismService();
				Map_MechanismVO map_mechanismVO = map_mechanismSvc.getOneMap_Mechanism(map_no);
				if (map_mechanismVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/map/select_map_mechanism_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("map_mechanismVO", map_mechanismVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/backend/map/listOneMap_Mechanism.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/map/select_map_mechanism_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllEmp.jsp ��  /dept/listEmps_ByDeptno.jsp ���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|: �i�ର�i/emp/listAllEmp.jsp�j ��  �i/dept/listEmps_ByDeptno.jsp�j �� �i /dept/listAllDept.jsp�j		
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				String map_no = req.getParameter("map_no");
				
				/***************************2.�}�l�d�߸��****************************************/
				Map_MechanismService map_mechanismSvc = new Map_MechanismService();
				Map_MechanismVO map_mechanismVO = map_mechanismSvc.getOneMap_Mechanism(map_no);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("map_mechanismVO", map_mechanismVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/backend/map/update_map_mechanism_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���update_emp_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƨ��X�ɥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // �Ӧ�update_emp_input.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|: �i�ର�i/emp/listAllEmp.jsp�j ��  �i/dept/listEmps_ByDeptno.jsp�j �� �i /dept/listAllDept.jsp�j
		
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String map_no = req.getParameter("map_no").trim();
				String map_name = req.getParameter("map_name").trim();
				String map_adds = req.getParameter("map_adds").trim();	
				String map_addc = req.getParameter("map_addc").trim();
				String map_phone = req.getParameter("map_phone").trim();
				String map_mail = toRealNull(req.getParameter("map_mail").trim());
				String map_cnt = req.getParameter("map_cnt").trim();
				// TODO �ݽվ�A�|���ǤJ�Ѽ�
				String mem_no = toRealNull(req.getParameter("mem_no").trim());
				
				String mapc_no = req.getParameter("mapc_no").trim();
				Double map_long = new Double(req.getParameter("map_long").trim());
				Double map_lat = new Double(req.getParameter("map_lat").trim());
				byte[] map_photo = getPictureByteArray(req.getPart("map_photo"));
				byte[] map_photo1 = getPictureByteArray(req.getPart("map_photo1"));
				byte[] map_photo2 = getPictureByteArray(req.getPart("map_photo2"));
				byte[] map_photo3 = getPictureByteArray(req.getPart("map_photo3"));
				byte[] map_photo4 = getPictureByteArray(req.getPart("map_photo4"));
				byte[] map_photo5 = getPictureByteArray(req.getPart("map_photo5"));
				
				Map_MechanismVO map_mechanismVO = new Map_MechanismVO();
				
				map_mechanismVO.setMap_no(map_no);
				map_mechanismVO.setMem_no(mem_no);
				map_mechanismVO.setMapc_no(mapc_no);
				map_mechanismVO.setMap_name(map_name);
				map_mechanismVO.setMap_adds(map_adds);
				map_mechanismVO.setMap_addc(map_addc);
				map_mechanismVO.setMap_long(map_long);
				map_mechanismVO.setMap_lat(map_lat);
				map_mechanismVO.setMap_phone(map_phone);
				map_mechanismVO.setMap_mail(map_mail);
				map_mechanismVO.setMap_cnt(map_cnt);
				map_mechanismVO.setMap_photo(map_photo);
				map_mechanismVO.setMap_photo1(map_photo1);
				map_mechanismVO.setMap_photo2(map_photo2);
				map_mechanismVO.setMap_photo3(map_photo3);
				map_mechanismVO.setMap_photo4(map_photo4);
				map_mechanismVO.setMap_photo5(map_photo5);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("map_mechanismVO", map_mechanismVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/map/update_map_mechanism_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				Map_MechanismService map_mechanismSvc = new Map_MechanismService();
				map_mechanismVO = map_mechanismSvc.updateMap_Mechanism(map_no, mem_no, mapc_no,
						map_name, map_adds, map_addc, map_long, map_lat, map_phone, map_mail, 
						map_cnt, map_photo, map_photo1, map_photo2, map_photo3, map_photo4, map_photo5);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/				
                String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);   // �ק令�\��,���^�e�X�ק諸�ӷ�����
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/map/update_map_mechanism_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
				String mapc_no = req.getParameter("mapc_no").trim();
				String map_name = req.getParameter("map_name").trim();
				String map_adds = req.getParameter("map_adds").trim();
				String map_addc = req.getParameter("map_addc").trim();
				String map_phone = req.getParameter("map_phone").trim();
				String map_mail = req.getParameter("map_mail").trim();
				String map_cnt = req.getParameter("map_cnt").trim();
				byte[] map_photo = getPictureByteArray(req.getPart("map_photo"));
				
				

				Map_MechanismVO map_mechanismVO = new Map_MechanismVO();
				map_mechanismVO.setMapc_no(mapc_no);
				map_mechanismVO.setMap_name(map_name);
				map_mechanismVO.setMap_adds(map_adds);
				map_mechanismVO.setMap_addc(map_addc);
				map_mechanismVO.setMap_phone(map_phone);
				map_mechanismVO.setMap_mail(map_mail);
				map_mechanismVO.setMap_cnt(map_cnt);
				map_mechanismVO.setMap_photo(map_photo);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("map_mechanismVO", map_mechanismVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/map/addMap_Mechanism.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				Map_MechanismService map_mechanismSvc = new Map_MechanismService();
				map_mechanismVO = map_mechanismSvc.addMap_Mechanism(null, mapc_no, map_name, map_adds, 
						          map_addc, 123.123, 11.11, map_phone, map_mail, map_cnt, map_photo, null, null, null, null, null);
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/backend/map/listAllMap_Mechanism.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/map/addMap_Mechanism.jsp");
				failureView.forward(req, res);
			}
		}
		
       
		if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp ��  /dept/listEmps_ByDeptno.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // �e�X�R�����ӷ��������|: �i�ର�i/emp/listAllEmp.jsp�j ��  �i/dept/listEmps_ByDeptno.jsp�j �� �i /dept/listAllDept.jsp�j

			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				String map_no = req.getParameter("map_no");
				
				/***************************2.�}�l�R�����***************************************/
				Map_MechanismService map_mechanismSvc = new Map_MechanismService();
				Map_MechanismVO map_mechanismVO = map_mechanismSvc.getOneMap_Mechanism(map_no);
				map_mechanismSvc.deleteMap_Mechanism(map_no);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/
//				DeptService deptSvc = new DeptService();
//				if(requestURL.equals("/dept/listEmps_ByDeptno.jsp") || requestURL.equals("/dept/listAllDept.jsp"))
//					req.setAttribute("listEmps_ByDeptno",deptSvc.getEmpsByDeptno(empVO.getDeptno())); // ��Ʈw���X��list����,�s�Jrequest
				
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // �R�����\��,���^�e�X�R�����ӷ�����
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
	public static byte[] getPictureByteArray(Part photo) throws IOException {
		InputStream is = photo.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = is.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		is.close();

		return baos.toByteArray();
	}
	
	public String toRealNull(String str) {
		if(str.equals("null")) {
			return null;
		}else {
			return str;
		}
		
	}
}

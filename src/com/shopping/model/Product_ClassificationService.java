package com.shopping.model;


import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.shopping.model.*;

public class Product_ClassificationService {

	private Product_ClassificationDAO_interface dao;

	public Product_ClassificationService() {
		dao = new Product_ClassificationDAO();
	}

	public Product_ClassificationVO addProduct_Classification(String proc_no, String proc_name)  {

		Product_ClassificationVO product_classificationVO = new Product_ClassificationVO();

		product_classificationVO.setProc_no(proc_no);
		product_classificationVO.setProc_name(proc_name);
		dao.insert(product_classificationVO);
		return product_classificationVO;
	}

	public Product_ClassificationVO updateProduct_Classification(String proc_no, String proc_name) {

		Product_ClassificationVO product_classificationVO = new Product_ClassificationVO();

		product_classificationVO.setProc_no(proc_no);
		product_classificationVO.setProc_name(proc_name);
		dao.update(product_classificationVO);
		return product_classificationVO;
	}
	

	public Product_ClassificationVO getOneProduct_Classifiction(String proc_no) {
		return dao.findByPrimaryKey(proc_no);
	}

	public List<Product_ClassificationVO> getAll() {
		return dao.getAll();
	}
	
	
	public Set<ProductVO> getProductsByProc_no(String proc_no){
		return dao.getProductsByProc_no(proc_no);
	}
}

package com.shopping.model;

import java.util.*;

import com.shopping.model.ProductVO;

public interface Product_ClassificationDAO_interface {
	      public void insert(Product_ClassificationVO product_classificationVO);
          public void update(Product_ClassificationVO product_classificationVO);
          public Product_ClassificationVO findByPrimaryKey(String proc_no);
	      public List<Product_ClassificationVO> getAll();
	      //�d�߬Y���ӫ~���ӫ~(�@��h)(�^��set)
	      public Set<ProductVO> getProductsByProc_no(String proc_no);
}

package com.shopping.model;

import java.sql.Timestamp;
import java.util.List;
import com.shopping.model.ProductVO;

public class ProductService {

	private ProductDAO_interface dao;

	public ProductService() {
		dao = new ProductDAO();
	}

	public ProductVO addProduct(String pro_no, String mem_no, String proc_no,
			Timestamp pro_date, String pro_name, Integer pro_price, String pro_intro, byte[] pro_photo,
			byte[] pro_photo1,byte[] pro_photo2,byte[] pro_photo3,byte[] pro_photo4 ,byte[] pro_photo5,
			String pro_stat, String pro_pay, String pro_get) {

		ProductVO productVO = new ProductVO();

		productVO.setPro_no(pro_no);  // Not Null (PK)
		productVO.setMem_no(mem_no);  // Not Null (FK)
		productVO.setProc_no(proc_no); // Not Null (FK)
		productVO.setPro_date(pro_date);   // Not Null
		productVO.setPro_name(pro_name);   // Not Null
		productVO.setPro_price(pro_price); // Not Null
		productVO.setPro_intro(pro_intro); // Not Null
		productVO.setPro_photo(pro_photo); // Not Null
		productVO.setPro_photo1(pro_photo1);
		productVO.setPro_photo2(pro_photo2);
		productVO.setPro_photo3(pro_photo3);
		productVO.setPro_photo4(pro_photo4);
		productVO.setPro_photo5(pro_photo5);
		productVO.setPro_stat(pro_stat);   // Not Null 0：上架中 1：下架 2：完成交易未付款 3：完成交易已付款
		productVO.setPro_pay(pro_pay);     // Not Null 0：貨到付款 1：ATM轉帳 2：兩者皆可
		productVO.setPro_get(pro_get);     // Not Null 0：便利商店取貨 1：快遞送貨 2：兩者皆可

		dao.insert(productVO);
		
		return productVO;
	}

	public ProductVO updateProduct(String pro_no, String mem_no, String proc_no,
			Timestamp pro_date, String pro_name, Integer pro_price, String pro_intro, byte[] pro_photo,
			byte[] pro_photo1,byte[] pro_photo2,byte[] pro_photo3,byte[] pro_photo4 ,byte[] pro_photo5,
			String pro_stat, String pro_pay, String pro_get) {
		
		ProductVO productVO = new ProductVO();

		productVO.setPro_no(pro_no);  // Not Null (PK)
		productVO.setMem_no(mem_no);  // Not Null (FK)
		productVO.setProc_no(proc_no); // Not Null (FK)
		productVO.setPro_date(pro_date);   // Not Null
		productVO.setPro_name(pro_name);   // Not Null
		productVO.setPro_price(pro_price); // Not Null
		productVO.setPro_intro(pro_intro); // Not Null
		productVO.setPro_photo(pro_photo); // Not Null
		productVO.setPro_photo1(pro_photo1);
		productVO.setPro_photo2(pro_photo2);
		productVO.setPro_photo3(pro_photo3);
		productVO.setPro_photo4(pro_photo4);
		productVO.setPro_photo5(pro_photo5);
		productVO.setPro_stat(pro_stat);   // Not Null 0：上架中 1：下架 2：完成交易未付款 3：完成交易已付款
		productVO.setPro_pay(pro_pay);     // Not Null 0：貨到付款 1：ATM轉帳 2：兩者皆可
		productVO.setPro_get(pro_get);     // Not Null 0：便利商店取貨 1：快遞送貨 2：兩者皆可

		dao.insert(productVO);
		
		return productVO;
	}
	

	public void deleteProduct(String pro_no) {
		dao.delete(pro_no);
	}

	public ProductVO getOneProduct(String pro_no) {
		return dao.findByPrimaryKey(pro_no);
	}

	public List<ProductVO> getAll() {
		return dao.getAll();
	}
}

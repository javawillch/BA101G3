package com.shopping.model;

import java.util.*;

public interface Buyer_ReportDAO_interface {
	      public void insert(Buyer_ReportVO buyer_reportVO);
          public void update(Buyer_ReportVO buyer_reportVO);
          public Buyer_ReportVO findByPrimaryKey(String ord_no, String mem_no);
	      public List<Buyer_ReportVO> getAll();
	      public String findSellerMem_noByOrder_no (String ord_no);
}

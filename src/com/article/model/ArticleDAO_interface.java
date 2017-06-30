package com.article.model;

import java.util.*;

public interface ArticleDAO_interface {
	      public void insert(ArticleVO articleVO);
          public void update(ArticleVO articleVO);
          public void delete(String art_no);
          public ArticleVO findByPrimaryKey(String art_no);
	      public List<ArticleVO> getAll();
	      //�W�[�s����
	      public void update_art_vcnt(Integer art_vcnt , String art_no);
	      //��������Ҧ��峹
	      public List<ArticleVO> getAllByArtc_no(String artc_no);
	      //����r�j�M
	      public List<ArticleVO> searchPattern(String pattern);
	      //���@�|�����Ҧ��峹
	      public List<ArticleVO> getAllByMem_no(String mem_no);
}

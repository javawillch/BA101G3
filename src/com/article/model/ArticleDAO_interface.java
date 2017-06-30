package com.article.model;

import java.util.*;

public interface ArticleDAO_interface {
	      public void insert(ArticleVO articleVO);
          public void update(ArticleVO articleVO);
          public void delete(String art_no);
          public ArticleVO findByPrimaryKey(String art_no);
	      public List<ArticleVO> getAll();
	      //增加瀏覽數
	      public void update_art_vcnt(Integer art_vcnt , String art_no);
	      //找分類內所有文章
	      public List<ArticleVO> getAllByArtc_no(String artc_no);
	      //關鍵字搜尋
	      public List<ArticleVO> searchPattern(String pattern);
	      //找單一會員的所有文章
	      public List<ArticleVO> getAllByMem_no(String mem_no);
}

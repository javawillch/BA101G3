package com.article.model;

import java.util.*;

public interface Article_ClassificationDAO_interface {
	      public void insert(Article_ClassificationVO article_classificationVO);
          public void update(Article_ClassificationVO article_classificationVO);
          public Article_ClassificationVO findByPrimaryKey(String artc_no);
	      public List<Article_ClassificationVO> getAll();
	      public Set<ArticleVO> getArticlesByArtc_no(String artc_no);
}

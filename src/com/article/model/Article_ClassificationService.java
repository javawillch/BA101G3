package com.article.model;


import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.article.model.ArticleVO;
import com.article.model.*;

public class Article_ClassificationService {

	private Article_ClassificationDAO_interface dao;

	public Article_ClassificationService() {
		dao = new Article_ClassificationDAO();
	}

	public Article_ClassificationVO addArticle_Classification(String artc_no, String artc_name)  {

		Article_ClassificationVO article_classificationVO = new Article_ClassificationVO();

		article_classificationVO.setArtc_no(artc_no);
		article_classificationVO.setArtc_name(artc_name);
		dao.insert(article_classificationVO);

		return article_classificationVO;
	}

	public Article_ClassificationVO updateArticle_Classification(String artc_no, String artc_name) {

		Article_ClassificationVO article_classificationVO = new Article_ClassificationVO();

		article_classificationVO.setArtc_no(artc_no);
		article_classificationVO.setArtc_name(artc_name);
		dao.update(article_classificationVO);

		return article_classificationVO;
	}
	

//	public void deleteArticle_Classification(String artc_no) {
//		dao.delete(artc_no);
//	}

	public Article_ClassificationVO getOneArticle_Classifiction(String artc_no) {
		return dao.findByPrimaryKey(artc_no);
	}

	public List<Article_ClassificationVO> getAll() {
		return dao.getAll();
	}
	
	
	public Set<ArticleVO> getArticlesByArtc_no(String artc_no){
		return dao.getArticlesByArtc_no(artc_no);
	}
}

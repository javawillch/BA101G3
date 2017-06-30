package com.article.model;

import java.sql.Timestamp;
import java.util.List;

public class Article_FavoriteService {

	private Article_FavoriteDAO_interface dao;

	public Article_FavoriteService() {
		dao = new Article_FavoriteDAO();
	}

	public Article_FavoriteVO addArticle_Favorite(String mem_no,String art_no) {

		Article_FavoriteVO article_favoriteVO = new Article_FavoriteVO();
		article_favoriteVO.setMem_no(mem_no);
		article_favoriteVO.setArt_no(art_no);
		dao.insert(article_favoriteVO);

		return article_favoriteVO;
	}

	
	public void deleteArticle_Favorite(String mem_no,String art_no) {
		dao.delete(mem_no, art_no);
	}

	public Article_FavoriteVO getOneArticle_Favorite(String mem_no,String art_no) {
		return dao.findByPrimaryKey(mem_no, art_no);
	}

	public List<Article_FavoriteVO> getAll() {
		return dao.getAll();
	}
	
	public List<Article_FavoriteVO> findByMemNo(String mem_no){
		return dao.findByMemNo(mem_no);
	}
}

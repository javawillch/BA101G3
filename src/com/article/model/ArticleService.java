package com.article.model;

import java.sql.Timestamp;
import java.util.List;

public class ArticleService {

	private ArticleDAO_interface dao;

	public ArticleService() {
		dao = new ArticleDAO();
	}

	public ArticleVO addArticle(String art_no, String mem_no, String artc_no,
			String art_title, Timestamp art_date, String art_cnt, Integer art_vcnt) {

		ArticleVO articleVO = new ArticleVO();
		articleVO.setArt_no(art_no);
		articleVO.setMem_no(mem_no);
		articleVO.setArtc_no(artc_no);
		articleVO.setArt_title(art_title);
		articleVO.setArt_date(art_date);
		articleVO.setArt_cnt(art_cnt);
		articleVO.setArt_vcnt(art_vcnt);
		dao.insert(articleVO);

		return articleVO;
	}

	public ArticleVO updateArticle(String art_no, String mem_no, String artc_no,
			String art_title, Timestamp art_date, String art_cnt, Integer art_vcnt) {

		ArticleVO articleVO = new ArticleVO();

		articleVO.setArt_no(art_no);
		articleVO.setMem_no(mem_no);
		articleVO.setArtc_no(artc_no);
		articleVO.setArt_title(art_title);
		articleVO.setArt_date(art_date);
		articleVO.setArt_cnt(art_cnt);
		articleVO.setArt_vcnt(art_vcnt);
		dao.update(articleVO);

		return articleVO;
	}
	
	public void deleteArticle(String art_no) {
		dao.delete(art_no);
	}

	public ArticleVO getOneArticle(String art_no) {
		return dao.findByPrimaryKey(art_no);
	}

	public List<ArticleVO> getAll() {
		return dao.getAll();
	}
	
	//�W�[�s����
	public void update_art_vcnt(Integer art_vcnt , String art_no) {
		dao.update_art_vcnt(art_vcnt, art_no);
	}
	
	//�̤峹������h���峹
	public List<ArticleVO> getAllByArtc_no(String artc_no){
		return dao.getAllByArtc_no(artc_no);
	}
	
	//����r�j�M
	public List<ArticleVO> searchPattern(String pattern){
		return dao.searchPattern(pattern);
	}
	
	//�̷|���s����h���峹
	public List<ArticleVO> getAllByMem_no(String mem_no){
		return dao.getAllByMem_no(mem_no);
	}
}

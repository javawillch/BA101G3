package com.article.model;

import java.sql.Timestamp;
import java.util.List;

public class Article_MessageService {

	private Article_MessageDAO_interface dao;

	public Article_MessageService() {
		dao = new Article_MessageDAO();
	}

	public Article_MessageVO addArticle_Message(String amsg_no, String art_no, String mem_no, Timestamp amsg_date,
			String amsg_cnt) {

		Article_MessageVO article_messageVO = new Article_MessageVO();
		article_messageVO.setAmsg_no(amsg_no);
		article_messageVO.setArt_no(art_no);
		article_messageVO.setMem_no(mem_no);
		article_messageVO.setAmsg_date(amsg_date);
		article_messageVO.setAmsg_cnt(amsg_cnt);
		dao.insert(article_messageVO);

		return article_messageVO;
	}

	
	public void deleteArticle_Message(String amsg_no) {
		dao.delete(amsg_no);
	}

	public Article_MessageVO getOneArticle_Message(String amsg_no) {
		return dao.findByPrimaryKey(amsg_no);
	}

	public List<Article_MessageVO> getAll() {
		return dao.getAll();
	}
}

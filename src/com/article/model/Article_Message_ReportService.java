package com.article.model;

import java.sql.Timestamp;
import java.util.List;

public class Article_Message_ReportService {

	private Article_Message_ReportDAO_interface dao;

	public Article_Message_ReportService() {
		dao = new Article_Message_ReportDAO();
	}

	public Article_Message_ReportVO addArticle_Message_Report(String amsg_no, String mem_no, Timestamp amrpt_date,
			String amrpt_rsn, String amrpt_is_cert, String amrpt_unrsn) {

		Article_Message_ReportVO article_message_reportVO = new Article_Message_ReportVO();
		article_message_reportVO.setAmsg_no(amsg_no);
		article_message_reportVO.setMem_no(mem_no);
		article_message_reportVO.setAmrpt_date(amrpt_date);
		article_message_reportVO.setAmrpt_rsn(amrpt_rsn);
		article_message_reportVO.setAmrpt_is_cert(amrpt_is_cert);
		article_message_reportVO.setAmrpt_unrsn(amrpt_unrsn);
		dao.insert(article_message_reportVO);

		return article_message_reportVO;
	}


	public Article_Message_ReportVO getOneArticle_Message_Report(String amsg_no, String mem_no) {
		return dao.findByPrimaryKey(amsg_no, mem_no);
	}

	public List<Article_Message_ReportVO> getAll() {
		return dao.getAll();
	}
}

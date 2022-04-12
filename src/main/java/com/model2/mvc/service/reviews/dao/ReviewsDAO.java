package com.model2.mvc.service.reviews.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Reviews;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.reviews.impl.ReviewsServiceImpl;

public class ReviewsDAO {

	public ReviewsDAO() {

	}

	public void insertReview(Reviews reviews) throws Exception {
		System.out.println("<<<<< ReviewsDAO : insertReview() 시작 >>>>>");

		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO reviews VALUES(seq_reviews_review_no.nextval,?,?,SYSDATE)";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, reviews.getTranNo().getTranNo());
		stmt.setString(2, reviews.getText());

		System.out.println("Review insert sql :" + sql);

		stmt.executeUpdate();

		con.close();
		System.out.println("<<<<< ReviewsDAO : insertReview() 끝 >>>>>");

	}

	public Reviews findReview(int reviewNo) throws Exception {
		System.out.println("<<<<< ReviewsDAO : findReview() 시작 >>>>>");

		Connection con = DBUtil.getConnection();

		String sql = "select " + " re.review_no, t.tran_no, p.prod_name, p.image_file, u.user_id, re.reg_date "
				+ " from reviews re, transaction t, product p, users u " + " where re.tran_no = t.tran_no "
				+ " and t.buyer_id = u.user_id " + " and t.prod_no = p.prod_no " + " and re.tran_no = ?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, reviewNo);

		ResultSet rs = stmt.executeQuery();

		System.out.println("DAO reviewNo : " + reviewNo);

		Reviews reviews = new Reviews();
		while (rs.next()) {
			Purchase purchase = new Purchase();
			Product product = new Product();
			User user = new User();
			reviews.setReviewNo(rs.getInt("review_no"));
			purchase.setTranNo(rs.getInt("tran_no"));
			reviews.setTranNo(purchase);
			user.setUserId(rs.getString("user_id"));
			product.setProdName(rs.getString("prod_name"));
			product.setFileName(rs.getString("image_file"));
			reviews.setReviewNo(rs.getInt("review_no"));
			reviews.setRegDate(rs.getDate("reg_date"));
		}
		System.out.println("DAO reviews :" + reviews);

		con.close();
		System.out.println("<<<<< ReviewsDAO : findReview() 끝 >>>>>");

		return reviews;
	}

	public Map<String, Object> getReviewList(Search search, String userId) throws Exception {
		System.out.println("<<<<< ReviewsDAO : getReviewList() 시작 >>>>>");
		System.out.println("받은 search : " + search);
		System.out.println("받은 userId : " + userId);

		Connection con = DBUtil.getConnection();

		String sql = "SELECT  u.user_id, p.prod_name, re.reg_date from  reviews re, transaction t, product p, users u "
				+ "where ";
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0") && !search.getSearchKeyword().equals("")) {
				sql += " user_id LIKE '%" + search.getSearchKeyword() + "%' and";
			} else if (search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
				sql += " prod_name LIKE '%" + search.getSearchKeyword() + "%' and";
			}
		}
		sql += " re.tran_no = t.tran_no ";
		sql += " and t.buyer_id = u.user_id ";
		sql += " and t.prod_no = p.prod_no ";
		sql += " and u.user_id = '" + userId + "'";
		sql += " order by user_id";

		int totalCount = this.getTotalCount(sql);
		System.out.println("totalCount : " + totalCount);

		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		System.out.println("sql 전송완료 : " + sql);

		Map<String, Object> map = new HashMap<String, Object>();
		List<Reviews> list = new ArrayList<Reviews>();

		while (rs.next()) {
			Reviews reviews = new Reviews();
			User user = new User();
			Product product = new Product();
			user.setUserId(rs.getString("user_id"));
			product.setProdName(rs.getString("prod_name"));
			reviews.setRegDate(rs.getDate("reg_date"));
			list.add(reviews);
			System.out.println("reviews 셋팅완료 : " + reviews);
		}
		// ==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		System.out.println("map에 totalCount 추가 : " + map);

		// ==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);
		System.out.println("map.put list" + list);

		System.out.println("list.size() : " + list.size());
		System.out.println("map.size() : " + map.size());

		rs.close();
		pStmt.close();
		con.close();
		System.out.println("<<<<< ReviewsDAO : getReviewList() 끝 >>>>>");

		return map;
	}
	
	public void updateReview(Reviews reviews) throws Exception{
		System.out.println("<<<<< ReviewsDAO : updateReview() 시작 >>>>>");

		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE reviews SET text=? WHERE review_no = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, reviews.getText());
		stmt.setInt(2, reviews.getReviewNo());
		
		System.out.println("update SQL : "+stmt);
		stmt.executeUpdate();
		
		con.close();
		System.out.println("<<<<< ReviewsDAO : updateReview() 끝 >>>>>");

	}
	private int getTotalCount(String sql) throws Exception {

		sql = "SELECT COUNT(*) " + "FROM ( " + sql + ") countTable";

		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();

		int totalCount = 0;
		if (rs.next()) {
			totalCount = rs.getInt(1);
		}

		pStmt.close();
		con.close();
		rs.close();

		return totalCount;
	}

	// 게시판 currentPage Row 만 return
	private String makeCurrentPageSql(String sql, Search search) {
		sql = "SELECT * FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " + " 	FROM (	" + sql
				+ " ) inner_table " + "	WHERE ROWNUM <=" + search.getCurrentPage() * search.getPageSize() + ")"
				+ " WHERE row_seq BETWEEN " + ((search.getCurrentPage() - 1) * search.getPageSize() + 1) + " AND "
				+ search.getCurrentPage() * search.getPageSize();

		System.out.println("UserDAO :: make SQL :: " + sql);

		return sql;
	}
}
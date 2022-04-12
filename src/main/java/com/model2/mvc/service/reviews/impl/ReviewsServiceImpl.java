package com.model2.mvc.service.reviews.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.reviews.ReviewsService;
import com.model2.mvc.service.reviews.dao.ReviewsDAO;
import com.model2.mvc.service.domain.Reviews;

public class ReviewsServiceImpl implements ReviewsService {
	
	private ReviewsDAO reviewsDAO;
	
	public ReviewsServiceImpl() {
		reviewsDAO = new ReviewsDAO();
	}

	public void addReview(Reviews reviews) throws Exception {
		reviewsDAO.insertReview(reviews);
	}

	public Reviews getReivew(int reviewNo) throws Exception {
		return reviewsDAO.findReview(reviewNo);
	}

	public Map<String, Object> getReviewList(Search search, String userId) throws Exception {
		return reviewsDAO.getReviewList(search, userId);
	}

	public void updateReview(Reviews reviews) throws Exception {
		reviewsDAO.updateReview(reviews);
	}


}
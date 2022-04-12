package com.model2.mvc.view.reviews;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.reviews.ReviewsService;
import com.model2.mvc.service.reviews.impl.ReviewsServiceImpl;
import com.model2.mvc.service.domain.Reviews;

public class AddReviewsAction extends Action {//��ǰ��� ��û

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< AddReviewsAction : execute() ���� >>>>>");
		
		Reviews reviews = new Reviews();
		reviews.setText(request.getParameter("text"));                    //��ǰ��
		System.out.println("reviewsVO ���ÿϷ� : " + reviews);
		
		ReviewsService service = new ReviewsServiceImpl();
		service.addReviews(reviews);
		
		//���1
		request.setAttribute("reviews", reviews);
		
		//���2
//		HttpSession session = request.getSession(true);		
//		session.setAttribute("reviews",reviews);
		
		System.out.println("<<<<< AddReviewsAction : execute() ���� >>>>>");
		return "forward:/reviews/addReviews.jsp";
	}
}
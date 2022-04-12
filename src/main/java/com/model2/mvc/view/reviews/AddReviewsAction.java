package com.model2.mvc.view.reviews;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.reviews.ReviewsService;
import com.model2.mvc.service.reviews.impl.ReviewsServiceImpl;
import com.model2.mvc.service.domain.Reviews;

public class AddReviewsAction extends Action {//상품등록 요청

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< AddReviewsAction : execute() 시작 >>>>>");
		
		Reviews reviews = new Reviews();
		reviews.setText(request.getParameter("text"));                    //상품명
		System.out.println("reviewsVO 셋팅완료 : " + reviews);
		
		ReviewsService service = new ReviewsServiceImpl();
		service.addReviews(reviews);
		
		//방법1
		request.setAttribute("reviews", reviews);
		
		//방법2
//		HttpSession session = request.getSession(true);		
//		session.setAttribute("reviews",reviews);
		
		System.out.println("<<<<< AddReviewsAction : execute() 종료 >>>>>");
		return "forward:/reviews/addReviews.jsp";
	}
}
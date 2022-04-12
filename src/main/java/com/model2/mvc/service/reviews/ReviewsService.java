
   
package com.model2.mvc.service.reviews;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Reviews;


public interface ReviewsService {

	public void addReview(Reviews reviews) throws Exception;
	
	public Reviews getReivew(int reviewNo) throws Exception;
	
	public Map<String,Object> getReviewList(Search search,String userId) throws Exception;
	
	public void updateReview(Reviews reviews) throws Exception;
}
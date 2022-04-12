package com.model2.mvc.view.reviews;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;

public class AddReviewsViewAction extends Action {

	public AddReviewsViewAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("¹ÞÀº tranNO :"+request.getParameter("tranNo"));
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseService service=new PurchaseServiceImpl();
		Purchase purchase=service.getPurchase(tranNo);
		System.out.println("purchase gettranNo>>>>>:"+tranNo);
		System.out.println("purchase gettranNo purchase>>>>>:"+purchase);
		request.setAttribute("purchase", purchase);
		
		
		return "forward:/reviews/addReviewsView.jsp";
	}

}
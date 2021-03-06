package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.User;

public class ListSaleAction extends Action {//판매목록 요청(Admin화면)

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< ListSaleAction : execute() 시작 >>>>>");
		
		Search search = new Search();
		
		int currentPage=1;// 리스트 페이지 1쪽
		
		//"page"의 value가 null이 아닐 경우(page를 눌러 들어올 경우) page에 현재 페이지 값 저장
		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		System.out.println("searchCondition : "+request.getParameter("searchCondition"));
		System.out.println("searchKeyword : "+request.getParameter("searchKeyword"));
		
		//SearchVO의 page에 값 셋팅 (처음 들어올 경우 1)
		// pageUnit에 web.xml의 "pageSize" 값 3을 저장하고, SearchVO의 pageUnit에 3 저장
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		System.out.println("search 셋팅완료 : " + search);
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String buyerId = user.getUserId();
		System.out.println("buyerId 는? " + buyerId);
		
		//getSaleList()를 통해 페이지에 보여줄 데이터를 DB에서 가져와 map에 저장
		PurchaseService purchaseService = new PurchaseServiceImpl();
		Map<String, Object> map = purchaseService.getSaleList(search);
		System.out.println("map 셋팅완료 : " + map);

		
//		//menu에 "menu"의 value(manage 혹은 search)를 불러와 저장
//		String menu = request.getParameter("menu");
//		System.out.println("menu는? "+menu);//디버깅
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("resultPage 셋팅완료 : "+resultPage);
		
		//페이지 정보를 listPurchase.jsp에 넘겨주기 위해 Request Object Scope에 값 셋팅
		request.setAttribute("list", map.get("list"));
		request.setAttribute("search", search);
		request.setAttribute("map", map);
		
		System.out.println("<<<<< ListSaleAction : execute() 종료 >>>>>");
		
		return "forward:/purchase/listSale.jsp";
		
	}//end of execute()	
}//end of class
package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.domain.User;


public class AddPurchaseViewAction extends Action{


	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		
		UserService userservice = new UserServiceImpl();
		User user = userservice.getUser(userId);
		
		ProductService productservice = new ProductServiceImpl();
		ProductVO productVO = productservice.getProduct(prodNo);
		
		request.setAttribute("user", user);
		request.setAttribute("productVO", productVO);
		
		System.out.println("구매상품 화면 뷰 조회 완료: "+productVO);
		
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
}

package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;

import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.domain.User;

import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;


public class AddPurchaseAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		String userId = request.getParameter("buyerId");
		
		PurchaseService service = new PurchaseServiceImpl();
		UserService userService = new UserServiceImpl();
		
		User user = new User();
		user = userService.getUser(userId);
		
		PurchaseVO purchaseVO=new PurchaseVO();
		purchaseVO.setProdNo(prodNo);
		purchaseVO.setBuyer(user);
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));
		purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));
		purchaseVO.setDivyDate(request.getParameter("receiverDate"));
		
		System.out.println("구매상품 추가 완료: "+purchaseVO);
		
		service.addPurchase(purchaseVO);
		request.setAttribute("purchaseVO", purchaseVO);
		request.setAttribute("user", user);
		
		
		return "forward:/purchase/addPurchase.jsp";
	}
}
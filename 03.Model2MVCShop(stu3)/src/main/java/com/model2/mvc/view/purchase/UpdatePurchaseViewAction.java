package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.*;
import com.model2.mvc.service.domain.Purchase;


public class UpdatePurchaseViewAction extends Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		Purchase purchase= purchaseService.getPurchase(tranNo);
		
		//UserService userservice = new UserServiceImpl();
		//UserVO userVO = userservice.getUser(userId);
		
		request.setAttribute("purchase", purchase);
		//HttpSession session = request.getSession();
		//String userId = (String)session.getAttribute("userId");
				
		//System.out.println("援щℓ�닔�젙 酉곗뿉�꽌 蹂댁씠�뒗 userId: "+userId);
		//request.setAttribute("userVO", userVO);-> �씠 遺�遺꾩쓣 �궡由щ㈃ 濡쒓렇�씤�븳 �젙蹂대줈 援щℓ �븘�씠�뵒媛� 媛깆떊�맂�떎. 二쇱쓽�븷寃�. 
		
		System.out.println("purchaseupdate view 구매상품 업데이트 완료 : "+purchase);
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}

}

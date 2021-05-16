package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;

public class UpdatePurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		int tranNo= Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseService purchaseService=new PurchaseServiceImpl();
		//UserService userService = new UserServiceImpl();
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("divyAddr"));
		purchase.setDivyRequest(request.getParameter("divyRequest"));
		purchase.setDivyDate(request.getParameter("divyDate"));
		System.out.println("\nUPDATE :::: "+purchase+"\n");
		purchaseService.updatePurcahse(purchase);
		
		request.setAttribute("purchase", purchase);

		//UserVO userVO = new UserVO();
		//userVO = userService.getUser(userId);
		
		//System.out.println("援щℓ �뾽�뜲�씠�듃userId 媛�: "+userId);
		
		//PurchaseVO purchaseVO = new PurchaseVO();
		//purchaseVO.setTranNo(tranNo);
		//purchaseVO.setBuyer(userVO);
		//request.setAttribute("userVO",userVO);
		//HttpSession session = request.getSession();
		//String userId = (String)session.getAttribute("userId");
		//-> 二쇱꽍泥섎━�븳 遺�遺꾩쓣 �궡由щ㈃  濡쒓렇�씤�븷�븣留덈떎 援щℓ�옄 �븘�씠�뵒媛� 諛붾�뚭쾶 �맂�떎. �븘�씠�뵒 ���옣�븯吏� 留먭쾬. 
		
		System.out.println("update purchase action구매목록 조회"+purchase+"\n\n\n");
		
		return "forward:/purchase/updatePurchase.jsp";
	}

}

package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class UpdatePurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		int tranNo= Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseService purchaseService=new PurchaseServiceImpl();
		//UserService userService = new UserServiceImpl();
		PurchaseVO purchaseVO = purchaseService.getPurchase(tranNo);
		
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("divyAddr"));
		purchaseVO.setDivyRequest(request.getParameter("divyRequest"));
		purchaseVO.setDivyDate(request.getParameter("divyDate"));
		System.out.println("\nUPDATE :::: "+purchaseVO+"\n");
		purchaseService.updatePurcahse(purchaseVO);
		
		request.setAttribute("purchaseVO", purchaseVO);

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
		
		System.out.println("update purchase action구매목록 조회"+purchaseVO+"\n\n\n");
		
		return "forward:/purchase/updatePurchase.jsp";
	}

}

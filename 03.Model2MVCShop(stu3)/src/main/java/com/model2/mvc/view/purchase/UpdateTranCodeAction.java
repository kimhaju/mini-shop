package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class UpdateTranCodeAction extends Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
			int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		    //String tranCode = request.getParameter(null);
			//request.setAttribute("purchaseVO", purchaseVO);
			//PurchaseVO purchaseVO = new PurchaseVO();	
			
			PurchaseService service=new PurchaseServiceImpl();
			PurchaseVO purchaseVO=service.getPurchase2(prodNo);
						
			service.updateTranCode(purchaseVO);
			System.out.println("trancode action °ª: "+purchaseVO);
			
			return "forward:/listProduct.do?menu=manage";
				
	}
}

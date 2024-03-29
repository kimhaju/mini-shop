package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;


public class GetProductAction extends Action{
	@Override
	public String execute(HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
	    int prodNo = Integer.parseInt(request.getParameter("prodNo"));
	    String search = request.getParameter("search");
	    String manage = request.getParameter("manage");
	   
		ProductService service=new ProductServiceImpl();
		ProductVO productVO = service.getProduct(prodNo);
		request.setAttribute("productVO",productVO);
	
		String menu=request.getParameter("menu");
		request.setAttribute("menu", menu);
		request.setAttribute("search", search);
		request.setAttribute("manage", manage);
		
	
		System.out.println("get 메뉴에 찍히는거 확인-> "+ menu);
	
		return "forward:/product/getProduct.jsp";
	}

}

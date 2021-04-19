package com.model2.mvc.service.product.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDao;
import com.model2.mvc.service.product.vo.ProductVO;

public class ProductServiceImpl implements ProductService {
 private ProductDao productDAO;
 
	public ProductServiceImpl() {
		productDAO =new ProductDao();	
	}
	public void addProduct(ProductVO productVO) throws Exception {
		productDAO.insertProduct(productVO);
	}
	public ProductVO getProduct(int prodNo)  throws Exception {
			return productDAO.findProduct(prodNo);
	}
	public Map<String,Object> getProductList(Search search) throws Exception{
		return productDAO.getProductList(search);
	}
	public void updateProduct(ProductVO productVO) throws Exception{
		productDAO.updateProduct(productVO);
		
	}
}



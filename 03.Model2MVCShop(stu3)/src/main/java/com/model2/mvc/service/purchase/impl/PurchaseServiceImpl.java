package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class PurchaseServiceImpl implements PurchaseService {
	
	private PurchaseDAO purchaseDAO;
	
	
	public PurchaseServiceImpl() {
		purchaseDAO=new PurchaseDAO();
	}

	@Override
	public void addPurchase(PurchaseVO purchaseVO) throws Exception {
		purchaseDAO.insertPurchase(purchaseVO);

	}
	@Override
	public PurchaseVO getPurchase(int tranNo) throws Exception {
		return purchaseDAO.findPurchase(tranNo);
	}
	@Override
	public PurchaseVO getPurchase2(int ProdNo) throws Exception {
		
		return purchaseDAO.findPurchase2(ProdNo);
	}
	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		System.out.println("���Ÿ�� ����Ʈ: ");
		return purchaseDAO.getPurchaseList(search, buyerId);
	}

	@Override
	public Map<String, Object> getSaleList(Search search) throws Exception {
		// TODO Auto-generated method stub
		return purchaseDAO.getPurchaseSaleList(search);
	}

	@Override
	public void updatePurcahse(PurchaseVO purchaseVO) throws Exception {
		// TODO Auto-generated method stub
		purchaseDAO.updatePurchase(purchaseVO);

	}

	@Override
	public void updateTranCode(PurchaseVO purchaseVO) throws Exception {
		// TODO Auto-generated method stub
		purchaseDAO.updateTranCode(purchaseVO);

	}

}

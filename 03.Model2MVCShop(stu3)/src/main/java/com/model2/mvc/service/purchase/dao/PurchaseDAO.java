package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User; 
import com.model2.mvc.service.user.*;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class PurchaseDAO {
	public PurchaseDAO() {
		
	}
	public void insertPurchase(Purchase purchase) throws Exception{
		Connection con = DBUtil.getConnection();
		
		String sql= "INSERT INTO transaction VALUES (seq_transaction_tran_no.nextval, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1,purchase.getProdNo());
		pStmt.setString(2,(purchase.getBuyer()).getUserId());
		
		pStmt.setString(3,purchase.getPaymentOption().trim());
		pStmt.setString(4,purchase.getReceiverName());
		pStmt.setString(5,purchase.getReceiverPhone());
		pStmt.setString(6,purchase.getDivyAddr());
		pStmt.setString(7,purchase.getDivyRequest());
		
		pStmt.setString(8,"1"); 
		
		pStmt.setString(9,purchase.getDivyDate().replace("-", ""));
		
		pStmt.executeUpdate();
		
		System.out.println("dao addpurchase  : "+ sql);
		
		con.close();
	}
	
	public Purchase findPurchase(int tranNo) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM product p, transaction t WHERE p.prod_no = t.prod_no AND t.tran_no = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, tranNo);
		
		ResultSet rs = pStmt.executeQuery();
		
		Purchase purchase= null;
		User user = null;
		while(rs.next()) {
			purchase = new Purchase();
			user = new User();
			user.setUserId(rs.getString("buyer_id"));
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setProdNo(rs.getInt("prod_no"));
			purchase.setBuyer(user);
			purchase.setPaymentOption(rs.getString("payment_option").trim());
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setDivyAddr(rs.getString("demailaddr"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setTranCode(rs.getString("tran_status_code").trim());
			purchase.setOrderDate(rs.getDate("order_data"));
			purchase.setDivyDate(String.valueOf(rs.getDate("dlvy_Date")));	
		}
		
		System.out.println("dao getpurchase :"+purchase);
		
		rs.close();
		pStmt.close();
		con.close();
		
		return purchase;
	}

public Purchase findPurchase2(int prodNo) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM product p, transaction t WHERE p.prod_no = t.prod_no AND t.prod_no = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, prodNo);
		
		ResultSet rs = pStmt.executeQuery();
		Purchase purchase= null;
		User user = null;
		
		while(rs.next()) {
			purchase = new Purchase();
			user = new User();
			user.setUserId(rs.getString("buyer_id"));
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setProdNo(rs.getInt("prod_no"));
			purchase.setBuyer(user);
			purchase.setPaymentOption(rs.getString("payment_option").trim());
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setDivyAddr(rs.getString("demailaddr"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setTranCode(rs.getString("tran_status_code").trim());
			purchase.setOrderDate(rs.getDate("order_data"));
			purchase.setDivyDate(String.valueOf(rs.getDate("dlvy_Date")));	
		}
		
		System.out.println("dao getpurchase2 :"+purchase);
		
		rs.close();
		pStmt.close();
		con.close();
		
		return purchase;
	}
	
	public Map<String,Object> getPurchaseList(Search search, String buyerId) throws Exception{
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM users u, transaction t"
				+ " WHERE u.user_id = t.buyer_id AND u.user_id= '"+buyerId+"' ORDER BY dlvy_date";
		
		
		int totalCount = this.getTotalCount(sql);
		System.out.println("purchaseDao :: totalCount  :: " + totalCount);
		 
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		//pStmt.setString(1, buyerId);
			
		UserService service = new UserServiceImpl();
		User user = service.getUser(buyerId);
		
		System.out.println("purchase dao :: "+ sql);
		System.out.println(": "+buyerId);
		
		System.out.println("purchaseList: "+sql);
			
		List<Purchase> list = new ArrayList<Purchase>();	
			while(rs.next()) {
				Purchase purchase = new Purchase();
				user.setUserId(rs.getString("buyer_id"));
				purchase.setTranNo(rs.getInt("tran_no"));
				purchase.setProdNo(rs.getInt("prod_no"));
				purchase.setBuyer(user);
				purchase.setPaymentOption(rs.getString("payment_option").trim());
				purchase.setReceiverName(rs.getString("receiver_name"));
				purchase.setReceiverPhone(rs.getString("receiver_phone"));
				purchase.setDivyAddr(rs.getString("demailaddr"));
				purchase.setDivyRequest(rs.getString("dlvy_request"));
				purchase.setTranCode(rs.getString("tran_status_code").trim());
				purchase.setOrderDate(rs.getDate("order_data"));
				purchase.setDivyDate(String.valueOf(rs.getDate("dlvy_Date")));
				
				list.add(purchase);
				
				
					
			}
			map.put("totalCount", new Integer(totalCount));
			map.put("list", list);

			rs.close();
			pStmt.close();
			con.close();
			
			System.out.println("purchasedaolist "+list);
			

			return map;	
	}
	public Map<String,Object> getPurchaseSaleList(Search search) throws Exception{
	
        Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM transaction";
		
		int totalCount = this.getTotalCount(sql);
		System.out.println("purchaseDao sale list :: totalCount  :: " + totalCount);
		 
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		//pStmt.setString(1, buyerId);
			
		User user = new User();
		
		System.out.println("purchase dao sale :: "+ sql);
		
			
		List<Purchase> list2 = new ArrayList<Purchase>();	
			while(rs.next()) {
				Purchase purchase = new Purchase();
				user.setUserId(rs.getString("buyer_id"));
				purchase.setTranNo(rs.getInt("tran_no"));
				purchase.setProdNo(rs.getInt("prod_no"));
				purchase.setBuyer(user);
				purchase.setPaymentOption(rs.getString("payment_option").trim());
				purchase.setReceiverName(rs.getString("receiver_name"));
				purchase.setReceiverPhone(rs.getString("receiver_phone"));
				purchase.setDivyAddr(rs.getString("demailaddr"));
				purchase.setDivyRequest(rs.getString("dlvy_request"));
				purchase.setTranCode(rs.getString("tran_status_code").trim());
				purchase.setOrderDate(rs.getDate("order_data"));
				purchase.setDivyDate(String.valueOf(rs.getDate("dlvy_Date")));
				
				list2.add(purchase);
				
				
					
			}
			map.put("totalCount", new Integer(totalCount));
			//System.out.println("total count: "+totalCount);
			map.put("list2", list2);

			rs.close();
			pStmt.close();
			con.close();
			
			System.out.println("purchase sale  "+list2);

		
		return map;
			
	}
	public void updatePurchase(Purchase purchase) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction SET payment_option=?, receiver_name=?, receiver_phone=?, demailaddr=?, dlvy_request=?, dlvy_date=? WHERE tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		//stmt.setString(1, purchaseVO.getBuyer().getUserId());
		stmt.setString(1, purchase.getPaymentOption().trim());
		stmt.setString(2, purchase.getReceiverName());
		stmt.setString(3, purchase.getReceiverPhone());
		stmt.setString(4, purchase.getDivyAddr());
		stmt.setString(5, purchase.getDivyRequest());
		stmt.setString(6, purchase.getDivyDate());
		stmt.setInt(7, purchase.getTranNo());
		stmt.executeUpdate();
		
		System.out.println("dao :"+purchase);
		con.close();
	}
	
	
	public void updateTranCode(Purchase purchase) throws Exception{

		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction SET ";
		
		if(purchase.getTranCode().equals("1")) {
			sql += " tran_status_code = 2";
		}else if(purchase.getTranCode().equals("2")) {
			sql += " tran_status_code = 3";
		}
		sql += " WHERE tran_no = ?";
		
	    System.out.println("trancode update   :"+sql);
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1,purchase.getTranNo());
		stmt.executeUpdate();
		
		System.out.println("tran code: "+purchase);
		
		con.close();
		
	}
	
private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("productDao :: make SQL :: "+ sql);	
		
		return sql;
	}
}



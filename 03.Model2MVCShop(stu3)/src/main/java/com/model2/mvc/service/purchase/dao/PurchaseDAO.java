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
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.domain.User; 
import com.model2.mvc.service.user.*;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class PurchaseDAO {
	public PurchaseDAO() {
		
	}
	public void insertPurchase(PurchaseVO purchaseVO) throws Exception{
		Connection con = DBUtil.getConnection();
		
		String sql= "INSERT INTO transaction VALUES (seq_transaction_tran_no.nextval, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1,purchaseVO.getProdNo());
		pStmt.setString(2,(purchaseVO.getBuyer()).getUserId());
		
		pStmt.setString(3,purchaseVO.getPaymentOption().trim());
		pStmt.setString(4,purchaseVO.getReceiverName());
		pStmt.setString(5,purchaseVO.getReceiverPhone());
		pStmt.setString(6,purchaseVO.getDivyAddr());
		pStmt.setString(7,purchaseVO.getDivyRequest());
		
		pStmt.setString(8,"1"); 
		
		pStmt.setString(9,purchaseVO.getDivyDate().replace("-", ""));
		
		pStmt.executeUpdate();
		
		System.out.println("dao addpurchase 구매상품 추가 완료: "+pStmt);
		
		con.close();
	}
	
	public PurchaseVO findPurchase(int tranNo) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM product p, transaction t WHERE p.prod_no = t.prod_no AND t.tran_no = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, tranNo);
		
		ResultSet rs = pStmt.executeQuery();
		
		PurchaseVO purchaseVO= null;
		User user = null;
		while(rs.next()) {
			purchaseVO = new PurchaseVO();
			user = new User();
			user.setUserId(rs.getString("buyer_id"));
			purchaseVO.setTranNo(rs.getInt("tran_no"));
			purchaseVO.setProdNo(rs.getInt("prod_no"));
			purchaseVO.setBuyer(user);
			purchaseVO.setPaymentOption(rs.getString("payment_option").trim());
			purchaseVO.setReceiverName(rs.getString("receiver_name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
			purchaseVO.setDivyAddr(rs.getString("demailaddr"));
			purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
			purchaseVO.setTranCode(rs.getString("tran_status_code").trim());
			purchaseVO.setOrderDate(rs.getDate("order_data"));
			purchaseVO.setDivyDate(String.valueOf(rs.getDate("dlvy_Date")));	
		}
		
		System.out.println("dao getpurchase 구매상품 조회:"+purchaseVO);
		
		rs.close();
		pStmt.close();
		con.close();
		
		return purchaseVO;
	}

public PurchaseVO findPurchase2(int prodNo) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM product p, transaction t WHERE p.prod_no = t.prod_no AND t.prod_no = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, prodNo);
		
		ResultSet rs = pStmt.executeQuery();
		PurchaseVO purchaseVO= null;
		User user = null;
		
		while(rs.next()) {
			purchaseVO = new PurchaseVO();
			user = new User();
			user.setUserId(rs.getString("buyer_id"));
			purchaseVO.setTranNo(rs.getInt("tran_no"));
			purchaseVO.setProdNo(rs.getInt("prod_no"));
			purchaseVO.setBuyer(user);
			purchaseVO.setPaymentOption(rs.getString("payment_option").trim());
			purchaseVO.setReceiverName(rs.getString("receiver_name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
			purchaseVO.setDivyAddr(rs.getString("demailaddr"));
			purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
			purchaseVO.setTranCode(rs.getString("tran_status_code").trim());
			purchaseVO.setOrderDate(rs.getDate("order_data"));
			purchaseVO.setDivyDate(String.valueOf(rs.getDate("dlvy_Date")));	
		}
		
		System.out.println("dao getpurchase2 구매상품 조회 2 :"+purchaseVO);
		
		rs.close();
		pStmt.close();
		con.close();
		
		return purchaseVO;
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
		System.out.println("로그인 유저 아이디 값: "+buyerId);
			
		List<PurchaseVO> list = new ArrayList<PurchaseVO>();	
			while(rs.next()) {
				PurchaseVO purchaseVO = new PurchaseVO();
				user.setUserId(rs.getString("buyer_id"));
				purchaseVO.setTranNo(rs.getInt("tran_no"));
				purchaseVO.setProdNo(rs.getInt("prod_no"));
				purchaseVO.setBuyer(user);
				purchaseVO.setPaymentOption(rs.getString("payment_option").trim());
				purchaseVO.setReceiverName(rs.getString("receiver_name"));
				purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
				purchaseVO.setDivyAddr(rs.getString("demailaddr"));
				purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
				purchaseVO.setTranCode(rs.getString("tran_status_code").trim());
				purchaseVO.setOrderDate(rs.getDate("order_data"));
				purchaseVO.setDivyDate(String.valueOf(rs.getDate("dlvy_Date")));
				
				list.add(purchaseVO);
				
				
					
			}
			map.put("totalCount", new Integer(totalCount));
			map.put("list", list);

			rs.close();
			pStmt.close();
			con.close();
			
			System.out.println("purchasedaolist 구매상품 조회 확인 "+list);

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
		
		System.out.println("purchase dao :: "+ sql);
		
			
		List<PurchaseVO> list2 = new ArrayList<PurchaseVO>();	
			while(rs.next()) {
				PurchaseVO purchaseVO = new PurchaseVO();
				user.setUserId(rs.getString("buyer_id"));
				purchaseVO.setTranNo(rs.getInt("tran_no"));
				purchaseVO.setProdNo(rs.getInt("prod_no"));
				purchaseVO.setBuyer(user);
				purchaseVO.setPaymentOption(rs.getString("payment_option").trim());
				purchaseVO.setReceiverName(rs.getString("receiver_name"));
				purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
				purchaseVO.setDivyAddr(rs.getString("demailaddr"));
				purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
				purchaseVO.setTranCode(rs.getString("tran_status_code").trim());
				purchaseVO.setOrderDate(rs.getDate("order_data"));
				purchaseVO.setDivyDate(String.valueOf(rs.getDate("dlvy_Date")));
				
				list2.add(purchaseVO);
				
				
					
			}
			map.put("totalCount", new Integer(totalCount));
			//System.out.println("total count: "+totalCount);
			map.put("list2", list2);

			rs.close();
			pStmt.close();
			con.close();
			
			System.out.println("purchase sale 판매상품 조회 확인 "+list2);

		
		return map;
			
	}
	public void updatePurchase(PurchaseVO purchaseVO) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction SET payment_option=?, receiver_name=?, receiver_phone=?, demailaddr=?, dlvy_request=?, dlvy_date=? WHERE tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		//stmt.setString(1, purchaseVO.getBuyer().getUserId());
		stmt.setString(1, purchaseVO.getPaymentOption().trim());
		stmt.setString(2, purchaseVO.getReceiverName());
		stmt.setString(3, purchaseVO.getReceiverPhone());
		stmt.setString(4, purchaseVO.getDivyAddr());
		stmt.setString(5, purchaseVO.getDivyRequest());
		stmt.setString(6, purchaseVO.getDivyDate());
		stmt.setInt(7, purchaseVO.getTranNo());
		stmt.executeUpdate();
		
		System.out.println("dao �뾽�뜲�씠�듃 �솗�씤:"+purchaseVO);
		con.close();
	}
	
	
	public void updateTranCode(PurchaseVO purchaseVO) throws Exception{

		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction SET ";
		
		if(purchaseVO.getTranCode().equals("1")) {
			sql += " tran_status_code = 2";
		}else if(purchaseVO.getTranCode().equals("2")) {
			sql += " tran_status_code = 3";
		}
		sql += " WHERE tran_no = ?";
		
	    System.out.println("trancode update 판매상태 변경  :"+sql);
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1,purchaseVO.getTranNo());
		stmt.executeUpdate();
		
		System.out.println("tran code 판매상태 변경 : "+purchaseVO);
		
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



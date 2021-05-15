package com.model2.mvc.service.product.dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
 
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
 
public class ProductDao {
	
	public ProductDao(){
	}
	public void insertProduct(Product product) throws Exception {
		
		Connection con = DBUtil.getConnection();
	
		String sql = " INSERT INTO product VALUES (seq_product_prod_no.nextval,?,?,?,?,?,SYSDATE )";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1,product.getProdName());
		pStmt.setString(2,product.getProdDetail());
		pStmt.setString(3,product.getManuDate().replace("-", ""));
		pStmt.setInt(4,product.getPrice());
		pStmt.setString(5,product.getFileName());
		pStmt.executeUpdate();
		
		pStmt.close();
		con.close();
		
		System.out.println("상품 추가 쿼리문: "+sql);
		System.out.println(pStmt+"값 들어가는 거 확인.");
		
	}
	
public Product findProduct(int prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();
 
		String sql = "SELECT "+
				"prod_no , prod_name  ,  prod_detail , manufacture_day , price ,image_file , reg_date " + 
				"FROM product WHERE prod_no = ?";
 
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, prodNo);
 
		ResultSet rs = pStmt.executeQuery();
 
		Product product = null;
		
		while (rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(rs.getInt("price"));
			product.setFileName(rs.getString("image_file"));
			product.setRegDate(rs.getDate("reg_date"));
		}
		rs.close();
		pStmt.close();
		con.close();
 
		return product;
	}
 
	public Map<String,Object> getProductList(Search search) throws Exception {
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
        Connection con = DBUtil.getConnection();
		
		String sql = "SELECT nvl(tran_status_code, 0) AS tra, p.prod_no, p.prod_name, p.reg_date, p.price "
				+ " FROM product p FULL OUTER JOIN transaction t "
				+ " ON p.prod_no = t.prod_no ";
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE prod_no='" + search.getSearchKeyword()
						+ "'";
			} else if (search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE prod_name='" + search.getSearchKeyword()
						+ "'";
			} else if (search.getSearchCondition().equals("2") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE price='" +search.getSearchKeyword()
				        + "'";
				
			}
		}
		
		sql += "ORDER BY prod_no";
		System.out.println("productDao: "+sql);
		
		int totalCount = this.getTotalCount(sql);
		System.out.println("productDao :: totalCount  :: " + totalCount);
		
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);
 
		List<Product> list = new ArrayList<Product>();
		
		while(rs.next()){
			Product product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setPrice(rs.getInt("price"));
			product.setRegDate(rs.getDate("reg_date"));
			product.setProTranCode(rs.getString("tra").trim());
			list.add(product);
			
			System.out.println("list product: "+product);
		}
		map.put("totalCount", new Integer(totalCount));
		map.put("list", list);
 
		rs.close();
		pStmt.close();
		con.close();
 
		return map;	
	}
	public void updateProduct(Product product) throws Exception {
		
		Connection con = DBUtil.getConnection();
 
		String sql = "UPDATE product "
				+ "SET prod_name=?, prod_detail=?, manufacture_day=?, price=?, image_file=? "
				+ "where prod_no=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		
		pStmt.setString(1,product.getProdName());
		pStmt.setString(2,product.getProdDetail());
		pStmt.setString(3,product.getManuDate().replace("-", ""));
		pStmt.setInt(4,product.getPrice());
		pStmt.setString(5,product.getFileName());
		pStmt.setInt(6,product.getProdNo());
		pStmt.executeUpdate();
		
		System.out.println("상품 정보 업데이트: "+product);
		
		pStmt.close();
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
 
 
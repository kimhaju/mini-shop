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
import com.model2.mvc.service.product.vo.ProductVO;
 
public class ProductDao {
	
	public ProductDao(){
	}
	public void insertProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
	
		String sql = " INSERT INTO product VALUES (seq_product_prod_no.nextval,?,?,?,?,?,SYSDATE )";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1,productVO.getProdName());
		pStmt.setString(2,productVO.getProdDetail());
		pStmt.setString(3,productVO.getManuDate().replace("-", ""));
		
		
		pStmt.setInt(4,productVO.getPrice());
		pStmt.setString(5,productVO.getFileName());
		pStmt.executeUpdate();
		
		pStmt.close();
		con.close();
		
		System.out.println(sql);
		System.out.println(pStmt+"값 들어가는 거 확인.");
		
	}
	
public ProductVO findProduct(int prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();
 
		String sql = "SELECT "+
				"prod_no , prod_name  ,  prod_detail , manufacture_day , price ,image_file , reg_date " + 
				"FROM product WHERE prod_no = ?";
 
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, prodNo);
 
		ResultSet rs = pStmt.executeQuery();
 
		ProductVO productVO = null;
		
		while (rs.next()) {
			productVO = new ProductVO();
			productVO.setProdNo(rs.getInt("prod_no"));
			productVO.setProdName(rs.getString("prod_name"));
			productVO.setProdDetail(rs.getString("prod_detail"));
			productVO.setManuDate(rs.getString("manufacture_day"));
			productVO.setPrice(rs.getInt("price"));
			productVO.setFileName(rs.getString("image_file"));
			productVO.setRegDate(rs.getDate("reg_date"));
		}
		rs.close();
		pStmt.close();
		con.close();
 
		return productVO;
	}
 
	public Map<String,Object> getProductList(Search search) throws Exception {
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT prod_no, prod_name, price, manufacture_day, reg_date FROM product ";
		
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0") && !search.getSearchKeyword().equals("")) {
				sql += " where PROD_NO='" + search.getSearchKeyword()
						+ "'";
			} else if (search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
				sql += " where PROD_NAME='" + search.getSearchKeyword()
						+ "'";
			} else if (search.getSearchCondition().equals("2") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE price='" + search.getSearchKeyword()
				+ "'";
			} else {
				System.out.println("search 실패. 없는 결과값");
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
 
		List<ProductVO> list = new ArrayList<ProductVO>();
		
		while(rs.next()){
			ProductVO productVO = new ProductVO();
			productVO.setProdNo(rs.getInt("prod_no"));
			productVO.setProdName(rs.getString("prod_name"));
			productVO.setPrice(rs.getInt("price"));
			productVO.setManuDate(rs.getString("manufacture_day"));
			productVO.setRegDate(rs.getDate("reg_date"));
			list.add(productVO);
		}
		map.put("totalCount", new Integer(totalCount));
		map.put("list", list);
 
		rs.close();
		pStmt.close();
		con.close();
 
		return map;	
	}
	public void updateProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
 
		String sql = "UPDATE product "
				+ "SET prod_name=?, prod_detail=?, manufacture_day=?, price=?, image_file=? "
				+ "where prod_no=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		
		pStmt.setString(1,productVO.getProdName());
		pStmt.setString(2,productVO.getProdDetail());
		pStmt.setString(3,productVO.getManuDate().replace("-", ""));
		pStmt.setInt(4,productVO.getPrice());
		pStmt.setString(5,productVO.getFileName());
		pStmt.setInt(6,productVO.getProdNo());
		pStmt.executeUpdate();
		
		System.out.println("상품 정보 업데이트: "+productVO);
		
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
 
 
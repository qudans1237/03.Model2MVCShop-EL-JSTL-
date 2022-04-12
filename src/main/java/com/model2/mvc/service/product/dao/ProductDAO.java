package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.User;

public class ProductDAO {
	//Constructor
	public ProductDAO() {
	}
	
	//Method
	//��ǰ����� ���� DBMS�� ����
	public void insertProduct(Product product) throws SQLException {
		System.out.println("<<<<< ProductDAO : insertProduct() ���� >>>>>");
		System.out.println("���� product : " + product);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO product VALUES (seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, product.getProdName());
		pStmt.setString(2, product.getProdDetail());
		pStmt.setString(3, product.getManuDate());
		pStmt.setInt(4, product.getPrice());
		pStmt.setString(5, product.getFileName());
		pStmt.executeUpdate();
		System.out.println("insert �Ϸ� : " + sql);
		
		pStmt.close();
		con.close();	
		System.out.println("<<<<< ProductDAO : insertProduct() ���� >>>>>");
	}
	
	
	//��ǰ���� ��ȸ�� ���� DBMS�� ����
	public Product findProduct(int prodNo) throws Exception {
		System.out.println("<<<<< ProductDAO : findProduct() ���� >>>>>");
		System.out.println("���� prodNo : " + prodNo);
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM product WHERE prod_no=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, prodNo);
		ResultSet rs = pStmt.executeQuery();
		System.out.println("sql ���ۿϷ� : " + sql);

		Product product = new Product();
		while (rs.next()) {
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(rs.getInt("price"));
			product.setFileName(rs.getString("image_file"));
			product.setRegDate(rs.getDate("reg_date"));
		}
		System.out.println("productVO ���ÿϷ� : " + product);
		
		rs.close();
		pStmt.close();
		con.close();
		System.out.println("<<<<< ProductDAO : findProduct() ���� >>>>>");
		return product;
	}
	
	
	//��ǰ��� ��ȸ�� ���� DBMS�� ����
	public Map<String,Object> getProductList(Search search) throws Exception {
		System.out.println("<<<<< ProductDAO : getProductList() ���� >>>>>");
		System.out.println("���� search : " + search);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select v.* from (select rownum rnum, vr.*,TRAN_STATUS_CODE from PRODUCT vr,transaction t where";
		
		//SearchCondition�� ���� ���� ���
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0") && !search.getSearchKeyword().equals("")) {
				sql += " prod_no LIKE '%" + search.getSearchKeyword() +"%' and";
			} else if (search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
				sql += " prod_name LIKE '%" + search.getSearchKeyword() +"%' and";
			} else if (search.getSearchCondition().equals("2") && !search.getSearchKeyword().equals("")) {
				sql += " price LIKE '%" + search.getSearchKeyword() +"%' and";
			}
		}
		sql += " vr.prod_no = t.prod_no(+)";
		sql += " ORDER BY vr.PROD_NO) v";
		
		//getTotalCount() �޼ҵ� ���� (this. ��������)
		int totalCount = this.getTotalCount(sql);
		System.out.println("totalCount : " + totalCount);
		
		//CurrentPage �Խù��� �޵��� Query �ٽñ���
		//makeCurrentPageSql() �޼ҵ� ���� (this. ��������)
		sql = this.makeCurrentPageSql(sql, search);
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		System.out.println("sql ���ۿϷ� : " + sql);

		//HashMap<String,Object> , ArrayList<Product> �ν��Ͻ� ����
		Map<String,Object> map = new HashMap<String,Object>();
		List<Product> list = new ArrayList<Product>();
		
		PurchaseService service = new PurchaseServiceImpl();
		
		while (rs.next()) {
			Product product = new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setRegDate(rs.getDate("REG_DATE"));
			product.setProTranCode(rs.getString("TRAN_STATUS_CODE"));	
			list.add(product);
//			if (!rs.next()) {
//				break;
//			}
			System.out.println("product ���ÿϷ� : " + product);	
		}
		
		//totalCount ���� ����
		map.put("totalCount", new Integer(totalCount));
		System.out.println("map�� totalCount �߰� : " + map);
		
		//currentPage �� �Խù� ���� ���� List ����
		map.put("list", list);
		System.out.println("map�� list �߰� : " + map);
		
		System.out.println("list.size() : " + list.size()); 
		System.out.println("map.size() : " + map.size()); 
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< ProductDAO : getProductList() ���� >>>>>");
		
		return map;
	}
		
//		PreparedStatement pStmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
//														    ResultSet.CONCUR_UPDATABLE);
//		ResultSet rs = pStmt.executeQuery();
//		System.out.println("sql ���ۿϷ� : " + sql);
//		
//		rs.last(); //boolean last() : ������ ������ Ŀ�� �̵�
//		int total = rs.getRow(); //int getRow() : ���� ���ȣ �˻� (������ ���ȣ = ��ü ���� ��)
//		System.out.println("��ü �ο� ��(total) : " + total);
//
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("count", new Integer(total));
//		System.out.println("map�� count �߰� : " + map);
//
//		//boolean absolute(int row) : ������ ���ȣ�� Ŀ�� �̵�
//		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
//		
//		ArrayList<Product> list = new ArrayList<Product>();
//		
//		PurchaseService service = new PurchaseServiceImpl();
//		if (total > 0) {
//			for (int i=0; i<searchVO.getPageUnit(); i++) {
//				Product productVO = new Product();
//				productVO.setProdNo(rs.getInt("prod_no"));
//				productVO.setProdName(rs.getString("prod_name"));
//				productVO.setProdDetail(rs.getString("prod_detail"));
//				productVO.setManuDate(rs.getString("manufacture_day"));
//				productVO.setPrice(rs.getInt("price"));
//				productVO.setFileName(rs.getString("image_file"));
//				productVO.setRegDate(rs.getDate("reg_date"));
//				
//				if(service.getPurchase2(productVO.getProdNo()) != null) {
//					productVO.setProTranCode("������"); 
//				}else {
//					productVO.setProTranCode("�Ǹ���");	
//				}
//				
//				list.add(productVO);
//				if (!rs.next()) {
//					break;
//				}
//				System.out.println("productVO ���ÿϷ� : " + productVO);	
//			}
//		}
//		map.put("list", list);
//		System.out.println("map�� list �߰� : " + map);
//		System.out.println("list.size() : " + list.size()); 
//		System.out.println("map.size() : " + map.size()); 


	
	
	//��ǰ���� ������ ���� DBMS�� ����
	public void updateProduct(Product product) throws Exception {
		System.out.println("<<<<< ProductDAO : updateProduct() ���� >>>>>");
		System.out.println("���� productVO : " + product);
		
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE product "
				    + "SET prod_name=?, prod_detail=?, manufacture_day=?, "
				    + "price=?, image_file=? WHERE prod_no=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, product.getProdName());
		pStmt.setString(2, product.getProdDetail());
		pStmt.setString(3, product.getManuDate());
		pStmt.setInt(4, product.getPrice());
		pStmt.setString(5, product.getFileName());
		pStmt.setInt(6, product.getProdNo());
		pStmt.executeUpdate();
		System.out.println("update �Ϸ� : " + sql);
		
		pStmt.close();
		con.close();
		System.out.println("<<<<< ProductDAO : updateProduct() ���� >>>>>");
	}
	// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
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
	
	// �Խ��� currentPage Row ��  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("UserDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
}//end of class

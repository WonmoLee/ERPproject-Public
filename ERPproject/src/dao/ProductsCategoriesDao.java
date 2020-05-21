package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import db.DBUtils;
import db.DBconnection;
import models.Categories;
import models.Products;
import models.ProductsCategories;

public class ProductsCategoriesDao {

	private static ProductsCategoriesDao instance = new ProductsCategoriesDao();
	private ProductsCategoriesDao() {}
	
	//싱글톤
	public static ProductsCategoriesDao getInstance() {
		return instance;
	}
	


	public List<ProductsCategories> selectAll(String categoryStr) {
		final String SQL = "SELECT P.id, P.category_id, C.name C_name ,P.name,P.price,P.ingredient,P.quantity, C.id C_id " + 
								"FROM PRODUCTS P,CATEGORIES C " + 
								"WHERE C.id = P.category_id AND C.name = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ProductsCategories> allCateProd = new Vector<>();
		
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, categoryStr);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ProductsCategories pc = new ProductsCategories();
				
				pc.setId(rs.getInt("id"));
				pc.setCategory_id(rs.getInt("category_id"));
				pc.setName(rs.getString("name"));
				pc.setPrice(rs.getInt("price"));
				pc.setIngredient(rs.getString("ingredient"));
				pc.setQuantity(rs.getString("quantity"));
				
				Categories c = new Categories();
				c.setId(rs.getInt("C_id"));
				c.setName(rs.getString("C_name"));
				
				
				pc.setCategories(c);
				
				allCateProd.add(pc);
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt, rs);
		}
		
		return allCateProd;
		
	}	
	
	
	
	public List<ProductsCategories> search(String keyword) {
		final String SQL = "SELECT P.id, P.category_id, C.name C_name ,P.name,P.price,P.ingredient,P.quantity, C.id C_id " + 
								"FROM PRODUCTS P,CATEGORIES C " + 
								"WHERE C.id = P.category_id AND ( P.id like ? OR P.category_id like ? OR C.name like ? " +
								" OR P.name like ? OR P.price like ? OR P.ingredient like ? OR P.quantity like ? " +
								" OR C.id like ? )";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ProductsCategories> resultCateProd = new Vector<>();
		
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			pstmt.setString(3, "%" + keyword + "%");
			pstmt.setString(4, "%" + keyword + "%");
			pstmt.setString(5, "%" + keyword + "%");
			pstmt.setString(6, "%" + keyword + "%");
			pstmt.setString(7, "%" + keyword + "%");
			pstmt.setString(8, "%" + keyword + "%");
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ProductsCategories pc = new ProductsCategories();
				
				pc.setId(rs.getInt("id"));
				pc.setCategory_id(rs.getInt("category_id"));
				pc.setName(rs.getString("name"));
				pc.setPrice(rs.getInt("price"));
				pc.setIngredient(rs.getString("ingredient"));
				pc.setQuantity(rs.getString("quantity"));
				
				Categories c = new Categories();
				c.setId(rs.getInt("C_id"));
				c.setName(rs.getString("C_name"));
				
				
				pc.setCategories(c);
				
				resultCateProd.add(pc);
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt, rs);
		}
		
		return resultCateProd;
		
	}	
	
}


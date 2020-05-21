package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import db.DBUtils;
import db.DBconnection;
import models.ProductStocks;

public class ProductStocksDao {

	private static ProductStocksDao instance = new ProductStocksDao();
	private ProductStocksDao() {}
	
	//싱글톤
	public static ProductStocksDao getInstance() {
		return instance;
	}
	
	public Vector<ProductStocks> selectAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			final String SQL = "SELECT * FROM PRODUCT_STOCKS";
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
		Vector<ProductStocks> productStocks = new Vector<>();	
		
		while(rs.next()) {
			ProductStocks productStock = null;
			productStock = new ProductStocks(
					rs.getInt("id"),
					rs.getString("name"),
					rs.getInt("input_stock"),
					rs.getInt("output_stock"),
					rs.getInt("current_stock")
					);
			productStocks.add(productStock);
			
		}
		return productStocks;
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt);
		}
		return null;
	}	
}

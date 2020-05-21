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
import models.Inventories;
import models.Products;
import models.ProductsCategories;

public class InventoriesDao {
	private static InventoriesDao instance = new InventoriesDao();
	private InventoriesDao() {}
	
	//싱글톤
	public static InventoriesDao getInstance() {
		return instance;
	}
	
	
	public List<Inventories> selectAll() {
		final String SQL = "SELECT * FROM INVENTORIES";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Inventories> allInventories = new Vector<>();
		
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Inventories inven = new Inventories();
				
				inven.setId(rs.getInt("id"));
				inven.setName(rs.getString("name"));
				inven.setStock(rs.getDouble("stock"));
				inven.setUnit(rs.getString("unit"));

				allInventories.add(inven);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt, rs);
		}
		
		return allInventories;
		
	}
	
	public List<Inventories> search(String keyword) {
		final String SQL = "SELECT NAME FROM INVENTORIES WHERE NAME LIKE ?"; 
								
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Inventories> resultCateProd = new Vector<>();
		
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%" + keyword + "%");
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Inventories pc = new Inventories();
				pc.setName(rs.getString("name"));
				resultCateProd.add(pc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt, rs);
		}
		
		return resultCateProd;
		
	}	
	
	// Insert
	public int insert(Inventories inventories) {
		final String SQL = "INSERT INTO INVENTORIES(ID,NAME,STOCK,UNIT) " + "VALUES(INVENTORIES_SEQ.nextval,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, inventories.getName());
			pstmt.setDouble(2, inventories.getStock());
			pstmt.setString(3, inventories.getUnit());

			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt);
		}
		return -1;
	}

	// Update
	public int update(Inventories inventories) {
		final String SQL = "UPDATE INVENTORIES SET NAME=?, STOCK=? , UNIT=? WHERE INVENTORIES.ID=?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, inventories.getName());
			pstmt.setDouble(2, inventories.getStock());
			pstmt.setString(3, inventories.getUnit());
			pstmt.setInt(4, inventories.getId());

			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt);
		}
		return -1;
	}
	// Delete
	public int delete(Inventories inventories) {
		final String SQL = "DELETE FROM INVENTORIES I WHERE I.ID = ? AND I.NAME = ? AND I.STOCK = ? AND I.UNIT = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, inventories.getId());
			pstmt.setString(2, inventories.getName());
			pstmt.setDouble(3, inventories.getStock());
			pstmt.setString(4, inventories.getUnit());

			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt);
		}
		return -1;
	}
	
	//Search
	public int productsInventorySearch(Inventories inventories) {
		final String SQL = "SELECT P.INGREDIENT "
				+ "FROM PRODUCTS P "
				+ "WHERE P.INGREDIENT LIKE ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%" + inventories.getName() + ",%");
			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt);
		}
		return -1;
	}
	
}

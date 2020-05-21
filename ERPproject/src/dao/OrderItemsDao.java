package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import db.DBUtils;
import db.DBconnection;
import models.OrderItems;
import models.Orders;
import models.Products;


public class OrderItemsDao {
	private static OrderItemsDao instance = new OrderItemsDao();
	private OrderItemsDao() {}
	
	//싱글톤
	public static OrderItemsDao getInstance() {
		return instance;
	}
	
	//주문하기 버튼 눌렀을때 오더아이템즈 테이블 데이터삽입 - 오더시퀀스값 , 장바구니, 프로덕트테이블의 정보들
	public void insertOrderItems(int currentSeq, Map<String,Integer> cart, Map<String,Products> productsMap) {
		final String SQL = "INSERT INTO ORDER_ITEMS (ORDERS_ID, ITEM_ID, PRODUCTS_ID, QUANTITY, UNIT_PRICE) VALUES (?,?,?,?,?)";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			int orderId = currentSeq;
			
			// for debug only
			System.out.println("cart: " + cart.keySet());
			System.out.println("productsMap: " + productsMap.keySet());
			
			int i = 1;
			for (Iterator<String> iter = cart.keySet().iterator(); iter.hasNext(); i++) {
				String prodName = iter.next();
				Products p = productsMap.get(prodName);
				int prodId = p.getId();
				int price = p.getPrice();
				int quantity = cart.get(prodName);
				
				
				pstmt.setInt(1, orderId);
				pstmt.setInt(2, i);
				pstmt.setInt(3, prodId);
				pstmt.setInt(4, quantity);
				pstmt.setInt(5, price);
				
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt);
		}
	}
	
	public void update(int quantity, int unit_price, int order_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			final String SQL = "UPDATE ORDER_ITEMS SET QUANTITY = ? , UNIT_PRICE = ? WHERE ORDER_ID = ?";
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, quantity);
			pstmt.setInt(2, unit_price);
			pstmt.setInt(3, order_id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt);
		}
	}
	
	//DELETE
	
	public void delete(int order_id, int item_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			final String SQL = "DELETE FROM ORDER_ITEMS WHERE ORDER_ITEMS = ? AND ITEM_ID = ?";
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, order_id);
			pstmt.setInt(2, item_id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt);
		}
	}
	
	//SELECT
	public Vector<OrderItems> selectAll() {
		final String SQL = "SELECT * FROM ORDER_ITEMS";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			Vector<OrderItems> orderItems = new Vector<>();
			
			while(rs.next()) {
				OrderItems orderItem = null;
				orderItem = new OrderItems(
						rs.getInt("orders_id"), 
						rs.getInt("item_id"), 
						rs.getInt("products_id"), 
						rs.getInt("quantity"), 
						rs.getInt("unit_price")
						);
				orderItems.add(orderItem);
			}
			return orderItems;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public Vector<OrderItems> selectOrderId(int orderId) {
		final String SQL = "SELECT * FROM ORDER_ITEMS WHERE ORDERS_ID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, orderId);
			rs = pstmt.executeQuery();
			
			Vector<OrderItems> orderItems = new Vector<>();
			
			while(rs.next()) {
				OrderItems orderItem = null;
				orderItem = new OrderItems(
						rs.getInt("orders_id"), 
						rs.getInt("item_id"), 
						rs.getInt("products_id"), 
						rs.getInt("quantity"), 
						rs.getInt("unit_price")
						);
				orderItems.add(orderItem);
			}
			return orderItems;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public Vector<OrderItems> selectByDate(String date) {
		final String SQL = "SELECT * " + 
				"FROM " + 
				"(SELECT SUBSTR(ORDER_DATE,0,8) \"DATES\" " + 
				"FROM ORDERS, ORDER_ITEMS " + 
				"WHERE ORDERS.ID = ORDER_ITEMS.ORDERS_ID) " + 
				"WHERE DATES like ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, date.trim());
			rs = pstmt.executeQuery();
			
			Vector<OrderItems> orderItems = new Vector<>();
			while(rs.next()) {
				OrderItems orderItem = null;
				System.out.println(rs.getString(1));
//				orderItem = new OrderItems(
//						rs.getInt("orders_id"), 
//						rs.getInt("item_id"), 
//						rs.getInt("products_id"), 
//						rs.getInt("quantity"), 
//						rs.getInt("unit_price")
//						);
//				orderItems.add(orderItem);
			}
			return orderItems;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt, rs);
		}
		return null;
	}
	
}

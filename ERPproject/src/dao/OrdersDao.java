package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import db.DBUtils;
import db.DBconnection;
import models.Orders;

public class OrdersDao {
	private static OrdersDao instance = new OrdersDao();
	private OrdersDao() {}
	
	//싱글톤
	public static OrdersDao getInstance() {
		return instance;
	}
	
	//주문햇을때 주문번호와 주문한 시간이 들어가는 함수
	public void insertNewOrder() {
		final String SQL = "INSERT INTO ORDERS (ID, ORDER_DATE) VALUES (ORDERS_SEQ.NEXTVAL, SYSDATE)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt);
		}
	}
	
	//현재주문번호를 가져다 쓸때 함수
	public int getSequenceNum() {
		String SQL1 = "SELECT ID FROM (SELECT * FROM ORDERS ORDER BY ID DESC) WHERE ROWNUM = 1";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL1);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt, rs);
		}
		
		return -1;
	}
	
	//Orders테이블의 모든 데이터 반환
	public Vector<Orders> selectAll() {
		final String SQL = "SELECT * FROM ORDERS";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			Vector<Orders> orders = new Vector<>();

			while (rs.next()) {
				Orders getOrder = null;
				getOrder = new Orders(rs.getInt("id"), rs.getString("order_date"));
				orders.add(getOrder);
			}

			return orders;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	public List<Integer> selectSalesByDate() {
		final String SQL = "SELECT EXTRACT(year FROM order_date) YEAR, " + 
			       "EXTRACT(month FROM order_date) MONTH, " +
			       "EXTRACT(day FROM order_date) DAY, " +
			       "COUNT(id) ORDER_COUNT, " +
			       "SUM(QUANTITY*UNIT_PRICE) SUM " +
			       "FROM orders, ORDER_ITEMS " +
			       "WHERE orders.id = ORDER_ITEMS.orders_id " +
			"GROUP BY " +
			       "EXTRACT(YEAR FROM order_date), " +
			       "EXTRACT(MONTH FROM order_date), " +
			       "EXTRACT(DAY FROM order_date) " +
			"ORDER BY YEAR DESC, MONTH";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			ArrayList<Integer> al = new ArrayList<>();

			while (rs.next()) {
				int year = rs.getInt("YEAR");
				int month = rs.getInt("MONTH");
				int day = rs.getInt("DAY");
				int ordercount = rs.getInt("ORDER_COUNT");
				int sum = rs.getInt("SUM");

				al.add(year);
				al.add(month);
				al.add(day);
				al.add(ordercount);
				al.add(sum);
				
			}
			return al;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt, rs);
		}
		
		return null;
	}
		
		
		//UPDATE
		public void update(String order_date, String order_id) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				final String SQL = "UPDATE ORDERS SET ORDER_DATE = ? WHERE ORDER_ID = ?";
				
				conn = DBconnection.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, order_date);
				pstmt.setString(2, order_id);
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBUtils.close(conn, pstmt);
			}
		}
		
		//DELETE
		
		public void delete(int order_id) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				final String SQL = "DELETE FROM ORDERS WHERE ID = ?";
				conn = DBconnection.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, order_id);
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBUtils.close(conn, pstmt);
			}
		}
		
//		public Vector<Orders> selectOrderDate(String orderDate) {
//			final String SQL = "SELECT * FROM ORDERS WHERE ORDER_DATE = ?";
//			Connection conn = null;
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//			
//		
//			try {
//				conn = DBconnection.getConnection();
//				pstmt = conn.prepareStatement(SQL);
//				pstmt.setString(1, orderDate);
//				rs = pstmt.executeQuery();
//				
//				Vector<Orders> orders = new Vector<>();
//				
//				while(rs.next()) {
//					Orders order = null;
//					order = new Orders(
//							rs.getInt("id"),
//							rs.getString("order_date") 
//							);
//					orders.add(order);
//				}
//				return orders;
//				
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} finally {
//				DBUtils.close(conn, pstmt, rs);
//			}
//			return null;
//		}
}

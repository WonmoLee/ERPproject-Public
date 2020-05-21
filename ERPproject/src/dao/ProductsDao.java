package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import db.DBUtils;
import db.DBconnection;
import models.Categories;
import models.Orders;
import models.Products;
import models.ProductsCategories;

public class ProductsDao {
	private static ProductsDao instance = new ProductsDao();

	private ProductsDao() {
	}

	// 싱글톤
	public static ProductsDao getInstance() {
		return instance;
	}

	// Products테이블의 모든 정보를 반환
	public List<Products> selectAll() {
		final String SQL = "SELECT * FROM PRODUCTS";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<Products> vector = new Vector<Products>();

		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Products p = new Products();

				p.setId(rs.getInt("id"));
				p.setCategory_id(rs.getInt("category_id"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getInt("price"));
				p.setIngredient(rs.getString("ingredient"));
				p.setQuantity(rs.getString("quantity"));

				vector.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt, rs);
		}

		return vector;

	}

	public static DefaultTableModel selectCategory(DefaultTableModel tableModel, String category) {
		final String SQL = "SELECT P.ID, C.NAME C_NAME, P.NAME P_NAME, P.PRICE, P.INGREDIENT, P.QUANTITY FROM PRODUCTS P,CATEGORIES C WHERE C.ID = P.CATEGORY_ID AND C.NAME = '"
				+ category + "'";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Vector<Object> row = new Vector<>();
				Products p = new Products();
				Categories c = new Categories();

				p.setId(rs.getInt("id"));
				c.setName(rs.getString("C_NAME"));
				p.setName(rs.getString("P_NAME"));
				p.setPrice(rs.getInt("PRICE"));
				p.setIngredient(rs.getString("ingredient"));
				p.setQuantity(rs.getString("quantity"));

				row.addElement(p.getId());
				row.addElement(c.getName());
				row.addElement(p.getName());
				row.addElement(p.getPrice());
				row.addElement(p.getIngredient());
				row.addElement(p.getQuantity());

				tableModel.addRow(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt, rs);
		}
		return tableModel;
	}

	public List<ProductsCategories> search(String keyword) {
		final String SQL = "SELECT P.ID, P.CATEGORY_ID, C.NAME C_NAME ,P.NAME,P.PRICE,P.INGREDIENT,P.QUANTITY, C.ID C_ID "
				+ "FROM PRODUCTS P,CATEGORIES C "
				+ "WHERE C.ID = P.CATEGORY_ID AND ( P.ID LIKE ? OR P.CATEGORY_ID LIKE ? OR C.NAME LIKE ? "
				+ " OR P.NAME LIKE ? OR P.PRICE LIKE ? OR P.INGREDIENT LIKE ? OR P.QUANTITY LIKE ? "
				+ " OR C.ID LIKE ? )";

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

	// INSERT Products
	public void insertNewProducts(Products products) {
		final String SQL = "INSERT INTO PRODUCTS(id,category_id,name,price,ingredient,quantity) VALUES(PRODUCTS_SEQ.NEXTVAL,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);

			pstmt.setInt(1, products.getCategory_id());
			pstmt.setString(2, products.getName());
			pstmt.setInt(3, products.getPrice());
			pstmt.setString(4, products.getIngredient());
			pstmt.setString(5, products.getQuantity());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt);
		}
	}

	// selectALL함수를 땡겨와서 상품정보의 모든정보를 해쉬맵에 담아서 반환
	public Map<String, Products> makeMap() {
		Map<String, Products> map = new HashMap<>();
		List<Products> list = selectAll();
		for (Products p : list) {
			map.put(p.getName(), p);
		}

		return map;
	}

	public Map<Integer, Products> joinMap() {
		Map<Integer, Products> map = new HashMap<>();
		List<Products> list = selectAll();
		for (Products p : list) {
			map.put(p.getId(), p);
		}

		return map;
	}

	// 파싱
	public void parseProducts(String before, String after) {
		System.out.println(before + " -> " + after);
		final String SQL = "SELECT * FROM PRODUCTS WHERE INGREDIENT like ?";
		final String SQL2 = "UPDATE PRODUCTS SET INGREDIENT = ? WHERE PRODUCTS.ID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Products> temps = new ArrayList<>();
		List<Products> products = new ArrayList<>();
		List<Products> products2 = new ArrayList<>();
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%" + before + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				temps.add(new Products(rs.getInt("ID"), rs.getInt("CATEGORY_ID"), rs.getString("NAME"),
						rs.getInt("PRICE"), rs.getString("INGREDIENT"), rs.getString("QUANTITY")));
			}
			System.out.println("실행테스트 1");
			pstmt = null;
			System.out.println("temp 의 크기 " + temps.size());
			boolean change = false;

			for (Products p : temps) {
				p.setIngredient(p.getIngredient());
				System.out.println("원료명 출력 : " + p.getIngredient());
				StringBuilder sb = new StringBuilder();
				String[] temp = p.getIngredient().split(", ");
				System.out.println("temps의 크기 : " + temp.length);
				for (int i = 0; i < temp.length; i++) {
					if (temp[i].equals(before)) {
						temp[i] = after;
						change = true;
					}
				}
				if (change) {
					for (int i = 0; i < temp.length; i++) {
						if (i == temp.length - 1) {
							sb.append(temp[i]);
							p.setIngredient(sb.toString());
							products.add(p);
							System.out.println("해당 원료명들" + p.getIngredient());
						} else {
							sb.append(temp[i] + ", ");
						}
					}
				}
				sb = null;
				change = false;
				System.out.println("----------------------");
			}
			System.out.println("실행테스트 2 : " + products.size());
			for (Products p : products) {
				pstmt = conn.prepareStatement(SQL2);
				pstmt.setString(1, p.getIngredient());
				System.out.println(p.getIngredient());
				pstmt.setInt(2, p.getId());
				System.out.println("ID : " + p.getId());
				pstmt.executeUpdate();
				System.out.println("----------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt, rs);
		}
	}
	
	//DELETE 
		public int delete(ProductsCategories productsCategories) {
			final String SQL = "DELETE FROM PRODUCTS WHERE ID = ?";
			Connection conn = null;
			PreparedStatement pstmt = null;

			try {
				conn = DBconnection.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, productsCategories.getId());

				int result = pstmt.executeUpdate();
				return result;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBUtils.close(conn, pstmt);
			}
			return -1;
		}
		
		//update 
		public int update(Products products) {
			final String SQL = "UPDATE PRODUCTS SET CATEGORY_ID = ?, NAME = ?, PRICE = ?, INGREDIENT = ?, QUANTITY = ? WHERE ID = ?";
			Connection conn = null;
			PreparedStatement pstmt = null;

			try {
				conn = DBconnection.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, products.getCategory_id());
				pstmt.setString(2, products.getName());
				pstmt.setInt(3, products.getPrice());
				pstmt.setString(4, products.getIngredient());
				pstmt.setString(5, products.getQuantity());
				pstmt.setInt(6, products.getId());

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

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import db.DBUtils;
import db.DBconnection;
import models.Categories;


public class CategoriesDao {
	private static CategoriesDao instance = new CategoriesDao();
	private CategoriesDao() {}
	
	//싱글톤
	public static CategoriesDao getInstance() {
		return instance;
	}
	
	//CategoriesDao테이블의 모든 정보를 반환
	public List<Categories> selectAll() {
		final String SQL = "SELECT * FROM CATEGORIES";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Categories> allCategories = new Vector<Categories>();
		
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Categories c = new Categories();
				
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));

				allCategories.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt, rs);
		}
		
		return allCategories;
		
	}
	
	//Categories테이블의 id값 
		public int selectOne(String c_name) {
			final String SQL = "SELECT C.id, C.name FROM CATEGORIES C WHERE C.NAME = ?";
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int id = 0;
			try {
				conn = DBconnection.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, c_name);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					Categories c = new Categories();
					c.setId(rs.getInt("id"));
					c.setName(rs.getString("name"));
					id = c.getId();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBUtils.close(conn, pstmt, rs);
			}
			return id;
			
		}
	
	
	//selectALL함수를 땡겨와서 카테고리정보의 모든정보를 해쉬맵에 담아서 반환
	public Map<Integer, String> makeMap() {
		Map<Integer, String> map = new HashMap<>();
		List<Categories> list = selectAll();
		for (Categories c : list) {
			map.put(c.getId(), c.getName());
		}
		
		return map;
	}
}

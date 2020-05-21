package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import db.DBUtils;
import db.DBconnection;
import models.Users;

public class UsersDao {
	private static UsersDao instance = new UsersDao();
	private UsersDao() {}
	
	public static UsersDao getInstance() {
		return instance;
	}
	
	public List<Users> selectAll() {
		List<Users> users = new Vector<Users>();
		
		final String SQL = "SELECT * FROM USERS";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Users u = new Users();
				u.setId(rs.getInt("ID"));
				u.setUser_id(rs.getString("USER_ID"));
				u.setPassword(rs.getString("PASSWORD"));
				u.setJumin(rs.getString("JUMIN"));
				u.setPhone(rs.getString("PHONE"));
				u.setEmail(rs.getString("EMAIL"));
				u.setAgree_wt(rs.getString("AGREE_WT").charAt(0));
				u.setAdmin_at(rs.getString("ADMIN_AT").charAt(0));
				u.setSignup_date(rs.getString("SIGNUP_DATE"));
				
				users.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt, rs);
		}
		
		return users;
	}
	
	// 해당 userId와 password인 사용자 정보가 Users 테이블에 있는지 확인하고
		// 존재하는 경우 User 객체를 반환하고, 해당하는 사용자 정보가 없는 경우는 null 반환
		// 이때 User 객체에는 "ADMIN_AT" 필드에만 값이 세팅되어 반환되고
		// 이 메서드를 호출한 측에서는 이 값을 통해 관리자인지 일반 사용자인지 판단한다.
		public Users login(String userId, String password) {
			final String SQL = "SELECT * FROM USERS WHERE USER_ID = ? AND PASSWORD = ?";
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			Users user = null;
			
			try {
				conn = DBconnection.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userId);
				pstmt.setString(2, password);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					user = new Users();
					// 테이블에 "Y" 또는 "N"이 들어있어서 한 글자이긴 하지만 getString()이므로
					// 한글자인 String 타입으로 반환되고 있음. 그래서 charAt(0)으로 char 타입으로 바꿈
					user.setAdmin_at(rs.getString("ADMIN_AT").charAt(0));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBUtils.close(conn, pstmt, rs);
			}
			
			return user;
		}
}

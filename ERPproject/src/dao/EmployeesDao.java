package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import db.DBUtils;
import db.DBconnection;
import models.Employees;
import models.Users;

public class EmployeesDao {
	private static EmployeesDao instance = new EmployeesDao();
	private EmployeesDao() {}
	
	//싱글톤
	public static EmployeesDao getInstance() {
		return instance;
	}
	
	public List<Employees> selectAll() {
		List<Employees> employees = new Vector<Employees>();
		
		final String SQL = "SELECT * FROM EMPLOYEES";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Employees e = Employees.builder()
						.id(rs.getInt("id"))
						.name(rs.getString("name"))
						.jumin(rs.getString("jumin"))
						.phone(rs.getString("phone"))
						.hire_date(rs.getString("hire_date"))
						.build();
				employees.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt, rs);
		}
		
		return employees;
	}
	
	
	public List<Employees> search(String keyword) {
		final String SQL = "SELECT ID, NAME, JUMIN, PHONE, HIRE_DATE FROM employees WHERE " +
								"ID LIKE ? OR NAME LIKE ? OR JUMIN LIKE ? OR PHONE LIKE ? OR HIRE_DATE LIKE ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Employees> empList = new Vector<>();
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			pstmt.setString(3, "%" + keyword + "%");
			pstmt.setString(4, "%" + keyword + "%");
			pstmt.setString(5, "%" + keyword + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Employees e = Employees.builder()
						.id(rs.getInt("id"))
						.name(rs.getString("name"))
						.jumin(rs.getString("jumin"))
						.phone(rs.getString("phone"))
						.hire_date(rs.getString("hire_date"))
						.build();
				empList.add(e);
				System.out.println("empList.size() : " + empList.size());
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt);
		}
		return empList;
	}
	
	public int updateEntry(Employees e) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			final String SQL = "UPDATE employees SET name = ?, jumin = ?, phone = ?, hire_date = ? where id = ?";

			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, e.getName());
			pstmt.setString(2, e.getJumin());
			pstmt.setString(3, e.getPhone());
			pstmt.setString(4, e.getHire_date());
			pstmt.setInt(5, e.getId());

			int result = pstmt.executeUpdate();
			
			return result;
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt);
		}
		
		return -1;
	}
	public int addEntry(Employees e) {
		final String SQL = "INSERT INTO employees (ID, NAME, JUMIN, PHONE, HIRE_DATE) VALUES (EMPLOYEES_SEQ.NEXTVAL,?,?,?,?)";

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, e.getName());
			pstmt.setString(2, e.getJumin());
			pstmt.setString(3, e.getPhone());
			pstmt.setString(4, e.getHire_date());

			int result = pstmt.executeUpdate();

			return result;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt);
		}
			return -1;
	}
	
	public int deleteEntry(Employees e) {
		final String SQL = "DELETE FROM EMPLOYEES WHERE ID = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, e.getId());
			int result = pstmt.executeUpdate();
			
			return result;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt);
		}
		return -1;
	}
	
	public Employees getEntryById(int id) {
		final String SQL = "SELECT * FROM EMPLOYEES WHERE ID = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Employees e = null;

		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				e = Employees.builder()
						.id(rs.getInt("ID"))
						.name(rs.getString("NAME"))
						.jumin(rs.getString("JUMIN"))
						.phone(rs.getString("PHONE"))
						.hire_date(rs.getString("HIRE_DATE"))
						.build();
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt, rs);
		}

		return e;
	}
	
	public List<Employees> getAll() {
		List<Employees> employees = new Vector<Employees>();

		final String SQL = "SELECT * FROM EMPLOYEES";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBconnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Employees e = Employees.builder()
						.id(rs.getInt("ID"))
						.name(rs.getString("NAME"))
						.jumin(rs.getString("JUMIN"))
						.phone(rs.getString("PHONE"))
						.hire_date(rs.getString("HIRE_DATE"))
						.build();

				employees.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, pstmt, rs);
		}

		return employees;
	}
}

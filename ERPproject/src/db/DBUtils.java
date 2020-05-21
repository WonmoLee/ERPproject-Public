package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

import dao.CategoriesDao;
import dao.EmployeesDao;
import dao.InventoriesDao;
import dao.OrderItemsDao;
import dao.OrdersDao;
import dao.ProductStocksDao;
import dao.ProductsCategoriesDao;
import dao.ProductsDao;
import dao.UsersDao;
import models.Employees;
import models.Inventories;
import models.OrderItems;
import models.Orders;
import models.ProductStocks;
import models.Products;
import models.ProductsCategories;
import models.Users;

public class DBUtils {
	public static void close(Connection conn, PreparedStatement pstmt) {
		try {
			conn.close();
		} catch (Exception e) {
			System.out.println("DB종료시 오류가 발생:" + e.getMessage());
		}
	}
	
	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			conn.close();
		} catch (Exception e) {
			System.out.println("DB종료시 오류가 발생:" + e.getMessage());
		}
	}
	//Jtable 컬럼 이름 가져오기 - DB에서 table 컬럼 수정시 수정해야함!!
	public static Vector<String> getColumnNames(String table) {
		Vector<String> columnNames = new Vector<>();
		if (table.equals("users")) {
			columnNames.add("일련번호");
			columnNames.add("사용자아이디");
			columnNames.add("비밀번호");
			columnNames.add("주민번호");
			columnNames.add("연락처");
			columnNames.add("이메일");
			columnNames.add("개인정보 동의 여부");
			columnNames.add("관리자 권한 유무");
			columnNames.add("가입날짜");
		}else if(table.equals("products")) {
			columnNames.add("상품번호");
			columnNames.add("카테고리");
			columnNames.add("상품명");
			columnNames.add("상품가격");
			columnNames.add("상품원료");
			columnNames.add("상품원료량");
		}else if(table.equals("orders")) {
			columnNames.add("주문번호");
			columnNames.add("주문시간");
		}else if(table.equals("orderitems")) {
			columnNames.add("주문번호");
			columnNames.add("아이템번호");
			columnNames.add("상품명");
			columnNames.add("수량");
			columnNames.add("판매가격");
		}else if(table.equals("inventories")) {
			columnNames.add("원료번호");
			columnNames.add("원료명");
			columnNames.add("원료량");
			columnNames.add("단위");
		}else if(table.equals("categories")) {
			columnNames.add("카테고리 번호");
			columnNames.add("카테고리 이름");
		}else if(table.equals("employees")) {
			columnNames.add("직원 번호");
			columnNames.add("이름");
			columnNames.add("주민등록번호 ");
			columnNames.add("연락처");
			columnNames.add("입사일");
		} else if(table.equals("detailorder")) {
			columnNames.add("상품명");
			columnNames.add("수량");
			columnNames.add("판매가격");
		} else if(table.equals("monthlysales")) {
			columnNames.add("YEAR");
			columnNames.add("MONTH");
			columnNames.add("DAY");
			columnNames.add("총 주문건수");
			columnNames.add("당일매출");
		} else if(table.equals("dailysales")) {
			columnNames.add("주문번호");
			columnNames.add("상품명");
			columnNames.add("수량");
			columnNames.add("판매가격");
		} else if (table.equals("updateProducts")) {
			columnNames.add("상품번호");
			columnNames.add("카테고리");
			columnNames.add("상품명");
			columnNames.add("판매가격");
		} else if (table.equals("updateInventory")) {
			columnNames.add("원료명");
			columnNames.add("원료량");
			columnNames.add("단위");
		} else if (table.equals("productStock")) {
			columnNames.add("상품ID");
			columnNames.add("상품명");
			columnNames.add("입고량");
			columnNames.add("출고량");
			columnNames.add("현재고량");
		}
		return columnNames;
	}
	
	public static DefaultTableModel makeRowUsers(DefaultTableModel tableModel) {
		List<Users> users;
		UsersDao usersDao = UsersDao.getInstance();
		users = usersDao.selectAll();
		for (Users u : users) {
			Vector<Object> row = new Vector<>();
			row.addElement(u.getId());
			row.addElement(u.getUser_id());
			row.addElement(u.getPassword());
			row.addElement(u.getJumin());
			row.addElement(u.getPhone());
			row.addElement(u.getEmail());
			row.addElement(u.getAgree_wt());
			row.addElement(u.getAdmin_at());
			row.addElement(u.getSignup_date());
			tableModel.addRow(row);
		}
		return tableModel;
	}
	
	public static DefaultTableModel makeRowProducts(DefaultTableModel tableModel1){
		List<Products> products;
		ProductsDao productsDao = ProductsDao.getInstance();
		CategoriesDao categoriesDao = CategoriesDao.getInstance();
		products = productsDao.selectAll();
		Map<Integer, String> categoriesMap = categoriesDao.makeMap();
		for (Products p : products) {
			Vector<Object> row = new Vector<>();
			row.addElement(p.getId());
			row.addElement(categoriesMap.get(p.getCategory_id()));
			row.addElement(p.getName());
			row.addElement(p.getPrice());
			row.addElement(p.getIngredient());
			row.addElement(p.getQuantity());
			tableModel1.addRow(row);
		}
		return tableModel1;
	}
	
	public static DefaultTableModel makeRowProductsCategoriesSearch(DefaultTableModel tableModel1, String keyword) {
		ProductsCategoriesDao productsCategoriesDao = ProductsCategoriesDao.getInstance();
		List<ProductsCategories> productsCategories = productsCategoriesDao.search(keyword);

		for (ProductsCategories pc : productsCategories) {
			Vector<Object> row = new Vector<>();
			row.addElement(pc.getId());
			row.addElement(pc.getCategories().getName());
			row.addElement(pc.getName());
			row.addElement(pc.getPrice());
			row.addElement(pc.getIngredient());
			row.addElement(pc.getQuantity());
			tableModel1.addRow(row);
		}
		return tableModel1;
	}
	
	public static DefaultTableModel makeRowOrders(DefaultTableModel tableModel) {
		List<Orders> orders;
		OrdersDao ordersDao = OrdersDao.getInstance();
		orders = ordersDao.selectAll();
		for (Orders o : orders) {
			Vector<Object> row = new Vector<>();
			row.addElement(o.getId());
			row.addElement(o.getOrder_date());
			tableModel.addRow(row);
		}
		return tableModel;
	}
	
	public static DefaultTableModel makeRowOrderItems(DefaultTableModel tableModel) {
		List<OrderItems> orderItems;
		OrderItemsDao orderItemsDao = OrderItemsDao.getInstance();
		ProductsDao productsDao = ProductsDao.getInstance();
		orderItems = orderItemsDao.selectAll();
		Map<Integer, Products> productsMap = productsDao.joinMap();
		for (OrderItems oi : orderItems) {
			Vector<Object> row = new Vector<>();
			row.addElement(oi.getOrders_id());
			row.addElement(oi.getItem_id());
			row.addElement(productsMap.get(oi.getProducts_id()).getName());
			row.addElement(oi.getQuantity());
			row.addElement(oi.getUnit_price());
			tableModel.addRow(row);
		}
		return tableModel;
	}
	
	public static DefaultTableModel makeRowDetailOrder(DefaultTableModel tableModel7, int orderId) {
		OrderItemsDao orderItemsDao = OrderItemsDao.getInstance();
		ProductsDao productsDao = ProductsDao.getInstance();
		List<OrderItems> orderItems = orderItemsDao.selectOrderId(orderId);
		Map<Integer, Products> productsMap = productsDao.joinMap();
		for (OrderItems oi : orderItems) {
			Vector<Object> row = new Vector<>();
			row.addElement(productsMap.get(oi.getProducts_id()).getName());
			row.addElement(oi.getQuantity());
			row.addElement(oi.getUnit_price());
			tableModel7.addRow(row);
		}
		return tableModel7;
	}
	
	//월매출 테이블에서 일매출 보여주는 우측테이블모델
	public static DefaultTableModel makeRowDetailOrder2(DefaultTableModel tableModel7, String date) {
		OrderItemsDao orderItemsDao = OrderItemsDao.getInstance();
		ProductsDao productsDao = ProductsDao.getInstance();
		List<OrderItems> orderItems = orderItemsDao.selectByDate(date);
		Map<Integer, Products> productsMap = productsDao.joinMap();
		for (OrderItems oi : orderItems) {
			Vector<Object> row = new Vector<>();
			row.addElement(oi.getOrders_id());
			row.addElement(productsMap.get(oi.getProducts_id()).getName());
			row.addElement(oi.getQuantity());
			row.addElement(oi.getUnit_price());
			tableModel7.addRow(row);
		}System.out.println(tableModel7.getRowCount());
		return tableModel7;
	}
	
	public static DefaultTableModel makeRowInventories(DefaultTableModel tableModel) {
		List<Inventories> inventories;
		InventoriesDao inventoriesDao = InventoriesDao.getInstance();
		inventories = inventoriesDao.selectAll();
		for (Inventories p : inventories) {
			Vector<Object> row = new Vector<>();
			row.addElement(p.getId());
			row.addElement(p.getName());
			row.addElement(p.getStock());
			row.addElement(p.getUnit());
			tableModel.addRow(row);
		}
		return tableModel;
	}
	
	public static DefaultListModel<String> makeRowInvenName(DefaultListModel<String> dt1model) {
		List<Inventories> inventories;
		InventoriesDao inventoriesDao = InventoriesDao.getInstance();
		inventories = inventoriesDao.selectAll();
		for (Inventories p : inventories) {
			dt1model.addElement(p.getName()+" ("+p.getUnit()+")");
			
		}
		return dt1model;
	}
	
	public static DefaultListModel<String> makeRowInvensearch(DefaultListModel<String> dt1model, String keyword) {
		InventoriesDao inventoriesDao = InventoriesDao.getInstance();
		List<Inventories> inventories = inventoriesDao.search(keyword);
		
		for (Inventories pc : inventories) {
//			Vector<Object> row = new Vector<>();
			dt1model.addElement(pc.getName());
		}
		return dt1model;
	}
	
	public static DefaultTableModel makeRowEmployees(DefaultTableModel tableModel3) {
		List<Employees> employees;
		EmployeesDao employeesDao = EmployeesDao.getInstance();
		employees = employeesDao.selectAll();
		for (Employees e : employees) {
			Vector<Object> row = new Vector<>();
			row.addElement(e.getId());
			row.addElement(e.getName());
			row.addElement(e.getJumin());
			row.addElement(e.getPhone());
			row.addElement(e.getHire_date());
			tableModel3.addRow(row);
		}
		return tableModel3;
	}
	
	public static DefaultTableModel makeRowEmployeesSearch(DefaultTableModel tableModel, String keyword) {
		EmployeesDao employeesDao = EmployeesDao.getInstance();
		List<Employees> employeesList = employeesDao.search(keyword);
		System.out.println("employeesList.size() : " + employeesList.size());
		
		for (Employees emp : employeesList) {
			Vector<Object> row = new Vector<>();
			System.out.println("emp : " + emp);
			row.addElement(emp.getId());
			row.addElement(emp.getName());
			row.addElement(emp.getJumin());
			row.addElement(emp.getPhone());

			row.addElement(emp.getHire_date());
			//row.addElement("2000-12-31");
			tableModel.addRow(row);
		}
		return tableModel;
	}
	
	public static DefaultTableModel selectSalesByDate(DefaultTableModel tableModel) {
		OrdersDao ordersDao = OrdersDao.getInstance();
		List<Integer> orders = ordersDao.selectSalesByDate();
		for (int i = 0; i<orders.size(); i = i + 5) {
			Vector<Object> row = new Vector<>();
			row.addElement(orders.get(i));
			row.addElement(orders.get(i + 1));
			row.addElement(orders.get(i + 2));
			row.addElement(orders.get(i + 3));
			row.addElement(orders.get(i + 4));
			tableModel.addRow(row);
		}
		return tableModel;
	}
	
	public static DefaultTableModel makeRowStocks(DefaultTableModel tableModel) {
		ProductStocksDao productStocksDao = ProductStocksDao.getInstance();
		List<ProductStocks> productStocks = productStocksDao.selectAll();
		for (ProductStocks s : productStocks) {
			Vector<Object> row = new Vector<>();
			row.addElement(s.getId());
			row.addElement(s.getName());
			row.addElement(s.getInput_stock());
			row.addElement(s.getOutput_stock());
			row.addElement(s.getCurrent_stock());
			tableModel.addRow(row);
		}
		return tableModel;
	}
	
	
}

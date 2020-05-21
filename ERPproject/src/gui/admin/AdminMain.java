package gui.admin;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import dao.ProductsDao;
import db.DBUtils;
import gui.admin.employeessub.AddEmployees;
import gui.admin.employeessub.DeleteEmployees;
import gui.admin.employeessub.UpdateEmployees;
import gui.admin.productsub.MaterialCFrame;
import gui.admin.productsub.MaterialDFrame;
import gui.admin.productsub.MaterialUFrame;
import gui.admin.productsub.MenuCFrame;
import gui.admin.productsub.MenuDFrame;
import gui.admin.productsub.MenuUFrame;
import gui.common.Login;
import models.Categories;
import models.Employees;
import models.Inventories;
import models.ProductsCategories;

public class AdminMain extends JFrame {
	private ImageIcon ERPlogo;
	private JLabel logolabel;
	private AdminMain adminMain = this;

	private JPanel mainPanel, cdMainPanel, cardPanel1, cardPanel2, cardPanel3, cardPanel4, cardPanel5;
	private JPanel cardPanel1_left, cardPanel1_right;
	private JPanel cardPanel2_left, cardPanel2_right;
	private JPanel cardPanel3_left, cardPanel3_right;
	private JPanel cardPanel4_left, cardPanel4_right;
	private JPanel cardPanel5_left, cardPanel5_right;

	private JPanel cardPanel1_right_bt1;
	private JPanel cardPanel2_right_bt1;
	private JPanel cardPanel3_right_bt1, cardPanel3_right_bt2, cardPanel3_right_bt3;
	private JPanel cardPanel4_right_bt1;
	private JPanel cardPanel5_right_bt1, cardPanel5_right_bt5;

	// 위쪽 버튼 상점관리~로그아웃
	private JButton 상단메뉴버튼, 상단재고버튼, 상단정산버튼, 상단판매버튼, 상단인사버튼, 상단로그아웃;

	// 메뉴
	private JButton 메뉴전체버튼, 메뉴추가버튼, 메뉴수정버튼, 메뉴삭제버튼;
	private JButton 메뉴검색버튼;
	private JComboBox 메뉴카테고리콤보;
	private JTextField 메뉴검색입력;
	private JLabel 분류라벨, 메뉴검색라벨;

	// 재고
	private JButton 재료전체버튼, 재료추가버튼, 재료수정버튼, 재료삭제버튼;
	private JLabel 원재료라벨;
	// 정산
	private JButton Left3_1, Left3_2, Left3_3, calculateBT;
	private JComboBox yearCB, monthCB;
	private JTextField orderNumberTF, ordersumTF, dailyTotalSalesTF;
	// 정산 - JLabel
	private JLabel 메뉴전체정보라벨, 정산주문번호라벨, 정산총판매금액라벨, 정산_원_라벨, 정산주문상세라벨;
	private JLabel dailySalesLB, dailyTotalSalesLB, monthSalesLB, yearLB, monthLB;
	private JTable orderTable, orderitemTable, detailorderTable, monthSalesTB;
	private JScrollPane orderSP, orderitemSP, detailorderSP, monthSalesSP;
	private DefaultTableModel tableModel5, tableModel6, tableModel7;
	// 판매관리
	private JButton Left4_1, 상품재고새로고침버튼;
	private JLabel 상품재고라벨;
	// 인사관리
	private JButton 인사전체버튼, 인사추가버튼, 인사수정버튼, 인사삭제버튼, 시스템계정버튼;
	private JButton 인사검색버튼;
	private JTextField 인사검색창;
	// JTable
	private JTable 상품테이블, 시스템계정테이블, 인사테이블, 원재료테이블, 주문번호테이블, 주문번호의상세내역테이블, 일매출테이블, 월매출테이블, 판매관리테이블;
	private JScrollPane 상품스크롤페인, 시스템계정스크롤페인, 인사스크롤페인, 원재료스크롤페인, 주문번호스크롤페인, 주문번호의상세내역스크롤페인, 일매출스크롤페인,
			월매출스크롤페인, 판매관리스크롤패인;
	private DefaultTableModel 상품테이블모델, 시스템계정테이블모델, 인사테이블모델, 원재료테이블모델, 주문번호테이블모델, 주문번호의상세내역테이블모델, 일매출테이블모델, 월매출테이블모델,
			해당날짜주문상세내역테이블모델, 판매테이블모델;

	// 5.11 저녁 추가 - 각 Model들 Vector 생성

	// table1, tableModel1 - products
	// jScrollPane1
	//
	// table2, tableModel2 - users
	// jScrollPane2
	//
	// table3, tableModel3- employees
	// jscrollPane3
	//
	// materialTable, tableModel4 - inventories
	// materialSP

	public AdminMain() {
		initTable();
		initPanel();
		initLabel();
		initTextField();
		initButton();
		initComboBox();
		initListener();
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Lotteria ERPsystem");
		setSize(1600, 900);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initTable() {
		// 메뉴관리 - 메뉴 Main
		상품테이블모델 = new DefaultTableModel(DBUtils.getColumnNames("products"), 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		상품테이블모델 = DBUtils.makeRowProducts(상품테이블모델);

		상품테이블 = new JTable(상품테이블모델);
		상품테이블.getColumnModel().getColumn(0).setPreferredWidth(85);
		상품테이블.getColumnModel().getColumn(1).setPreferredWidth(115);
		상품테이블.getColumnModel().getColumn(2).setPreferredWidth(150);
		상품테이블.getColumnModel().getColumn(3).setPreferredWidth(100);
		상품테이블.getColumnModel().getColumn(4).setPreferredWidth(500);
		상품테이블.getColumnModel().getColumn(5).setPreferredWidth(432);
		상품스크롤페인 = new JScrollPane(상품테이블);
		상품스크롤페인.setBounds(12, 204, 1358, 452);

		// 인사관리 - 시스템 계정정보
		시스템계정테이블모델 = new DefaultTableModel(DBUtils.getColumnNames("users"), 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		시스템계정테이블모델 = DBUtils.makeRowUsers(시스템계정테이블모델);

		시스템계정테이블 = new JTable(시스템계정테이블모델);
		시스템계정스크롤페인 = new JScrollPane(시스템계정테이블);
		시스템계정스크롤페인.setBounds(12, 215, 1358, 121);

		// 인사관리 - 직원 전체정보
		인사테이블모델 = new DefaultTableModel(DBUtils.getColumnNames("employees"), 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		인사테이블모델 = DBUtils.makeRowEmployees(인사테이블모델);

		인사테이블 = new JTable(인사테이블모델);
		인사스크롤페인 = new JScrollPane(인사테이블);
		인사스크롤페인.setBounds(12, 211, 1358, 361);
		인사스크롤페인.setViewportView(인사테이블);

		원재료테이블모델 = new DefaultTableModel(DBUtils.getColumnNames("inventories"), 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		원재료테이블모델 = DBUtils.makeRowInventories(원재료테이블모델);

		원재료테이블 = new JTable(원재료테이블모델);
		원재료스크롤페인 = new JScrollPane(원재료테이블);
		원재료스크롤페인.setBounds(28, 102, 1342, 312);
		원재료스크롤페인.setViewportView(원재료테이블);

		주문번호테이블모델 = new DefaultTableModel(DBUtils.getColumnNames("orders"), 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		주문번호테이블모델 = DBUtils.makeRowOrders(주문번호테이블모델);

		주문번호테이블 = new JTable(주문번호테이블모델);
		주문번호스크롤페인 = new JScrollPane(주문번호테이블);
		주문번호스크롤페인.setBounds(162, 136, 435, 502);
		주문번호스크롤페인.setViewportView(주문번호테이블);

		// 주문별 상세내역에서 좌측 행 클릭시 보여지는 주문상세내역
		주문번호의상세내역테이블모델 = new DefaultTableModel(DBUtils.getColumnNames("detailorder"), 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		주문번호의상세내역테이블 = new JTable(주문번호의상세내역테이블모델);
		주문번호의상세내역스크롤페인 = new JScrollPane(주문번호의상세내역테이블);
		주문번호의상세내역스크롤페인.setBounds(729, 136, 512, 384);
		주문번호의상세내역스크롤페인.setViewportView(주문번호의상세내역테이블);

		일매출테이블모델 = new DefaultTableModel(DBUtils.getColumnNames("orderitems"), 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		일매출테이블모델 = DBUtils.makeRowOrderItems(일매출테이블모델);

		일매출테이블 = new JTable(일매출테이블모델);
		일매출스크롤페인 = new JScrollPane(일매출테이블);
		일매출스크롤페인.setBounds(12, 110, 1358, 479);
		일매출스크롤페인.setViewportView(일매출테이블);

		// 월매출 테이블
		월매출테이블모델 = new DefaultTableModel(DBUtils.getColumnNames("monthlysales"), 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		월매출테이블모델 = DBUtils.selectSalesByDate(월매출테이블모델);

		월매출테이블 = new JTable(월매출테이블모델);
		월매출스크롤페인 = new JScrollPane(월매출테이블);
		월매출스크롤페인.setBounds(97, 110, 506, 479);
		월매출스크롤페인.setViewportView(월매출테이블);


		// 판매테이블
		판매테이블모델 = new DefaultTableModel(DBUtils.getColumnNames("productStock"), 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};
		
		판매테이블모델 = DBUtils.makeRowStocks(판매테이블모델);

		판매관리테이블 = new JTable(판매테이블모델);
		판매관리스크롤패인 = new JScrollPane(판매관리테이블);
		판매관리스크롤패인.setBounds(20, 142, 1000, 340);
		판매관리스크롤패인.setViewportView(판매관리테이블);
		

		
		
	}

	private void initPanel() {
		// 최하단패널
		mainPanel = new JPanel();
		mainPanel.setBounds(0, 20, 1584, 841);
		getContentPane().add(mainPanel);
		mainPanel.setLayout(null);

		// 메인패널
		cdMainPanel = new JPanel();
		cdMainPanel.setBounds(0, 73, 1584, 768);
		mainPanel.add(cdMainPanel);
		cdMainPanel.setLayout(new CardLayout(0, 0));

		// 카드패널1
		cardPanel1 = new JPanel();
		cdMainPanel.add(cardPanel1, "메뉴관리");
		cardPanel1.setLayout(null);

		cardPanel1_left = new JPanel();
		cardPanel1_left.setBounds(0, 0, 201, 710);
		cardPanel1.add(cardPanel1_left);
		cardPanel1_left.setLayout(null);

		cardPanel1_right = new JPanel();
		cardPanel1_right.setBorder(new LineBorder(Color.GRAY));
		cardPanel1_right.setBounds(201, 0, 1384, 769);
		cardPanel1.add(cardPanel1_right);
		cardPanel1_right.setLayout(new CardLayout(0, 0));

		cardPanel1_right_bt1 = new JPanel();
		cardPanel1_right.add(cardPanel1_right_bt1, "메뉴 전체정보");
		cardPanel1_right_bt1.setLayout(null);
		cardPanel1_right_bt1.add(상품스크롤페인);

		// 카드패널2
		cardPanel2 = new JPanel();
		cdMainPanel.add(cardPanel2, "재고관리");
		cardPanel2.setLayout(null);

		cardPanel2_left = new JPanel();
		cardPanel2_left.setBounds(0, 0, 201, 769);
		cardPanel2.add(cardPanel2_left);
		cardPanel2_left.setLayout(null);

		cardPanel2_right = new JPanel();
		cardPanel2_right.setBorder(new LineBorder(Color.GRAY));
		cardPanel2_right.setBounds(201, 0, 1384, 769);
		cardPanel2.add(cardPanel2_right);
		cardPanel2_right.setLayout(new CardLayout(0, 0));

		cardPanel2_right_bt1 = new JPanel();
		cardPanel2_right.add(cardPanel2_right_bt1, "원재료 전체정보");
		cardPanel2_right_bt1.setLayout(null);
		cardPanel2_right_bt1.add(원재료스크롤페인);
		
		JLabel lblNewLabel_1 = new JLabel("원재료 전체정보");
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 24));
		lblNewLabel_1.setBounds(28, 27, 192, 49);
		cardPanel2_right_bt1.add(lblNewLabel_1);

		// 카드패널 3
		cardPanel3 = new JPanel();
		cdMainPanel.add(cardPanel3, "정산관리");
		cardPanel3.setLayout(null);

		cardPanel3_left = new JPanel();
		cardPanel3_left.setBounds(0, 0, 201, 710);
		cardPanel3.add(cardPanel3_left);
		cardPanel3_left.setLayout(null);

		cardPanel3_right = new JPanel();
		cardPanel3_right.setBorder(new LineBorder(Color.GRAY));
		cardPanel3_right.setBounds(201, 0, 1384, 769);
		cardPanel3.add(cardPanel3_right);
		cardPanel3_right.setLayout(new CardLayout(0, 0));

		// 카드패널 3에 좌측 항목들에 따른 우측패널
		cardPanel3_right_bt1 = new JPanel();
		cardPanel3_right.add(cardPanel3_right_bt1, "당일주문별 상세내역");
		cardPanel3_right_bt1.setLayout(null);
		cardPanel3_right_bt1.add(주문번호스크롤페인);
		cardPanel3_right_bt1.add(주문번호의상세내역스크롤페인);

		cardPanel3_right_bt2 = new JPanel();
		cardPanel3_right.add(cardPanel3_right_bt2, "당일매출");
		cardPanel3_right_bt2.setLayout(null);
		cardPanel3_right_bt2.add(일매출스크롤페인);

		cardPanel3_right_bt3 = new JPanel();
		cardPanel3_right.add(cardPanel3_right_bt3, "월매출");
		cardPanel3_right_bt3.setLayout(null);
		cardPanel3_right_bt3.add(월매출스크롤페인);

		// 카드패널 4
		cardPanel4 = new JPanel();
		cdMainPanel.add(cardPanel4, "판매관리");
		cardPanel4.setLayout(null);

		cardPanel4_left = new JPanel();
		cardPanel4_left.setBounds(0, 0, 201, 710);
		cardPanel4.add(cardPanel4_left);
		cardPanel4_left.setLayout(null);
		
		cardPanel4_right = new JPanel();
		cardPanel4_right.setBorder(new LineBorder(Color.GRAY));
		cardPanel4_right.setBounds(201, 0, 1384, 769);
		cardPanel4.add(cardPanel4_right);
		cardPanel4_right.setLayout(new CardLayout(0, 0));

		cardPanel4_right_bt1 = new JPanel();
		cardPanel4_right.add(cardPanel4_right_bt1, "상품재고");
		cardPanel4_right_bt1.setLayout(null);
		cardPanel4_right_bt1.add(판매관리스크롤패인);

		

		// 카드패널 5
		cardPanel5 = new JPanel();
		cdMainPanel.add(cardPanel5, "인사관리");
		cardPanel5.setLayout(null);

		cardPanel5_left	= new JPanel();
		cardPanel5_left.setBounds(0, 0, 201, 710);
		cardPanel5.add(cardPanel5_left);
		cardPanel5_left.setLayout(null);

		cardPanel5_right = new JPanel();
		cardPanel5_right.setBorder(new LineBorder(Color.GRAY));
		cardPanel5_right.setBounds(201, 0, 1384, 769);
		cardPanel5.add(cardPanel5_right);
		cardPanel5_right.setLayout(new CardLayout(0, 0));

		cardPanel5_right_bt1 = new JPanel();
		cardPanel5_right.add(cardPanel5_right_bt1, "직원 전체정보");
		cardPanel5_right_bt1.setLayout(null);
		cardPanel5_right_bt1.add(인사스크롤페인);

		cardPanel5_right_bt5 = new JPanel();
		cardPanel5_right.add(cardPanel5_right_bt5, "시스템 계정정보");
		cardPanel5_right_bt5.setLayout(null);
		cardPanel5_right_bt5.add(시스템계정스크롤페인);

	}

	private void initLabel() {

		ERPlogo = new ImageIcon("img/ERPlogo.jpg");
		logolabel = new JLabel(ERPlogo);
		logolabel.setBounds(0, 0, 201, 73);
		mainPanel.add(logolabel);

		원재료라벨 = new JLabel("원재료");
		원재료라벨.setFont(new Font("굴림", Font.PLAIN, 24));
		원재료라벨.setBounds(63, 51, 78, 31);
		cardPanel2_left.add(원재료라벨);

		분류라벨 = new JLabel("분류");
		분류라벨.setBounds(12, 133, 57, 15);
		cardPanel1_right_bt1.add(분류라벨);

		메뉴검색라벨 = new JLabel("메뉴 찾기");
		메뉴검색라벨.setBounds(202, 133, 57, 15);
		cardPanel1_right_bt1.add(메뉴검색라벨);

		정산주문번호라벨 = new JLabel("주문번호 :");
		정산주문번호라벨.setFont(new Font("굴림", Font.PLAIN, 18));
		정산주문번호라벨.setBounds(729, 605, 93, 32);
		cardPanel3_right_bt1.add(정산주문번호라벨);

		정산총판매금액라벨 = new JLabel("총 판매금액 :");
		정산총판매금액라벨.setFont(new Font("굴림", Font.PLAIN, 18));
		정산총판매금액라벨.setBounds(972, 606, 111, 32);
		cardPanel3_right_bt1.add(정산총판매금액라벨);

		정산_원_라벨 = new JLabel("원");
		정산_원_라벨.setFont(new Font("굴림", Font.PLAIN, 18));
		정산_원_라벨.setBounds(1223, 605, 18, 33);
		cardPanel3_right_bt1.add(정산_원_라벨);

		정산주문상세라벨 = new JLabel("주문별 상세내역");
		정산주문상세라벨.setFont(new Font("굴림", Font.BOLD, 24));
		정산주문상세라벨.setBounds(567, 35, 195, 58);
		cardPanel3_right_bt1.add(정산주문상세라벨);

		dailySalesLB = new JLabel("당일매출");
		dailySalesLB.setFont(new Font("굴림", Font.BOLD, 24));
		dailySalesLB.setBounds(12, 10, 100, 64);
		cardPanel3_right_bt2.add(dailySalesLB);

		dailyTotalSalesLB = new JLabel("일일 총매출 :");
		dailyTotalSalesLB.setFont(new Font("굴림", Font.PLAIN, 18));
		dailyTotalSalesLB.setBounds(12, 599, 113, 32);
		cardPanel3_right_bt2.add(dailyTotalSalesLB);

		monthSalesLB = new JLabel("월매출");
		monthSalesLB.setFont(new Font("굴림", Font.BOLD, 24));
		monthSalesLB.setBounds(97, 10, 81, 64);
		cardPanel3_right_bt3.add(monthSalesLB);

		yearLB = new JLabel("년도");
		yearLB.setFont(new Font("굴림", Font.PLAIN, 16));
		yearLB.setBounds(97, 82, 39, 18);
		cardPanel3_right_bt3.add(yearLB);

		monthLB = new JLabel("월");
		monthLB.setFont(new Font("굴림", Font.PLAIN, 16));
		monthLB.setBounds(251, 82, 23, 18);
		cardPanel3_right_bt3.add(monthLB);

		monthLB = new JLabel("월");
		monthLB.setFont(new Font("굴림", Font.PLAIN, 16));
		monthLB.setBounds(166, 82, 23, 18);
		cardPanel3_right_bt3.add(monthLB);
		
		상품재고라벨 = new JLabel("상품재고");
		상품재고라벨.setFont(new Font("굴림", Font.PLAIN, 24));
		상품재고라벨.setBounds(24, 60, 110, 31);
		cardPanel4_right_bt1.add(상품재고라벨);
	}

	private void initTextField() {

		메뉴검색입력 = new JTextField();
		메뉴검색입력.setBounds(202, 156, 203, 23);
		cardPanel1_right_bt1.add(메뉴검색입력);
		메뉴검색입력.setColumns(10);

		orderNumberTF = new JTextField();
		orderNumberTF.setBounds(834, 606, 91, 35);
		cardPanel3_right_bt1.add(orderNumberTF);
		orderNumberTF.setColumns(10);
		orderNumberTF.setEditable(false);

		ordersumTF = new JTextField();
		ordersumTF.setBounds(1095, 605, 116, 33);
		cardPanel3_right_bt1.add(ordersumTF);
		ordersumTF.setColumns(10);
		ordersumTF.setEditable(false);

		dailyTotalSalesTF = new JTextField();
		dailyTotalSalesTF.setBounds(137, 599, 116, 32);
		cardPanel3_right_bt2.add(dailyTotalSalesTF);
		dailyTotalSalesTF.setColumns(10);
		dailyTotalSalesTF.setEditable(false);

		인사검색창 = new JTextField();
		인사검색창.setBounds(12, 156, 203, 23);
		cardPanel5_right_bt1.add(인사검색창);
		인사검색창.setColumns(10);
		
		
	}

	private void initButton() {
		// 카테고리 버튼
		상단메뉴버튼 = new JButton("메뉴관리");
		상단메뉴버튼.setFont(new Font("굴림", Font.BOLD, 22));
		상단메뉴버튼.setBounds(201, 0, 130, 73);
		mainPanel.add(상단메뉴버튼);
		상단메뉴버튼.setName("메뉴관리");

		상단재고버튼 = new JButton("재고관리");
		상단재고버튼.setFont(new Font("굴림", Font.BOLD, 22));
		상단재고버튼.setBounds(331, 0, 130, 73);
		mainPanel.add(상단재고버튼);
		상단재고버튼.setName("재고관리");

		상단정산버튼 = new JButton("정산관리");
		상단정산버튼.setFont(new Font("굴림", Font.BOLD, 22));
		상단정산버튼.setBounds(461, 0, 130, 73);
		mainPanel.add(상단정산버튼);
		상단정산버튼.setName("정산관리");

		상단판매버튼 = new JButton("판매관리");
		상단판매버튼.setFont(new Font("굴림", Font.BOLD, 22));
		상단판매버튼.setBounds(591, 0, 130, 73);
		mainPanel.add(상단판매버튼);
		상단판매버튼.setName("판매관리");

		상단인사버튼 = new JButton("인사관리");
		상단인사버튼.setFont(new Font("굴림", Font.BOLD, 22));
		상단인사버튼.setBounds(721, 0, 130, 73);
		mainPanel.add(상단인사버튼);
		상단인사버튼.setName("인사관리");

		상단로그아웃 = new JButton("로그아웃");
		상단로그아웃.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 20));
		상단로그아웃.setBounds(1454, 0, 130, 73);
		상단로그아웃.setForeground(Color.RED);
		mainPanel.add(상단로그아웃);

		메뉴전체버튼 = new JButton("메뉴 전체정보");
		메뉴전체버튼.setBounds(24, 60, 154, 36);
		cardPanel1_left.add(메뉴전체버튼);
		메뉴전체버튼.setName("메뉴 전체정보");

		메뉴추가버튼 = new JButton("메뉴 추가");
		메뉴추가버튼.setBounds(24, 120, 154, 36);
		cardPanel1_left.add(메뉴추가버튼);

		메뉴수정버튼 = new JButton("메뉴 수정");
		메뉴수정버튼.setBounds(24, 180, 154, 36);
		cardPanel1_left.add(메뉴수정버튼);

		메뉴삭제버튼 = new JButton("메뉴 삭제");
		메뉴삭제버튼.setBounds(24, 240, 154, 36);
		cardPanel1_left.add(메뉴삭제버튼);

		메뉴검색버튼 = new JButton("검색");
		메뉴검색버튼.setBounds(423, 156, 65, 23);
		cardPanel1_right_bt1.add(메뉴검색버튼);

		재료전체버튼 = new JButton("원재료 전체정보");
		재료전체버튼.setName("원재료 전체정보");
		재료전체버튼.setBounds(24, 104, 154, 36);
		cardPanel2_left.add(재료전체버튼);

		재료추가버튼 = new JButton("원재료 추가");
		재료추가버튼.setBounds(24, 164, 154, 36);
		cardPanel2_left.add(재료추가버튼);

		재료수정버튼 = new JButton("원재료 수정");
		재료수정버튼.setBounds(24, 224, 154, 36);
		cardPanel2_left.add(재료수정버튼);

		재료삭제버튼 = new JButton("원재료 삭제");
		재료삭제버튼.setBounds(24, 284, 154, 36);
		cardPanel2_left.add(재료삭제버튼);

		Left3_1 = new JButton("당일주문별 상세내역");
		Left3_1.setName("당일주문별 상세내역");
		Left3_1.setBounds(24, 60, 154, 36);
		cardPanel3_left.add(Left3_1);

		Left3_2 = new JButton("당일매출");
		Left3_2.setName("당일매출");
		Left3_2.setBounds(24, 120, 154, 36);
		cardPanel3_left.add(Left3_2);

		Left3_3 = new JButton("월매출");
		Left3_3.setName("월매출");
		Left3_3.setBounds(24, 180, 154, 36);
		cardPanel3_left.add(Left3_3);
		
		Left4_1 = new JButton("상품재고");
		Left4_1.setName("상품재고");
		Left4_1.setBounds(24, 60, 154, 36);
		cardPanel4_left.add(Left4_1);
		
		상품재고새로고침버튼 = new JButton("새로고침(F5)");
		상품재고새로고침버튼.setBounds(900, 90, 120, 36);
		cardPanel4_right_bt1.add(상품재고새로고침버튼);
		

		인사전체버튼 = new JButton("직원 전체정보");
		인사전체버튼.setName("직원 전체정보");
		인사전체버튼.setBounds(24, 60, 154, 36);
		cardPanel5_left.add(인사전체버튼);

		인사검색버튼 = new JButton("검색");
		인사검색버튼.setBounds(227, 156, 65, 23);
		cardPanel5_right_bt1.add(인사검색버튼);

		인사추가버튼 = new JButton("직원 추가");
		인사추가버튼.setBounds(24, 120, 154, 36);
		cardPanel5_left.add(인사추가버튼);

		인사수정버튼 = new JButton("직원 정보수정");
		인사수정버튼.setBounds(24, 180, 154, 36);
		cardPanel5_left.add(인사수정버튼);

		인사삭제버튼 = new JButton("직원 정보삭제");
		인사삭제버튼.setBounds(24, 240, 154, 36);
		cardPanel5_left.add(인사삭제버튼);

		시스템계정버튼 = new JButton("시스템 계정정보");
		시스템계정버튼.setName("시스템 계정정보");
		시스템계정버튼.setBounds(24, 300, 154, 36);
		cardPanel5_left.add(시스템계정버튼);

		calculateBT = new JButton("정산");
		calculateBT.setFont(new Font("굴림", Font.PLAIN, 24));
		calculateBT.setBounds(1257, 599, 113, 52);
		cardPanel3_right_bt2.add(calculateBT);

	}

	private void initComboBox() {

		메뉴카테고리콤보 = new JComboBox(new String[] { "전체", "햄버거", "감자튀김", "커피", "음료" });
		메뉴카테고리콤보.setBounds(12, 156, 133, 23);
		cardPanel1_right_bt1.add(메뉴카테고리콤보);
		
		JLabel lblNewLabel = new JLabel("메뉴 전체정보");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 24));
		lblNewLabel.setBounds(12, 56, 173, 32);
		cardPanel1_right_bt1.add(lblNewLabel);

		yearCB = new JComboBox(new String[] { "2020", "2019", "2018", "2017" });

		yearCB.setBounds(132, 79, 81, 21);
		cardPanel3_right_bt3.add(yearCB);

		monthCB = new JComboBox(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" });
		monthCB.setBounds(269, 79, 63, 21);
		cardPanel3_right_bt3.add(monthCB);

	}

	private void initListener() {

		// 메뉴관리
		상단메뉴버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cdMainPanel.getLayout());
				cl.show(cdMainPanel, ((JButton) e.getSource()).getName());
				menuNotifyProductsList();
			}
		});
		// 재고ㅓ관리
		상단재고버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cdMainPanel.getLayout());
				cl.show(cdMainPanel, ((JButton) e.getSource()).getName());
			}
		});
		// 정산관리
		상단정산버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cdMainPanel.getLayout());
				cl.show(cdMainPanel, ((JButton) e.getSource()).getName());
			}
		});
		// 유통관리
		상단판매버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cdMainPanel.getLayout());
				cl.show(cdMainPanel, ((JButton) e.getSource()).getName());
			}
		});
		// 인사관리
		상단인사버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cdMainPanel.getLayout());
				cl.show(cdMainPanel, ((JButton) e.getSource()).getName());
			}
		});

		// 1번카테고리의 좌측 1번째 버튼
		메뉴전체버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cardPanel1_right.getLayout());
				cl.show(cardPanel1_right, ((JButton) e.getSource()).getName());
				menuNotifyProductsList();
			}
		});

		메뉴추가버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MenuCFrame(adminMain);
				setEnabled(false);
			}
		});

		메뉴수정버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MenuUFrame(adminMain);
				setEnabled(false);
			}
		});

		메뉴삭제버튼.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (상품테이블.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "삭제할 데이터를 선택 하세요.");
				} else {
					int id = (int) 상품테이블.getValueAt(상품테이블.getSelectedRow(), 0);
					String category_name = (String) 상품테이블.getValueAt(상품테이블.getSelectedRow(), 1);
					String name = (String) 상품테이블.getValueAt(상품테이블.getSelectedRow(), 2);
					int price = (int) 상품테이블.getValueAt(상품테이블.getSelectedRow(), 3);
					String ingredient = (String) 상품테이블.getValueAt(상품테이블.getSelectedRow(), 4);
					String quantity = (String) 상품테이블.getValueAt(상품테이블.getSelectedRow(), 5);
					ProductsCategories productsCategories = new ProductsCategories();
					productsCategories.setId(id);
					productsCategories.setCategories(new Categories(0, category_name));
					productsCategories.setName(name);
					productsCategories.setPrice(price);
					productsCategories.setIngredient(ingredient);
					productsCategories.setQuantity(quantity);
					new MenuDFrame(adminMain, productsCategories);
					setEnabled(false);
				}
			}
		});

		재료전체버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cardPanel2_right.getLayout());
				cl.show(cardPanel2_right, ((JButton) e.getSource()).getName());
			}
		});

		재료전체버튼.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("전체목록");
				invenNotifyUserList();
			}
		});

		재료추가버튼.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("원재료 추가");
				new MaterialCFrame(adminMain);
				setEnabled(false);
			}
		});
		재료수정버튼.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("원재료 수정");
				// -1 선택X
				if (원재료테이블.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "수정할 데이터를 선택 하세요.");
				} else {
					int id = (int) 원재료테이블.getValueAt(원재료테이블.getSelectedRow(), 0);
					String name = (String) 원재료테이블.getValueAt(원재료테이블.getSelectedRow(), 1);
					double stock = (double) 원재료테이블.getValueAt(원재료테이블.getSelectedRow(), 2);
					String unit = (String) 원재료테이블.getValueAt(원재료테이블.getSelectedRow(), 3);
					Inventories inventories = new Inventories(id, name, stock, unit);
					new MaterialUFrame(adminMain, inventories);
					setEnabled(false);
				}

			}
		});
		재료삭제버튼.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("원재료 삭제");
				if (원재료테이블.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "삭제할 데이터를 선택 하세요.");
				} else {
					int id = (int) 원재료테이블.getValueAt(원재료테이블.getSelectedRow(), 0);
					String name = (String) 원재료테이블.getValueAt(원재료테이블.getSelectedRow(), 1);
					double stock = (double) 원재료테이블.getValueAt(원재료테이블.getSelectedRow(), 2);
					String unit = (String) 원재료테이블.getValueAt(원재료테이블.getSelectedRow(), 3);
					Inventories inventories = new Inventories(id, name, stock, unit);
					new MaterialDFrame(adminMain, inventories);
					setEnabled(false);
				}

				invenNotifyUserList();
			}
		});

		Left3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cardPanel3_right.getLayout());
				cl.show(cardPanel3_right, ((JButton) e.getSource()).getName());
			}
		});

		Left3_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cardPanel3_right.getLayout());
				cl.show(cardPanel3_right, ((JButton) e.getSource()).getName());
			}
		});

		Left3_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cardPanel3_right.getLayout());
				cl.show(cardPanel3_right, ((JButton) e.getSource()).getName());
			}
		});

		인사전체버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cardPanel5_right.getLayout());
				cl.show(cardPanel5_right, ((JButton) e.getSource()).getName());
			}
		});

		// 인사관리 화면에서 검색어 입력 후 엔터키 입력시 발생하는 이벤트
		인사검색창.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String keyword = 인사검색창.getText();
				인사테이블모델.setRowCount(0);
				인사테이블모델 = DBUtils.makeRowEmployeesSearch(인사테이블모델, keyword);

			}
		});

		// 위 tfSearchEmp 의 액션리스너 부분과 완전히 동일함 (인사관리 화면에서 검색어 입력 후 검색 버튼 클릭시)
		인사검색버튼.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String keyword = 인사검색창.getText();
				인사테이블모델.setRowCount(0);
				인사테이블모델 = DBUtils.makeRowEmployeesSearch(인사테이블모델, keyword);

			}
		});

		인사추가버튼.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddEmployees(adminMain);

			}
		});

		// 직원 정보 삭제
		인사삭제버튼.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = 인사테이블.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "먼저 삭제할 직원을 선택하시오");
					return;
				}

				int id = (Integer) 인사테이블모델.getValueAt(인사테이블.getSelectedRow(), 0);
				String name = (String) 인사테이블모델.getValueAt(인사테이블.getSelectedRow(), 1);
				String jumin = (String) 인사테이블모델.getValueAt(인사테이블.getSelectedRow(), 2);
				String phone = (String) 인사테이블모델.getValueAt(인사테이블.getSelectedRow(), 3);
				String hire_date = (String) 인사테이블모델.getValueAt(인사테이블.getSelectedRow(), 4);

				Employees employee = Employees.builder().id(id).name(name).jumin(jumin).phone(phone)
						.hire_date(hire_date).build();
				new DeleteEmployees(adminMain, employee);
				setEnabled(false);
				// tableModel3.removeRow(selectedRow); // 컴포넌트 상에서 삭제됨. DB는 손대지 않음
				
			}
		});

		인사수정버튼.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = 인사테이블.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "먼저 수정할 직원을 선택하시오");
					return;
				}

				int id = (Integer) 인사테이블모델.getValueAt(인사테이블.getSelectedRow(), 0);
				String name = (String) 인사테이블모델.getValueAt(인사테이블.getSelectedRow(), 1);
				String jumin = (String) 인사테이블모델.getValueAt(인사테이블.getSelectedRow(), 2);
				String phone = (String) 인사테이블모델.getValueAt(인사테이블.getSelectedRow(), 3);
				String hire_date = (String) 인사테이블모델.getValueAt(인사테이블.getSelectedRow(), 4);

				Employees employee = Employees.builder().id(id).name(name).jumin(jumin).phone(phone)
						.hire_date(hire_date).build();
				new UpdateEmployees(adminMain, employee);
				setEnabled(false);
			}
		});

		시스템계정버튼.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cardPanel5_right.getLayout());
				cl.show(cardPanel5_right, ((JButton) e.getSource()).getName());
			}
		});

		메뉴카테고리콤보.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String temp = 메뉴카테고리콤보.getSelectedItem().toString();
				상품테이블모델.setRowCount(0);
				if (메뉴카테고리콤보.getSelectedItem().equals("전체")) {
					상품테이블모델 = DBUtils.makeRowProducts(상품테이블모델);
				} else {
					상품테이블모델 = ProductsDao.selectCategory(상품테이블모델, temp);
				}
			}
		});

		메뉴검색버튼.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String keyword = 메뉴검색입력.getText();
				상품테이블모델.setRowCount(0);
				상품테이블모델 = DBUtils.makeRowProductsCategoriesSearch(상품테이블모델, keyword);
			}
		});

		// 로그아웃버튼
		상단로그아웃.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "로그아웃하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					dispose();
					new Login();
				}
			}
		});

		주문번호테이블.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				주문번호의상세내역테이블모델.setRowCount(0);
				int row = 주문번호테이블.getSelectedRow();
				int orderId = (Integer) 주문번호테이블.getValueAt(row, 0);
				주문번호의상세내역테이블모델 = DBUtils.makeRowDetailOrder(주문번호의상세내역테이블모델, orderId);
				orderNumberTF.setText(orderId + "");
				int row2 = 주문번호의상세내역테이블.getRowCount();

				int sum = 0;
				for (int i = 0; i < row2; i++) {

					int count = (Integer) 주문번호의상세내역테이블.getValueAt(i, 1);
					int price = (Integer) 주문번호의상세내역테이블.getValueAt(i, 2);
					int sumprice = count * price;

					sum += sumprice;

				}
				ordersumTF.setText(sum + "");

			}
		});

		월매출테이블.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				해당날짜주문상세내역테이블모델.setRowCount(0);
				int row = 월매출테이블.getSelectedRow();
				int orderdate = (Integer) 월매출테이블.getValueAt(row, 2);

				int year = (Integer) 월매출테이블.getValueAt(row, 0);
				int month = (Integer) 월매출테이블.getValueAt(row, 1);
				int day = (Integer) 월매출테이블.getValueAt(row, 2);

				String date = "" + (year-2000) + "/" + (month < 10 ? "0" + month : month) + "/" + (day < 10 ? "0" + day : day);

				해당날짜주문상세내역테이블모델 = DBUtils.makeRowDetailOrder2(해당날짜주문상세내역테이블모델, date);
			}
		});

		calculateBT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "일일 정산마감을 진행하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {

					dailyTotalSalesTF.setText("");
					일매출테이블모델.setRowCount(0);

				}
			}
		});
		
		상품재고새로고침버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				notifyStockList();
			}
		});

		// 일일매출 총합계
		int row = 일매출테이블.getRowCount();
		int sum = 0;
		for (int i = 0; i < row; i++) {
			int count = (Integer) 일매출테이블모델.getValueAt(i, 3);
			int price = (Integer) 일매출테이블모델.getValueAt(i, 4);
			int sumPrice = count * price;
			sum += sumPrice;
		}
		dailyTotalSalesTF.setText(sum + "");
	}

	public void invenNotifyUserList() {
		원재료테이블모델.setRowCount(0);
		원재료테이블모델 = DBUtils.makeRowInventories(원재료테이블모델);
	}

	public void menuNotifyProductsList() {
		상품테이블모델.setRowCount(0);
		상품테이블모델 = DBUtils.makeRowProducts(상품테이블모델);
	}

	public void notifyEmployeesList() {
		인사테이블모델.setRowCount(0);
		인사테이블모델 = DBUtils.makeRowEmployees(인사테이블모델);
	}
	
	public void notifyStockList() {
		판매테이블모델.setRowCount(0);
		판매테이블모델 = DBUtils.makeRowStocks(판매테이블모델);
	}

	public static void main(String[] args) {
		new AdminMain();
	}
}

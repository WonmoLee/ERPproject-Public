package gui.user;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import dao.OrderItemsDao;
import dao.OrdersDao;
import dao.ProductsCategoriesDao;
import dao.ProductsDao;
import models.Products;
import models.ProductsCategories;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Image;

import javax.swing.border.LineBorder;
import java.awt.Font;

public class KioskApp extends JFrame {
	private KioskApp kioskApp = this;
	final static String[] CATEGORIES = {"햄버거", "감자튀김", "커피", "음료"};
	// private Map<String,Integer> priceInfo;	// Products 테이블로부터 각 메뉴 상품의 가격정보를 가져옴
	private Map<String,Integer> cart = new HashMap<>();	// 키오스크에서 고객이 선택한 상품과 그 수량 정보를 담고 있음
	private Map<String,Products> productsInfo = new HashMap<>();
	
	private JPanel panelDrink,panelPotato,panelHamburger,panelCoffee;
	private JTextPane tpResult;
	
	private JButton 결제버튼, 취소버튼;
	private List<MenuItem> menuItems = new ArrayList<>();
	private JLabel lblNewLabel;
	

	public KioskApp() {
		getContentPane().setLayout(null);
		
		JPanel panelLeft = new JPanel();
		panelLeft.setBounds(0,0,670,1000);
		JPanel panelRight = new JPanel();
		panelRight.setBackground(Color.WHITE);
		panelRight.setBounds(670,0,300,1000);

		JTabbedPane tabPane = new JTabbedPane();
		tabPane.setBounds(12,10,650,900);

		panelLeft.setLayout(null);
		panelLeft.add(tabPane);
		
        
		panelDrink = new JPanel();
		panelPotato = new JPanel();
        panelHamburger = new JPanel();
        panelCoffee = new JPanel();
        
        tabPane.add(panelHamburger,"햄버거");
        tabPane.add(panelPotato,"감자튀김");
        tabPane.add(panelCoffee,"커피");
        tabPane.add(panelDrink,"음료");
        
        
        
        
        panelHamburger.setLayout(null);
        panelPotato.setLayout(null);
        panelCoffee.setLayout(null);
        panelDrink.setLayout(null);
        
        //The following line enables to use scrolling tabs.
        //tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        

        initData();
        initMenuItems();
        

		
		getContentPane().add(panelLeft);
		getContentPane().add(panelRight);
		panelRight.setLayout(null);
		
		tpResult = new JTextPane();
		tpResult.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		tpResult.setBounds(12, 362, 272, 486);
		panelRight.add(tpResult);
		
		결제버튼 = new JButton("결 제");
		결제버튼.setBounds(28, 885, 97, 23);
		panelRight.add(결제버튼);
		
		취소버튼 = new JButton("취 소");
		취소버튼.setBounds(174, 885, 97, 23);
		panelRight.add(취소버튼);
		
		
		ImageIcon lotteriaLogo2 = new ImageIcon("./img/lotterialogo2.png");
		lblNewLabel = new JLabel(lotteriaLogo2);
//		lblNewLabel.setIcon(new ImageIcon(new ImageIcon("./img/lotterialogo2.png").getImage().getScaledInstance(270, 320, Image.SCALE_DEFAULT)));
		lblNewLabel.setBackground(UIManager.getColor("Button.darkShadow"));
		lblNewLabel.setBounds(12, 90, 272, 189);
		panelRight.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("*장바구니");
		lblNewLabel_1.setForeground(new Color(255, 0, 0));
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel_1.setBounds(12, 310, 97, 30);
		panelRight.add(lblNewLabel_1);
		
		
		initListener();
		
		
		setSize(1020, 1000);
		setVisible(true);
	}
	
	
	private void initMenuItems() {
		
		ProductsCategoriesDao productsCategoriesDao = ProductsCategoriesDao.getInstance();
		
		for (String category : CATEGORIES) {
			List<ProductsCategories> productsCategories = productsCategoriesDao.selectAll(category);
			int i =0;

			for (ProductsCategories pc : productsCategories) {
				String menuName = pc.getName();
			
				// 메뉴 아이템의 x, y 좌표를 계산함
				int posX = (i%3) * 200;
				int posY = (i/3) * 200;
				
				MenuItem menuItem = null;
				switch (category) {
				
				case "햄버거":	menuItem = new MenuItem(kioskApp, menuName, posX, posY);
		        				panelHamburger.add(menuItem);
		        				menuItems.add(menuItem);
		        				break;
				case "감자튀김":	menuItem = new MenuItem(kioskApp, menuName, posX, posY);
								panelPotato.add(menuItem);
								menuItems.add(menuItem);
								break;
				case "커피":		menuItem = new MenuItem(kioskApp, menuName, posX, posY);
								panelCoffee.add(menuItem);
								menuItems.add(menuItem);
								break;
				case "음료":		menuItem = new MenuItem(kioskApp, menuName, posX, posY);
								panelDrink.add(menuItem);
								menuItems.add(menuItem);
								break;
				}
				
				i++;
				
			}
		}
	}
	
	private void initData() {
		ProductsDao dao = ProductsDao.getInstance();
		List<Products> allProducts = dao.selectAll();
		
		for (Products p : allProducts) {
			productsInfo.put(p.getName(), p);
		}
		
	}
	
	
	private void resetKiosk() {
		cart.clear();
		tpResult.setText("");
		for (MenuItem mi : menuItems) {
			mi.reset();
		}
	}
	
	private void initListener() {
		결제버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "결제하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					OrdersDao ordersDao = OrdersDao.getInstance();
					ordersDao.insertNewOrder();
					
					int curSequence = ordersDao.getSequenceNum();
					// System.out.println("ORDERS_SEQ : " + curSequence);
					OrderItemsDao.getInstance().insertOrderItems(curSequence, cart, productsInfo);
					
					JOptionPane.showMessageDialog(null, "결제 완료");
					resetKiosk();
					dispose();
					new KioskMain();
				}
				
			}
		});
		
		
		취소버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int result = JOptionPane.showConfirmDialog(null, "메인으로 돌아가시겠습니까?", "알림", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					resetKiosk();
					dispose();
					new KioskMain();
				}
			}
		});
	}
	
	public void notifyPlus(String item) {
		if (cart.get(item) == null) {
			cart.put(item, 1);
		} else {
			int count = cart.get(item);
			cart.put(item, count+1);
		}
		
		calcAndShow();
	}
	
	public void notifyMinus(String item) {
		if (cart.get(item) != null) {
			int count = cart.get(item);
			if (--count == 0) {
				// cart.put(item, null);
				cart.remove(item);
			} else {
				cart.put(item, count);
			}
		}
		
		calcAndShow();
	}
	
	// 계산하고 오른쪽에 보여주기
	private void calcAndShow() {
		tpResult.setText("");
		
		StyledDocument doc = tpResult.getStyledDocument();
		
		
		Set<String> items = cart.keySet();
		int sumTotal = 0;
		for (String item : items) {
			
			int itemSum = cart.get(item) * productsInfo.get(item).getPrice();
			
			String line = item + " : " + cart.get(item) + " X " + productsInfo.get(item).getPrice() + "원" + " = " + itemSum + " 원\n";
			try {
				doc.insertString(doc.getLength(), line, null);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sumTotal = sumTotal + itemSum;
		
		}
		
		try {
			doc.insertString(doc.getLength(), "\n==============================\n", null);
			doc.insertString(doc.getLength(), "총액 : " + sumTotal, null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SimpleAttributeSet attribs = new SimpleAttributeSet();
		StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_RIGHT);
		doc.setParagraphAttributes(0, doc.getLength(), attribs, true);
		
	}
	
	public static void main(String[] args) {
		new KioskApp();
	}
}



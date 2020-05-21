package gui.user;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;


//메뉴 아이템 한 개 (그림 올릴수 있는 아이템 버튼 + 플러스버튼 + 마이너스버튼 + 수량텍스트필드 세트)
public class MenuItem extends JPanel {
	private KioskApp kioskApp;
	private String menuName;
	
	private JTextField tfCount;	// 수량 표시
	private JButton btnItem;	// 그림 올릴수 있는 대형 사각버튼
	private JButton btnMinus;	// 마이너스 버튼
	private JButton btnPlus;	// 플러스 버튼
	
	private static ImageIcon[] menuImages;
	private static String[] menuNames = {"불고기버거", "새우버거", "데리버거", "치킨버거", "더블X2", "T-REX", "한우불고기", "AZ버거", "핫크리스피버거", "클래식 치즈버거",
										"포테이토", "양념감자", "양념감자", "양념감자", 
										"아메리카노", "카페라떼",
										"핫초코", "아이스티", "오렌지주스", "콜라", "사이다"};
	private static int count = 0;
	
	static {
		menuImages = new ImageIcon[21];
		
		for (int i = 0; i < menuImages.length; i++) {
			ImageIcon image = new ImageIcon (new ImageIcon("./img/"+ menuNames[i] + ".png").getImage().getScaledInstance(200, 160, Image.SCALE_DEFAULT));
			
			menuImages[i] = image;
		}
		
		
	}
	public MenuItem(KioskApp kioskApp, String menuName, int x, int y) {
		this.kioskApp = kioskApp;
		this.menuName = menuName;
		this.setBounds(x, y, 200, 200);
		
		this.setLayout(null);
		
		initObject();
		initListener();
		
	}

	
	private void initObject() {
		// btnItem = new JButton(menuName);
		btnItem = new JButton(menuImages[count++]);
		btnItem.setFont(new Font("굴림", Font.BOLD, 16));
		btnItem.setName(menuName);
//		btnItem.setIcon(new ImageIcon(new ImageIcon().getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
		btnItem.setBounds(10, 10, 180, 150);
		this.add(btnItem);
		
		btnMinus = new JButton("-");
		btnMinus.setName("빼기" + menuName);
		btnMinus.setBounds(20, 165, 50, 30);
		this.add(btnMinus);
		
		tfCount = new JTextField("0");
		tfCount.setBounds(75, 165, 50, 30);
		this.add(tfCount);
		tfCount.setColumns(10);
		
		btnPlus = new JButton("+");
		btnPlus.setName("더하기" + menuName);
		btnPlus.setBounds(130, 165, 50, 30);
		this.add(btnPlus);
	}

	private void initListener() {
		MenuItemListener listener = new MenuItemListener();
		
		btnItem.addActionListener(listener);
		btnMinus.addActionListener(listener);
		btnPlus.addActionListener(listener);
	}
	
	private void setCount(int count) {
		tfCount.setText(String.valueOf(count));
	}
	
	public void reset() {
		System.out.println("reset 실행");
		setCount(0);
		count = 0;
	}
	
	
	class MenuItemListener implements ActionListener  {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			String name = button.getName();
			// System.out.println("MenuItemListener (버튼이름) : " + name);
			
			// String menuName = null;
			// System.out.println("MenuItemListener : " + menuName);
			int count = Integer.parseInt(tfCount.getText());
			
			if (name.startsWith("더하기")) {		// + 버튼 클릭
				// menuName = name.substring(3);
				tfCount.setText(String.valueOf(++count));
				kioskApp.notifyPlus(menuName);
				//System.out.println("MenuItemListener 더하기 : " + menuName);
			} else if (name.startsWith("빼기")) {		// - 버튼 클릭
				// menuName = name.substring(2);
				if (count>0) tfCount.setText(String.valueOf(--count));
				kioskApp.notifyMinus(menuName);
				//System.out.println("MenuItemListener 빼기 : " + menuName);
			} else {	// 메뉴이름 버튼 클릭
				// menuName = name;
				tfCount.setText(String.valueOf(++count));
				kioskApp.notifyPlus(menuName);
			}
		}
	}
	
}
package gui.user;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KioskMain extends JFrame {
	
	private JPanel mainPanel;
	private ImageIcon lotteriaLogo, storeMealImg, takeOutImg;
	private JLabel lotteriaImgLB;
	private JButton storeMealBT, takeOutBT;
//	private LineBorder a, b;
	
	public KioskMain() {
		
		init();
		initListener();
		
		getContentPane().setLayout(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Lotteria Kiosk");
		setSize(1000, 700);
		setLocationRelativeTo(null);
		setVisible(true);
		
		
	}
	
	private void init() {
		
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(0, 0, 984, 661);
		getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		lotteriaLogo = new ImageIcon("img/Lotteria.png");
		lotteriaImgLB = new JLabel(lotteriaLogo);
		lotteriaImgLB.setBounds(227, 67, 529, 140);
		mainPanel.add(lotteriaImgLB);
		
		storeMealImg = new ImageIcon("img/storemeal.png");
		storeMealBT = new JButton(storeMealImg);
//		a = new LineBorder(Color.RED,2);
//		storeMealBT.setBorder(a);
		storeMealBT.setBounds(155, 244, 293, 294);
		mainPanel.add(storeMealBT);
		
		takeOutImg = new ImageIcon("img/takeout.png");
		takeOutBT = new JButton(takeOutImg);
//		b = new LineBorder(Color.RED,2);
//		takeOutBT.setBorder(b);
		takeOutBT.setBounds(536, 244, 293, 294);
		mainPanel.add(takeOutBT);
	}
	
	private void initListener() {
		
		storeMealBT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new KioskApp();
				dispose();
			}
		});
	}
	
	public static void main(String[] args) {
		new KioskMain();
	}
}

package gui.admin.productsub;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.InventoriesDao;
import gui.admin.AdminMain;
import models.Inventories;

public class MaterialCFrame extends JFrame {
	private JFrame addFrame = this;
	private AdminMain adminMain;
	private Container backgroundPanel;
	private JLabel laName, laStock, laUnit;
	private JTextField tfName, tfStock, tfUnit;
	private JPanel addPanel;
	private JButton addButton;

	
	public MaterialCFrame(AdminMain adminMain) {
		this.adminMain = adminMain;
		initObject();
		initDesign();
		initListener();
		setResizable(false);
		setVisible(true);
	}

	private void initObject() {
		addPanel = new JPanel();
		backgroundPanel = getContentPane();
		laName = new JLabel("원료명");
		laStock = new JLabel("원료량");
		laUnit = new JLabel("단위");
		
		tfName = new JTextField(20);
		tfStock = new JTextField(20);
		tfUnit = new JTextField(20);
		
		addButton = new JButton("추가하기");
	}

	private void initDesign() {
		setTitle("ERPsystem");
		setSize(400, 100);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		backgroundPanel.setLayout(new BorderLayout());

		addPanel.setLayout(new GridLayout(1, 6));
		
		laName.setHorizontalAlignment(JLabel.CENTER);
		laStock.setHorizontalAlignment(JLabel.CENTER);
		laUnit.setHorizontalAlignment(JLabel.CENTER);

		addPanel.add(laName);
		addPanel.add(tfName);

		addPanel.add(laStock);
		addPanel.add(tfStock);

		addPanel.add(laUnit);
		addPanel.add(tfUnit);

		backgroundPanel.add(addPanel, BorderLayout.CENTER);
		backgroundPanel.add(addButton, BorderLayout.SOUTH);

	}

	private void initListener() {
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = tfName.getText().trim();
				String stock = tfStock.getText().trim();
				String unit = tfUnit.getText().trim();
				
				if(name.length()==0||stock.length()==0||unit.length()==0) {
					JOptionPane.showMessageDialog(null, "입력한 값이 잘못되었습니다.");
				}else {
					Inventories inventories = new Inventories(
							0,
							name,
							Double.parseDouble(tfStock.getText()),
							unit);
					System.out.println(name);
					System.out.println(stock);
					System.out.println(unit);
					InventoriesDao inventoriesDao = InventoriesDao.getInstance();
					int result = inventoriesDao.insert(inventories);
					if(result == 1) {
						adminMain.invenNotifyUserList();
						addFrame.dispose();
						adminMain.setEnabled(true);
					}else {
						JOptionPane.showMessageDialog(null, "원재료 추가에 실패하였습니다.");
					}
				}
			}
		});
		
		addFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				adminMain.setEnabled(true);
				adminMain.setAlwaysOnTop(true);
				adminMain.setAlwaysOnTop(false);
			}
		});
	}
}
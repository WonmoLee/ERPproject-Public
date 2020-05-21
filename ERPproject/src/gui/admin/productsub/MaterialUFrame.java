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
import dao.ProductsDao;
import gui.admin.AdminMain;
import models.Inventories;

public class MaterialUFrame extends JFrame {
	private JFrame addFrame = this;
	private AdminMain adminMain;
	private Container backgroundPanel;
	//before
	private JLabel laBeforeName, laBeforeStock, laBeforeUnit;
	private JTextField tfBeforeName, tfBeforeStock, tfBeforeUnit;
	//after
	private JLabel laAfterName, laAfterStock, laAfterUnit;
	private JTextField tfAfterName, tfAfterStock, tfAfterUnit;
	private JPanel updatePanel;
	private JButton updateButton;
	//중간 여백
	private JLabel laMarginLeft;
	private JLabel laMarginRight;
	//가져온값
	Inventories inventories;

	
	public MaterialUFrame(AdminMain adminMain, Inventories inventories) {
		this.adminMain = adminMain;
		this.inventories = inventories;
		initObject();
		initDesign();
		initListener();
		setResizable(false);
		setVisible(true);
	}

	private void initObject() {
		updatePanel = new JPanel();
		backgroundPanel = getContentPane();
		
		laBeforeName = new JLabel("원료명");
		laBeforeStock = new JLabel("원료량");
		laBeforeUnit = new JLabel("단위");
		
		tfBeforeName = new JTextField(inventories.getName());
		tfBeforeStock = new JTextField(inventories.getStock()+"");
		tfBeforeUnit = new JTextField(inventories.getUnit());
		
		tfBeforeName.setEnabled(false);
		tfBeforeStock.setEnabled(false);
		tfBeforeUnit.setEnabled(false);
		
		laAfterName = new JLabel("원료명");
		laAfterStock = new JLabel("원료량");
		laAfterUnit = new JLabel("단위");
		
		tfAfterName = new JTextField(20);
		tfAfterStock = new JTextField(20);
		tfAfterUnit = new JTextField(20);
		
		laMarginLeft = new JLabel("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ▽");
		laMarginRight = new JLabel("▽ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		
		updateButton = new JButton("수정하기");
	}

	private void initDesign() {
		setTitle("ERPsystem");
		setSize(400, 200);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		backgroundPanel.setLayout(new BorderLayout());
		updatePanel.setLayout(new GridLayout(7, 2));
		
		laBeforeName.setHorizontalAlignment(JLabel.CENTER);
		laBeforeStock.setHorizontalAlignment(JLabel.CENTER);
		laBeforeUnit.setHorizontalAlignment(JLabel.CENTER);
		
		laAfterName.setHorizontalAlignment(JLabel.CENTER);
		laAfterStock.setHorizontalAlignment(JLabel.CENTER);
		laAfterUnit.setHorizontalAlignment(JLabel.CENTER);

		updatePanel.add(laBeforeName);
		updatePanel.add(tfBeforeName);
		
		updatePanel.add(laBeforeStock);
		updatePanel.add(tfBeforeStock);
		
		updatePanel.add(laBeforeUnit);
		updatePanel.add(tfBeforeUnit);
		
		updatePanel.add(laMarginLeft);
		updatePanel.add(laMarginRight);
		
		updatePanel.add(laAfterName);
		updatePanel.add(tfAfterName);
		
		updatePanel.add(laAfterStock);
		updatePanel.add(tfAfterStock);
		
		updatePanel.add(laAfterUnit);
		updatePanel.add(tfAfterUnit);
		
		backgroundPanel.add(updatePanel, BorderLayout.CENTER);
		backgroundPanel.add(updateButton, BorderLayout.SOUTH);

	}

	private void initListener() {
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = tfAfterName.getText().trim();
				String stock = tfAfterStock.getText().trim();
				String unit = tfAfterUnit.getText().trim();
				
				if(name.length()==0||stock.length()==0||unit.length()==0) {
					JOptionPane.showMessageDialog(null, "입력한 값이 잘못되었습니다.");
				}else {
					inventories.setName(name);
					inventories.setStock(Double.parseDouble(stock));
					inventories.setUnit(unit);
					ProductsDao productsDao = ProductsDao.getInstance();
					productsDao.parseProducts(tfBeforeName.getText(), tfAfterName.getText());
					InventoriesDao inventoriesDao = InventoriesDao.getInstance();
					int result = inventoriesDao.update(inventories);
					if(result == 1) {
						adminMain.invenNotifyUserList();
						addFrame.dispose();
						adminMain.setEnabled(true);
					}else {
						JOptionPane.showMessageDialog(null, "원재료 수정에 실패하였습니다.");
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
  
package gui.admin.productsub;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

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
import models.Products;
import models.ProductsCategories;

public class MenuDFrame extends JFrame {
	private JFrame addFrame = this;
	private AdminMain adminMain;
	private Container backgroundPanel;
	// before
	private JLabel laId, laCateName, laName, laPrice, laIngredient, laQuantity;
	private JTextField tfId, tfCateName, tfName, tfPrice, tfIngredient, tfQuantity;

	private JPanel deletePanel;
	private JButton deleteButton;
	// 가져온값
	ProductsCategories productsCategories;

	public MenuDFrame(AdminMain adminMain, ProductsCategories productsCategories) {
		this.adminMain = adminMain;
		this.productsCategories = productsCategories;
		initObject();
		initDesign();
		initListener();
		setResizable(false);
		setVisible(true);
	}

	private void initObject() {
		deletePanel = new JPanel();
		backgroundPanel = getContentPane();

		laId = new JLabel("상품번호");
		laCateName = new JLabel("카테고리");
		laName = new JLabel("상품명");
		laPrice = new JLabel("상품가격");
		laIngredient = new JLabel("상품원료");
		laQuantity = new JLabel("상품원료량");

		tfId = new JTextField(productsCategories.getId()+"");
		tfCateName = new JTextField(productsCategories.getCategories().getName());
		tfName = new JTextField(productsCategories.getName());
		tfPrice = new JTextField(productsCategories.getPrice()+"");
		tfIngredient = new JTextField(productsCategories.getIngredient());
		tfQuantity = new JTextField(productsCategories.getQuantity());

		tfId.setEnabled(false);
		tfCateName.setEnabled(false);
		tfName.setEnabled(false);
		tfPrice.setEnabled(false);
		tfIngredient.setEnabled(false);
		tfQuantity.setEnabled(false);

		deleteButton = new JButton("삭제하기");
	}

	private void initDesign() {
		setTitle("ERPsystem");
		setSize(400, 200);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		backgroundPanel.setLayout(new BorderLayout());
		deletePanel.setLayout(new GridLayout(6, 2));

		laId.setHorizontalAlignment(JLabel.CENTER);
		
		laCateName.setHorizontalAlignment(JLabel.CENTER);
		laName.setHorizontalAlignment(JLabel.CENTER);
		laPrice.setHorizontalAlignment(JLabel.CENTER);
		laIngredient.setHorizontalAlignment(JLabel.CENTER);
		laQuantity.setHorizontalAlignment(JLabel.CENTER);

		deletePanel.add(laId);
		deletePanel.add(tfId);
		
		deletePanel.add(laCateName);
		deletePanel.add(tfCateName);
		
		deletePanel.add(laName);
		deletePanel.add(tfName);
		
		deletePanel.add(laPrice);
		deletePanel.add(tfPrice);

		deletePanel.add(laIngredient);
		deletePanel.add(tfIngredient);

		deletePanel.add(laQuantity);
		deletePanel.add(tfQuantity);

		backgroundPanel.add(deletePanel, BorderLayout.CENTER);
		backgroundPanel.add(deleteButton, BorderLayout.SOUTH);

	}

	private void initListener() {
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					ProductsDao productsDao = ProductsDao.getInstance();
					int result = productsDao.delete(productsCategories);
					if (result == 1) {
						adminMain.invenNotifyUserList();
						addFrame.dispose();
						adminMain.setEnabled(true);
					} else {
						JOptionPane.showMessageDialog(null, "원재료 삭제에 실패하였습니다.");
					}
					adminMain.menuNotifyProductsList();
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
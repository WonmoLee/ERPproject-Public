  
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

public class MaterialDFrame extends JFrame {
	private JFrame addFrame = this;
	private AdminMain adminMain;
	private Container backgroundPanel;
	// before
	private JLabel laName, laStock, laUnit;
	private JTextField tfName, tfStock, tfUnit;

	private JPanel deletePanel;
	private JButton deleteButton;
	// 가져온값
	Inventories inventories;

	public MaterialDFrame(AdminMain adminMain, Inventories inventories) {
		this.adminMain = adminMain;
		this.inventories = inventories;
		initObject();
		initDesign();
		initListener();
		setResizable(false);
		setVisible(true);
	}

	private void initObject() {
		deletePanel = new JPanel();
		backgroundPanel = getContentPane();

		laName = new JLabel("원료명");
		laStock = new JLabel("원료량");
		laUnit = new JLabel("단위");

		tfName = new JTextField(inventories.getName());
		tfStock = new JTextField(inventories.getStock() + "");
		tfUnit = new JTextField(inventories.getUnit());

		tfName.setEnabled(false);
		tfStock.setEnabled(false);
		tfUnit.setEnabled(false);

		deleteButton = new JButton("삭제하기");
	}

	private void initDesign() {
		setTitle("ERPsystem");
		setSize(400, 200);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		backgroundPanel.setLayout(new BorderLayout());
		deletePanel.setLayout(new GridLayout(3, 2));

		laName.setHorizontalAlignment(JLabel.CENTER);
		laStock.setHorizontalAlignment(JLabel.CENTER);
		laUnit.setHorizontalAlignment(JLabel.CENTER);

		deletePanel.add(laName);
		deletePanel.add(tfName);

		deletePanel.add(laStock);
		deletePanel.add(tfStock);

		deletePanel.add(laUnit);
		deletePanel.add(tfUnit);

		backgroundPanel.add(deletePanel, BorderLayout.CENTER);
		backgroundPanel.add(deleteButton, BorderLayout.SOUTH);

	}

	private void initListener() {
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InventoriesDao inventoriesDao = InventoriesDao.getInstance();
				int search = inventoriesDao.productsInventorySearch(inventories);
				//원재료가 포함된 메뉴가 존재하면 삭제 불가능
				if(search > 0) {
					JOptionPane.showMessageDialog(null, "원재료와 관련된 메뉴를 먼저 삭제하세요.");
				}else {
					int result = inventoriesDao.delete(inventories);
					if (result == 1) {
						adminMain.invenNotifyUserList();
						addFrame.dispose();
						adminMain.setEnabled(true);
					} else {
						JOptionPane.showMessageDialog(null, "원재료 삭제에 실패하였습니다.");
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
package gui.admin.productsub;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;

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
import javax.swing.table.DefaultTableModel;

import dao.CategoriesDao;
import dao.ProductsDao;
import db.DBUtils;
import gui.admin.AdminMain;
import models.Categories;
import models.Inventories;
import models.Products;

public class MenuUFrame extends JFrame {
	private AdminMain adminMain;
	private JPanel panel;
	private JScrollPane leftMenuSP, rightMenuSP;
	private JComboBox categoryBox;

	private JLabel afterMenuLa, afterNameLa, afterPriceLa, afterListLa, afterListLa2, updateTitle;
	private JLabel beforeMenuLa, beforeNameLa, beforePriceLa, beforeListLa, beforeIdLa;
	private JTextField afterName, afterPriceText;
	private JTextField beforeId, beforeCate, beforeName, beforePriceText;
	private ImageIcon leftArrow;
	private JButton arrowbt, 수정버튼, deletebt, 취소버튼;
	private JTable rightTable;
	private JTable leftTable;

	private DefaultTableModel rightMenuTableModel;
	private DefaultTableModel leftMenuTableModel;

	private JScrollPane inventorySP;
	private JTable inventoryTable;
	private DefaultTableModel inventoryTableModel;

	List<Products> products;
	List<Inventories> inventories;

	String[] categories;

	public MenuUFrame(AdminMain adminMain) {
		this.adminMain = adminMain;
		init();
		initListener();
	}

	private void initData() {
		leftMenuTableModel = DBUtils.makeRowProducts(leftMenuTableModel);
		inventoryTableModel = DBUtils.makeRowInventories(inventoryTableModel);
	}

	private void init() {

		CategoriesDao categoriesDao = CategoriesDao.getInstance();
		List<Categories> a = categoriesDao.selectAll();
		categories = new String[a.size()];
		for (int i = 0; i < categories.length; i++) {
			categories[i] = a.get(i).getName();
		}

		panel = new JPanel();
		panel.setBounds(0, 0, 1200, 800);
		getContentPane().add(panel);
		panel.setLayout(null);

		getContentPane().setLayout(null);

		// left
		leftMenuTableModel = new DefaultTableModel(DBUtils.getColumnNames("products"), 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		leftMenuTableModel = DBUtils.makeRowProducts(leftMenuTableModel);

		leftTable = new JTable(leftMenuTableModel);
		leftTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		leftTable.getColumnModel().getColumn(1).setPreferredWidth(80);
		leftTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		leftTable.getColumnModel().getColumn(3).setPreferredWidth(40);
		leftTable.getColumnModel().getColumn(4).setPreferredWidth(0);
		leftTable.getColumnModel().getColumn(5).setPreferredWidth(0);
		leftMenuSP = new JScrollPane();
		leftMenuSP.setBounds(58, 294, 350, 274);
		panel.add(leftMenuSP);
		leftMenuSP.setViewportView(leftTable);

		// right
		rightMenuTableModel = new DefaultTableModel(DBUtils.getColumnNames("updateInventory"), 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		rightTable = new JTable(rightMenuTableModel);
		rightTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		rightTable.getColumnModel().getColumn(1).setPreferredWidth(80);

		rightMenuSP = new JScrollPane();
		rightMenuSP.setBounds(420, 294, 300, 274);
		panel.add(rightMenuSP);

		rightMenuSP.setViewportView(rightTable);

		// Inventory
		inventoryTableModel = new DefaultTableModel(DBUtils.getColumnNames("inventories"), 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		inventoryTableModel = DBUtils.makeRowInventories(inventoryTableModel);

		inventoryTable = new JTable(inventoryTableModel);
		inventoryTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		inventoryTable.getColumnModel().getColumn(1).setPreferredWidth(70);
		inventoryTable.getColumnModel().getColumn(2).setPreferredWidth(70);
		inventoryTable.getColumnModel().getColumn(3).setPreferredWidth(70);

		inventorySP = new JScrollPane();
		inventorySP.setBounds(841, 294, 300, 274);
		panel.add(inventorySP);

		inventorySP.setViewportView(inventoryTable);

		///////////////////////////////////////////

		///////////////////////////////////////////
		categoryBox = new JComboBox(categories);
		categoryBox.setBounds(604, 130, 116, 21);
		panel.add(categoryBox);

		beforeIdLa = new JLabel("상품번호");
		beforeIdLa.setBounds(113, 96, 60, 15);
		panel.add(beforeIdLa);

		beforeMenuLa = new JLabel("카테고리");
		beforeMenuLa.setBounds(113, 133, 60, 15);
		panel.add(beforeMenuLa);

		beforeNameLa = new JLabel("메뉴명");
		beforeNameLa.setBounds(123, 176, 50, 15);
		panel.add(beforeNameLa);

		beforePriceLa = new JLabel("가격");
		beforePriceLa.setBounds(133, 220, 40, 15);
		panel.add(beforePriceLa);

		beforeListLa = new JLabel("상품 리스트");
		beforeListLa.setBounds(58, 269, 84, 15);
		panel.add(beforeListLa);
		///////////////////////////////////////////
		afterMenuLa = new JLabel("카테고리");
		afterMenuLa.setBounds(532, 133, 60, 15);
		panel.add(afterMenuLa);

		afterNameLa = new JLabel("메뉴명");
		afterNameLa.setBounds(542, 176, 50, 15);
		panel.add(afterNameLa);

		afterPriceLa = new JLabel("가격");
		afterPriceLa.setBounds(552, 220, 40, 15);
		panel.add(afterPriceLa);

		afterListLa = new JLabel("상품 리스트");
		afterListLa.setBounds(58, 269, 84, 15);
		panel.add(afterListLa);

		afterListLa2 = new JLabel("원재료 리스트");
		afterListLa2.setBounds(420, 263, 149, 15);
		panel.add(afterListLa2);

		///////////////////////////////////////////

		updateTitle = new JLabel("상품 수정");
		updateTitle.setFont(new Font("굴림", Font.BOLD, 24));
		updateTitle.setBounds(469, 32, 116, 76);
		panel.add(updateTitle);

		afterName = new JTextField();
		afterName.setBounds(604, 173, 116, 21);
		panel.add(afterName);
		afterName.setColumns(10);

		afterPriceText = new JTextField();
		afterPriceText.setBounds(604, 217, 116, 21);
		panel.add(afterPriceText);
		afterPriceText.setColumns(10);

		leftArrow = new ImageIcon("img/reInterface.png");

		arrowbt = new JButton("arrow", leftArrow);
		arrowbt.setBounds(732, 406, 97, 52);
		panel.add(arrowbt);

		수정버튼 = new JButton("수정");
		수정버튼.setFont(new Font("굴림", Font.BOLD, 18));
		수정버튼.setBounds(469, 651, 104, 52);
		panel.add(수정버튼);

		deletebt = new JButton("삭제");
		deletebt.setBounds(657, 578, 63, 23);
		panel.add(deletebt);

		취소버튼 = new JButton("취소");
		취소버튼.setFont(new Font("굴림", Font.BOLD, 18));
		취소버튼.setBounds(665, 651, 104, 52);
		panel.add(취소버튼);

		beforeId = new JTextField();
		beforeId.setBounds(173, 93, 116, 21);
		panel.add(beforeId);
		beforeId.setColumns(10);

		beforeCate = new JTextField();
		beforeCate.setColumns(10);
		beforeCate.setBounds(173, 130, 116, 21);
		panel.add(beforeCate);

		beforeName = new JTextField();
		beforeName.setColumns(10);
		beforeName.setBounds(173, 173, 116, 21);
		panel.add(beforeName);

		beforePriceText = new JTextField();
		beforePriceText.setColumns(10);
		beforePriceText.setBounds(173, 217, 116, 21);
		panel.add(beforePriceText);

		setTitle("Lotteria ERPsystem");
		setSize(1200, 800);
		setLocationRelativeTo(null);
		setVisible(true);

		arrowbt.setEnabled(false);
		inventoryTable.setEnabled(false);

		beforeId.setEnabled(false);
		beforeCate.setEnabled(false);
		beforeName.setEnabled(false);
		beforePriceText.setEnabled(false);
	}

	private void initListener() {

		leftTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					rightMenuTableModel.setRowCount(0);
					// 왼쪽에 Text 셋팅
					beforeId.setText(leftTable.getValueAt(leftTable.getSelectedRow(), 0).toString());
					beforeCate.setText(leftTable.getValueAt(leftTable.getSelectedRow(), 1).toString());
					beforeName.setText(leftTable.getValueAt(leftTable.getSelectedRow(), 2).toString());
					beforePriceText.setText(leftTable.getValueAt(leftTable.getSelectedRow(), 3).toString());
					// Split을 위해서 값을 저장
					String tempIngredient = (String) leftTable.getValueAt(leftTable.getSelectedRow(), 4);
					String tempQuantity = (String) leftTable.getValueAt(leftTable.getSelectedRow(), 5);
					// Split
					String[] temp, temp2;
					temp = tempIngredient.split(", ");
					temp2 = tempQuantity.split(", ");
					// 해당 값들을 우측에 보여줌
					for (int i = 0; i < temp2.length; i++) {
						Vector<Object> row = new Vector<>();
						row.addElement(temp[i]);
						row.addElement(temp2[i]);
						for (int j = 0; j < inventoryTable.getRowCount(); j++) {
							if (temp[i].trim().equals(inventoryTable.getValueAt(j, 1).toString())) {
								row.addElement(inventoryTable.getValueAt(j, 3).toString());
							}
						}
						rightMenuTableModel.addRow(row);
					}
					inventoryTable.setEnabled(true);
					arrowbt.setEnabled(true);
				}
			}
		});

		deletebt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (rightTable.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "삭제항목을 선택해주세요");
					return;
				}

				int result = JOptionPane.showConfirmDialog(null, "해당항목을 삭제하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					rightMenuTableModel.removeRow(rightTable.getSelectedRow());
				} else {
					return;
				}
			}
		});

		// 상품에 들어갈 List에 넘길 때
		arrowbt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// 단위
				String unit = "";
				String str = "";
				String result = "";

				if (inventoryTable.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "선택항목이 없습니다.");
					return;
				} else {
					unit = inventoryTable.getValueAt(inventoryTable.getSelectedRow(), 3).toString();
					result = JOptionPane.showInputDialog("재료양을 입력하시오 (단위 " + unit + ")");
					if (result == null) {
						return;
					}
					if (result.equals("")) {
						JOptionPane.showMessageDialog(null, "값을 입력해주세요.");
					} else {
						// 5.16
						Vector<Object> row = new Vector<>();
						str = (String) inventoryTable.getValueAt(inventoryTable.getSelectedRow(), 1);
						boolean check = false;
						if (rightTable.getRowCount() == 0) {
							System.out.println("처음입력");
							row.addElement(str);
							row.addElement(result);
							row.addElement(unit);
							rightMenuTableModel.addRow(row);
						} else if (rightTable.getRowCount() > 0) {
							// 두번째 입력부턴 입력된 값만큼 검사
							System.out.println("처음입력2");
							for (int i = 0; i < rightTable.getRowCount(); i++) {
								if (str.equals(rightTable.getValueAt(i, 0).toString())) {
									check = false;
									JOptionPane.showMessageDialog(null, "해당 재료는 할당되어있습니다.");
									break;
								} else {
									check = true;
								}
							}
							// 중복이 없다면 실행
							if (check) {
								row.addElement(str);
								row.addElement(result);
								row.addElement(unit);
								rightMenuTableModel.addRow(row);
							}

						}
					}
				}

			}
		});

		수정버튼.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (afterName.getText().length() == 0 || afterPriceText.getText().length() == 0
						|| rightTable.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "입력을 확인 하세요.");
				} else {
					StringBuilder ingredient = new StringBuilder();
					StringBuilder quantity = new StringBuilder();

					for (int i = 0; i < rightTable.getRowCount(); i++) {
						if (i == rightTable.getRowCount() - 1) {
							ingredient.append(rightTable.getValueAt(i, 0));
							quantity.append(rightTable.getValueAt(i, 1));

						} else {
							ingredient.append(rightTable.getValueAt(i, 0) + ", ");
							quantity.append(rightTable.getValueAt(i, 1) + ", ");
						}
					}
					// 카테고리 id 구해오기
					CategoriesDao categoriesDao = CategoriesDao.getInstance();
					int a = categoriesDao.selectOne(categoryBox.getSelectedItem().toString());

					// Dao에 products 보내기
					Products products = new Products();
					products.setId(Integer.parseInt(beforeId.getText()));
					products.setCategory_id(a);
					products.setName(afterName.getText());
					products.setPrice(Integer.parseInt(afterPriceText.getText()));
					products.setIngredient(ingredient.toString());
					products.setQuantity(quantity.toString());

					ProductsDao productsDao = ProductsDao.getInstance();
					int result = productsDao.update(products);
					if (result == -1) {
						JOptionPane.showMessageDialog(null, "상품 수정 실패!");
					} else {
						JOptionPane.showMessageDialog(null, "상품 수정 성공!");
					}
					leftMenuTableModel.setRowCount(0);
					inventoryTableModel.setRowCount(0);
					initData();
				}
			}
		});

		// 기존창 프리징 풀면서 현재창 닫기
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				adminMain.setEnabled(true);
				adminMain.menuNotifyProductsList();
			}
		});

		// 취소 버튼 눌렀을때
		취소버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminMain.setEnabled(true);
				adminMain.menuNotifyProductsList();
				dispose();
			}
		});
	}
}
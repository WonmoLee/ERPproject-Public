package gui.admin.productsub;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import dao.CategoriesDao;
import dao.ProductsDao;
import db.DBUtils;
import gui.admin.AdminMain;
import models.Categories;
import models.Inventories;
import models.Products;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTable;

public class MenuCFrame extends JFrame {
	private JFrame adminMain;
	private JPanel panel;
	private JScrollPane AllList, recipeSP;
	private JComboBox ListBox, CategoryBox, opcomboBox;
	private JLabel Label1, Label2, Label3, Label4, Label5, Label6;
	private JTextField searchTF, product_nameTF, priceTF;
	private ImageIcon rightArrow, searchButton;
	private JButton searchbt, arrowbt, addbt, deletebt, cancelbt;
	private JList<String> allList;
	private JTable recipeTable;
	private String[] categories;
	
	// 5.16추가
	private DefaultTableModel dtModel;
	private DefaultListModel<String> dt1model;
	//
	private List<Inventories> inventories;

	public MenuCFrame(AdminMain adminMain) {
		this.adminMain = adminMain;
		
		CategoriesDao categoriesDao = CategoriesDao.getInstance();
		List<Categories> a = categoriesDao.selectAll();		
		categories = new String[a.size()];
		for (int i = 0; i < categories.length; i++) {
			categories[i] = a.get(i).getName();
		}
		
		init();
		initListener();
	}

	private void init() {

		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 884, 761);
		getContentPane().add(panel);
		panel.setLayout(null);

		AllList = new JScrollPane();
		AllList.setBounds(58, 294, 305, 274);
		panel.add(AllList);

		dt1model = new DefaultListModel<String>();

		dt1model = DBUtils.makeRowInvenName(dt1model);

		allList = new JList<String>(dt1model);
		AllList.setViewportView(allList);

		recipeSP = new JScrollPane();
		recipeSP.setBounds(519, 294, 305, 274);
		panel.add(recipeSP);

		// 메뉴관리 - 메뉴 Main
		dtModel = new DefaultTableModel(DBUtils.getColumnNames("inventories"), 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		//////////////////

		recipeTable = new JTable(dtModel);
		recipeTable.getColumnModel().getColumn(0).setPreferredWidth(60);
		recipeTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		recipeTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		recipeTable.getColumnModel().getColumn(3).setPreferredWidth(40);
		recipeSP.setViewportView(recipeTable);

		ListBox = new JComboBox(new String[] { "전체", "빵", "패티", "야채", "과일", "소스" });
		AllList.setColumnHeaderView(ListBox);

		CategoryBox = new JComboBox(categories);
		CategoryBox.setBounds(615, 130, 116, 21);
		panel.add(CategoryBox);

		Label1 = new JLabel("메뉴 카테고리");
		Label1.setBounds(519, 133, 84, 15);
		panel.add(Label1);

		Label2 = new JLabel("메뉴명");
		Label2.setBounds(560, 176, 43, 15);
		panel.add(Label2);

		Label3 = new JLabel("가격");
		Label3.setBounds(574, 220, 29, 15);
		panel.add(Label3);

		Label4 = new JLabel("총 원재료 List");
		Label4.setBounds(58, 269, 84, 15);
		panel.add(Label4);

		Label5 = new JLabel("상품에 들어갈 원재료 List");
		Label5.setBounds(520, 269, 149, 15);
		panel.add(Label5);

		Label6 = new JLabel("상품 추가");
		Label6.setFont(new Font("굴림", Font.BOLD, 24));
		Label6.setBounds(393, 34, 116, 76);
		panel.add(Label6);

		Label6 = new JLabel("원재료 찾기");
		Label6.setBounds(58, 192, 69, 15);
		panel.add(Label6);

		searchTF = new JTextField();
		searchTF.setBounds(58, 217, 169, 21);
		panel.add(searchTF);
		searchTF.setColumns(10);

		product_nameTF = new JTextField();
		product_nameTF.setBounds(615, 173, 116, 21);
		panel.add(product_nameTF);
		product_nameTF.setColumns(10);

		priceTF = new JTextField();
		priceTF.setBounds(615, 217, 116, 21);
		panel.add(priceTF);
		priceTF.setColumns(10);

		rightArrow = new ImageIcon("img/interface.png");

		searchbt = new JButton("검색");
		searchbt.setBounds(239, 217, 63, 21);
		panel.add(searchbt);

		arrowbt = new JButton("arrow", rightArrow);
		arrowbt.setBounds(393, 400, 97, 52);
		panel.add(arrowbt);

		addbt = new JButton("추가");
		addbt.setFont(new Font("굴림", Font.BOLD, 18));
		addbt.setBounds(303, 651, 104, 52);
		panel.add(addbt);

		deletebt = new JButton("삭제");
		deletebt.setBounds(761, 578, 63, 23);
		panel.add(deletebt);

		cancelbt = new JButton("취소");
		cancelbt.setFont(new Font("굴림", Font.BOLD, 18));
		cancelbt.setBounds(474, 651, 104, 52);
		panel.add(cancelbt);

		setTitle("Lotteria ERPsystem");
		setSize(900, 800);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initListener() {

		searchbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String keyword = searchTF.getText();
				dt1model.clear();
				dt1model = DBUtils.makeRowInvensearch(dt1model, keyword);
			}
		});

		deletebt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (recipeTable.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "삭제항목을 선택해주세요");
					return;
				}

				int result = JOptionPane.showConfirmDialog(null, "해당항목을 삭제하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					dtModel.removeRow(recipeTable.getSelectedRow());
				} else {
					return;
				}
			}
		});

		// 상품에 들어갈 List에 넘길 때
		arrowbt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// 정규표현식으로 분해
				//// ex) 빵 (개) -> temp[0] = 빵, m = 개
				String x = allList.getSelectedValue();
				String y = "";
				String[] temp = x.split("\\((.*)\\)");
				System.out.println(temp[0]);
				Matcher m = Pattern.compile("\\((.*)\\)").matcher(x);
				while (m.find()) {
					y = m.group(1);
				}
				String unit = y;
				String str = temp[0];
				/////////////////////////////////////
				// 밑에 str = allList.getSelectedValue(); 주석처리해야함

				String result = "";
				int selection = allList.getSelectedIndex();
				if (selection == -1) {
					JOptionPane.showMessageDialog(null, "선택항목이 없습니다.");
					return;
				} else {
					result = JOptionPane.showInputDialog("재료양을 입력하시오 (단위 " + unit + ")");
					if (result == null) {
						return;
					}
					if (result.equals("")) {
						JOptionPane.showMessageDialog(null, "값을 입력해주세요.");
					} else {
						// 5.16
						Vector<Object> row = new Vector<>();
//						str = allList.getSelectedValue();
						boolean check = false;
						if (recipeTable.getRowCount() == 0) {
							row.add(recipeTable.getRowCount() + 1);
							row.addElement(str);
							row.addElement(result);
							row.addElement(unit);
							dtModel.addRow(row);
						} else if (recipeTable.getRowCount() > 0) {
							// 두번째 입력부턴 입력된 값만큼 검사
							for (int i = 0; i < recipeTable.getRowCount(); i++) {
								if (str.equals(recipeTable.getValueAt(i, 1).toString())) {
									check = false;
									JOptionPane.showMessageDialog(null, "해당 재료는 할당되어있습니다.");
									break;
								} else {
									check = true;
								}
							}
							// 중복이 없다면 실행
							if (check) {
								row.add(recipeTable.getRowCount() + 1);
								row.addElement(str);
								row.addElement(result);
								row.addElement(unit);
								dtModel.addRow(row);
							}

						}
					}
				}
			}
		});

		addbt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int result = JOptionPane.showConfirmDialog(null, "메뉴를 추가하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.NO_OPTION) {
					return;
				} else {
					// 상품번호 시퀀스
					String name = product_nameTF.getText();
					String price = priceTF.getText();
					String category = CategoryBox.getSelectedItem().toString();
					// category id 불러오기
					CategoriesDao categoriesDao = CategoriesDao.getInstance();
					int c_id = categoriesDao.selectOne(category);

					StringBuilder sb1 = new StringBuilder();
					StringBuilder sb2 = new StringBuilder();
					for (int i = 0; i < dtModel.getRowCount(); i++) {
						System.out.println("for 반복문 : " + i);
						if (i == dtModel.getRowCount() - 1) {
							sb1.append(dtModel.getValueAt(i, 1).toString());
							sb2.append(dtModel.getValueAt(i, 2).toString());
						} else {
							sb1.append(dtModel.getValueAt(i, 1) + ", ");
							sb2.append(dtModel.getValueAt(i, 2) + ", ");
						}
					}

					Products products = new Products(0, c_id, name, Integer.parseInt(price), sb1.toString(),
							sb2.toString());
					ProductsDao productsDao = ProductsDao.getInstance();
					productsDao.insertNewProducts(products);
				}
			}
		});

		// 기존창 프리징 풀면서 현재창 닫기
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				adminMain.setEnabled(true);
			}
		});

		// 취소 버튼 눌렀을때
		cancelbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminMain.setEnabled(true);
				dispose();
			}
		});
	}
}

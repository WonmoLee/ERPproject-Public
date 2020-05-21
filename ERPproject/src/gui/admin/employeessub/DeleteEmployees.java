package gui.admin.employeessub;

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

import dao.EmployeesDao;
import dao.InventoriesDao;
import dao.ProductsDao;
import gui.admin.AdminMain;
import models.Employees;
import models.Inventories;
import models.Products;

public class DeleteEmployees extends JFrame {
	private JFrame deleteFrame = this;
	private AdminMain adminMain;
	private Container backgroundPanel;
	// before
	private JLabel 이름라벨, 주민번호라벨, 전화번호라벨, 입사일라벨;
	private JTextField 이름입력창, 주민번호입력창, 전화번호입력창, 입사일입력창;

	private JPanel 삭제패널;
	private JButton 삭제버튼;
	// 가져온값
	Employees employees;

	public DeleteEmployees(AdminMain adminMain, Employees employees) {
		this.adminMain = adminMain;
		this.employees = employees;
		initObject();
		initDesign();
		initListener();
		setResizable(false);
		setVisible(true);
	}

	private void initObject() {
		삭제패널 = new JPanel();
		backgroundPanel = getContentPane();

		이름라벨 = new JLabel("이름");
		주민번호라벨 = new JLabel("주민번호");
		전화번호라벨 = new JLabel("폰번호");
		입사일라벨 = new JLabel("입사일");

		이름입력창 = new JTextField(employees.getName());
		주민번호입력창 = new JTextField(employees.getJumin());
		전화번호입력창 = new JTextField(employees.getPhone());
		입사일입력창 = new JTextField(employees.getHire_date());

		이름입력창.setEnabled(false);
		주민번호입력창.setEnabled(false);
		전화번호입력창.setEnabled(false);
		입사일입력창.setEnabled(false);

		삭제버튼 = new JButton("삭제하기");
	}

	private void initDesign() {
		setTitle("ERPsystem");
		setSize(400, 200);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		backgroundPanel.setLayout(new BorderLayout());
		삭제패널.setLayout(new GridLayout(4, 2));

		이름라벨.setHorizontalAlignment(JLabel.CENTER);
		주민번호라벨.setHorizontalAlignment(JLabel.CENTER);
		전화번호라벨.setHorizontalAlignment(JLabel.CENTER);
		입사일라벨.setHorizontalAlignment(JLabel.CENTER);

		삭제패널.add(이름라벨);
		삭제패널.add(이름입력창);

		삭제패널.add(주민번호라벨);
		삭제패널.add(주민번호입력창);

		삭제패널.add(전화번호라벨);
		삭제패널.add(전화번호입력창);
		
		삭제패널.add(입사일라벨);
		삭제패널.add(입사일입력창);

		backgroundPanel.add(삭제패널, BorderLayout.CENTER);
		backgroundPanel.add(삭제버튼, BorderLayout.SOUTH);

	}

	private void initListener() {
		삭제버튼.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EmployeesDao employeesDao = EmployeesDao.getInstance();
				int result = employeesDao.deleteEntry(employees);
				if (result == 1) {
					JOptionPane.showMessageDialog(null, "해당 직원 정보를 삭제하였습니다.");
					adminMain.notifyEmployeesList();
					deleteFrame.dispose();
					adminMain.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "직원 삭제에 실패하였습니다.");
				}
			}
		});

		deleteFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				adminMain.setEnabled(true);
				adminMain.setAlwaysOnTop(true);
				adminMain.setAlwaysOnTop(false);
			}
		});
	}
}

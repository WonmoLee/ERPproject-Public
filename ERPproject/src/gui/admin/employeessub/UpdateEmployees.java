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

public class UpdateEmployees extends JFrame {
	private JFrame updateFrame = this;
	private AdminMain adminMain;
	private Container backgroundPanel;
	// before
	private JLabel 이름라벨, 주민번호라벨, 전화번호라벨, 입사일라벨;
	private JTextField 이름입력창, 주민번호입력창, 전화번호입력창, 입사일입력창;

	private JPanel 수정패널;
	private JButton 수정버튼;
	// 가져온값
	Employees employees;

	public UpdateEmployees(AdminMain adminMain, Employees employees) {
		this.adminMain = adminMain;
		this.employees = employees;
		initObject();
		initDesign();
		initListener();
		setResizable(false);
		setVisible(true);
	}

	private void initObject() {
		수정패널 = new JPanel();
		backgroundPanel = getContentPane();

		이름라벨 = new JLabel("이름");
		주민번호라벨 = new JLabel("주민번호");
		전화번호라벨 = new JLabel("폰번호");
		입사일라벨 = new JLabel("입사일");

		이름입력창 = new JTextField(employees.getName());
		주민번호입력창 = new JTextField(employees.getJumin());
		전화번호입력창 = new JTextField(employees.getPhone());
		입사일입력창 = new JTextField(employees.getHire_date());

//		tfName.setEnabled(false);
//		tfJumin.setEnabled(false);
//		tfPhone.setEnabled(false);
//		tfHire_date.setEnabled(false);

		수정버튼 = new JButton("수정하기");
	}

	private void initDesign() {
		setTitle("ERPsystem");
		setSize(400, 200);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		backgroundPanel.setLayout(new BorderLayout());
		수정패널.setLayout(new GridLayout(4, 2));

		이름라벨.setHorizontalAlignment(JLabel.CENTER);
		주민번호라벨.setHorizontalAlignment(JLabel.CENTER);
		전화번호라벨.setHorizontalAlignment(JLabel.CENTER);
		입사일라벨.setHorizontalAlignment(JLabel.CENTER);

		수정패널.add(이름라벨);
		수정패널.add(이름입력창);

		수정패널.add(주민번호라벨);
		수정패널.add(주민번호입력창);

		수정패널.add(전화번호라벨);
		수정패널.add(전화번호입력창);
		
		수정패널.add(입사일라벨);
		수정패널.add(입사일입력창);

		backgroundPanel.add(수정패널, BorderLayout.CENTER);
		backgroundPanel.add(수정버튼, BorderLayout.SOUTH);

	}

	private void initListener() {
		수정버튼.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String name = 이름입력창.getText();
				String jumin = 주민번호입력창.getText();
				String phone = 전화번호입력창.getText();
				String hire_date = 입사일입력창.getText();
				
				Employees emp = Employees.builder()
						.id(employees.getId())
						.name(name)
						.jumin(jumin)
						.phone(phone)
						.hire_date(hire_date)
						.build();
				
				
				EmployeesDao employeesDao = EmployeesDao.getInstance();
				int result = employeesDao.updateEntry(emp);
				if (result == 1) {
					JOptionPane.showMessageDialog(null, "직원 정보를 수정하였습니다.");
					adminMain.notifyEmployeesList();
					updateFrame.dispose();
					adminMain.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "직원 정보 수정에 실패하였습니다.");
				}
			}
		});

		updateFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				adminMain.setEnabled(true);
				adminMain.setAlwaysOnTop(true);
				adminMain.setAlwaysOnTop(false);
			}
		});
	}
}

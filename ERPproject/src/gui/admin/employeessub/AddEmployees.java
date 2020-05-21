package gui.admin.employeessub;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.Timestamp;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.EmployeesDao;
import dao.InventoriesDao;
import gui.admin.AdminMain;
import models.Employees;
import models.Inventories;
import oracle.sql.TIMESTAMP;

public class AddEmployees extends JFrame {
	private JFrame addFrame = this;
	private AdminMain adminMain;
	private Container backgroundPanel;
	private JLabel 이름라벨, 주민번호라벨, 전화번호라벨, 입사일라벨;
	private JTextField 이름입력창, 주민번호입력창, 전화번호입력창, 입사일입력창;
	private JPanel 추가패널;
	private JButton 추가버튼;

	
	public AddEmployees(AdminMain adminMain) {
		this.adminMain = adminMain;
		initObject();
		initDesign();
		initListener();
		setResizable(false);
		setVisible(true);
	}

	private void initObject() {
		추가패널 = new JPanel();
		backgroundPanel = getContentPane();
		이름라벨 = new JLabel("이름");
		주민번호라벨 = new JLabel("주민등록번호");
		전화번호라벨 = new JLabel("연락처");
		입사일라벨 = new JLabel("입사일");
		
		이름입력창 = new JTextField(20);
		주민번호입력창 = new JTextField(20);
		전화번호입력창 = new JTextField(20);
		입사일입력창 = new JTextField();
		
		추가버튼 = new JButton("추가하기");
	}

	private void initDesign() {
		setTitle("ERPsystem");
		setSize(300, 200);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		backgroundPanel.setLayout(new BorderLayout());

		추가패널.setLayout(new GridLayout(4, 2));
		
		이름라벨.setHorizontalAlignment(JLabel.CENTER);
		주민번호라벨.setHorizontalAlignment(JLabel.CENTER);
		전화번호라벨.setHorizontalAlignment(JLabel.CENTER);
		입사일라벨.setHorizontalAlignment(JLabel.CENTER);

		추가패널.add(이름라벨);
		추가패널.add(이름입력창);
		
		추가패널.add(주민번호라벨);
		추가패널.add(주민번호입력창);

		추가패널.add(전화번호라벨);
		추가패널.add(전화번호입력창);

		추가패널.add(입사일라벨);
		추가패널.add(입사일입력창);

		backgroundPanel.add(추가패널, BorderLayout.CENTER);
		backgroundPanel.add(추가버튼, BorderLayout.SOUTH);
	}

	private void initListener() {
		추가버튼.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = 이름입력창.getText();
				String jumin = 주민번호입력창.getText();
				String phone = 전화번호입력창.getText();
				// String hire_date = "20200503";
				String hire_date = 입사일입력창.getText();
				
				if(name.length()==0||jumin.length()==0||phone.length()==0) {
					JOptionPane.showMessageDialog(null, "입력한 값이 잘못되었습니다.");
				}else {
					Employees employees = new Employees(
							0,
							name, 
							jumin, 
							phone, 
							hire_date);
					
					EmployeesDao employeesDao = EmployeesDao.getInstance();
					int result = employeesDao.addEntry(employees);
					if(result == 1) {
						adminMain.notifyEmployeesList();
						addFrame.dispose();
						adminMain.setEnabled(true);
					}else {
						JOptionPane.showMessageDialog(null, "직원 추가에 실패하였습니다.");
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

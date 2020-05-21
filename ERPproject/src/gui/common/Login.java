package gui.common;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dao.UsersDao;
import db.DBconnection;
import gui.admin.AdminMain;
import gui.user.KioskMain;
import models.Users;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JPasswordField;

public class Login extends JFrame {
	private JTextField idField;
	private JPasswordField passwordField;
	private JPanel panel;
	private JLabel loginlb, idlb, pwlb, idfdlb, pwfdlb, signuplb, nlb1, nlb2;
	private JButton loginButton;

	public Login() {
		init();

		idfdlb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(panel, "관리자에게 문의하세요.", "오류", JOptionPane.ERROR_MESSAGE);
			}
		});

		pwfdlb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(panel, "관리자에게 문의하세요.", "오류", JOptionPane.ERROR_MESSAGE);
			}
		});

		signuplb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new Signup();
			}
		});

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userId = idField.getText();
				String password = String.valueOf(passwordField.getPassword());
				if (userId.length() == 0 && password.length() == 0) {
					JOptionPane.showMessageDialog(null, "아이디와 패스워드를 입력해주세요.");
					return;
				} else if (userId.length() == 0 && password.length() != 0) {
					JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.");
					return;
				} else if (password.length() == 0 && userId.length() != 0) {
					JOptionPane.showMessageDialog(null, "패스워드를 입력해주세요.");
					return;
				}
				//
				UsersDao userDao = UsersDao.getInstance();
				Users u = userDao.login(userId, password);

				if (u == null) {
					JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호가 일치하지 않습니다.");
				} else if (u.getAdmin_at() == 'Y') {
					new AdminMain();
					dispose();
					
				} else /* (u.getAdmin_at() == 'N') */ {	// 'Y' 이외 값이면 무조건 일반 사용자인 걸로
					new KioskMain();
					dispose();
				}
			}
		});
	}

	private void init() {

		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 484, 391);
		getContentPane().add(panel);
		panel.setLayout(null);

		loginlb = new JLabel("Login");
		loginlb.setFont(new Font("굴림", Font.PLAIN, 36));
		loginlb.setBounds(181, 46, 122, 57);
		panel.add(loginlb);

		idlb = new JLabel("ID");
		idlb.setFont(new Font("굴림", Font.PLAIN, 18));
		idlb.setBounds(172, 118, 21, 32);
		panel.add(idlb);

		idField = new JTextField();
		idField.setBounds(200, 123, 131, 27);
		panel.add(idField);
		idField.setColumns(10);

		idfdlb = new JLabel("아이디 찾기");
		idfdlb.setBounds(106, 228, 68, 15);
		panel.add(idfdlb);

		pwfdlb = new JLabel("비밀번호 찾기");
		pwfdlb.setBounds(200, 228, 81, 15);
		panel.add(pwfdlb);

		signuplb = new JLabel("회원가입");
		signuplb.setBounds(305, 228, 68, 15);
		panel.add(signuplb);

		pwlb = new JLabel("Password");
		pwlb.setFont(new Font("굴림", Font.PLAIN, 18));
		pwlb.setBounds(105, 165, 88, 32);
		panel.add(pwlb);

		nlb1 = new JLabel("|");
		nlb1.setBounds(181, 217, 12, 37);
		panel.add(nlb1);

		nlb2 = new JLabel("|");
		nlb2.setBounds(291, 228, 12, 15);
		panel.add(nlb2);

		loginButton = new JButton("로그인");
		loginButton.setFont(new Font("굴림", Font.PLAIN, 24));
		loginButton.setBounds(160, 275, 162, 63);
		panel.add(loginButton);

		passwordField = new JPasswordField();
		passwordField.setBounds(200, 170, 131, 27);
		panel.add(passwordField);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Lotteria");
		setSize(500, 430);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
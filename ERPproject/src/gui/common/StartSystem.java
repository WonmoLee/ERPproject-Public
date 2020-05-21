package gui.common;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import gui.common.Login;
import gui.common.Signup;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartSystem extends JFrame {
	
	private JPanel panel;
	private JLabel lblNewLabel;
	private JButton loginbt, signupbt;
	
	public StartSystem() {
		init();
		
		loginbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Login();
			}
		});
		
		signupbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Signup();
			}
		});
	}
	
	private void init() {
		
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 684, 530);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		lblNewLabel = new JLabel("ERP System");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 46));
		lblNewLabel.setBounds(196, 62, 290, 66);
		panel.add(lblNewLabel);
		
		loginbt = new JButton("로그인");
		loginbt.setFont(new Font("굴림", Font.PLAIN, 33));
		loginbt.setBounds(72, 174, 237, 243);
		panel.add(loginbt);
		
		signupbt = new JButton("회원가입");
		signupbt.setFont(new Font("굴림", Font.PLAIN, 33));
		signupbt.setBounds(362, 174, 237, 243);
		panel.add(signupbt);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Lotteria");
		setSize(700,530);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public static void main(String[] args) {
		new StartSystem();
	}
}

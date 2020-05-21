package gui.common;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTextField;

import db.DBconnection;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;

public class Signup extends JFrame {
	private JPanel panel;
	private JLabel signuplb, idlb, pwlb, pwcflb, nt1lb, rrnlb, nt2lb, phlb, emlb, nt3lb, nt4lb, nt5lb, nt6lb;
	private JButton idcfbt, signupbt, cancelbt;
	private JTextField idField, rrnField, phField, emField;
	private JPasswordField pwField, pwcfField;
	private JCheckBox agreecb;
	private int checkedId;
	
	
	public Signup() {
		init();
		
		idcfbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				final String SQL = "SELECT * FROM USERS WHERE USER_ID = ?";
				
				Connection conn = DBconnection.getConnection();
				PreparedStatement pstmt;
				ResultSet rs;
				
				String id = idField.getText();
				if (id.length() == 0) {
					JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.");
					return;
				}
				try {
					pstmt = conn.prepareStatement(SQL);
					pstmt.setString(1, id);
					rs = pstmt.executeQuery(); //이부분 이해안감
					
					if (rs.next()) {
						JOptionPane.showMessageDialog(null, "이미 사용중인 아이디입니다.");
					} else {
						JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다.");
					}
					conn.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				
			}
		});
		
		agreecb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String consent = "[필수] 개인정보 수집 및 이용동의\r\n" + "\r\n" + "아래의 목적으로 개인정보를 수집 및 이용합니다\r\n" + "\r\n"
							   + "1. 목적 : 개인 식별, 프로그램 사용\r\n" + "2. 항목 : 아이디, 주민등록번호, 연락처\r\n"
							   + "3. 보유 기간 : 회원 탈퇴 후 1년까지 보유\r\n" + "4. 개인정보의 수집 및 이용에 대한 동의를 거부할 수 있으나, 프로그램 사용이 제한됩니다.";
				int result = JOptionPane.showConfirmDialog(null, consent, "알림", JOptionPane.YES_NO_OPTION);
				
				if (result == JOptionPane.YES_OPTION) {
					agreecb.setSelected(true);
				} else if (result == JOptionPane.NO_OPTION) {
					agreecb.setSelected(false);
				}
			}
		});
		
		signupbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				final String SQL2 = "INSERT INTO USERS (ID, USER_ID, PASSWORD, JUMIN, PHONE, EMAIL, AGREE_WT, ADMIN_AT, SIGNUP_DATE) VALUES (USER_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?,'N', SYSDATE)";
				
				String id2 = idField.getText();
				if (id2.length() < 8) {
					idField.setText("");
				}
				
				String spw = "";
				char[] pw = pwField.getPassword();
				for(char cha : pw) {
					spw += cha;
				}
				if (spw.length() < 8) {
					pwField.setText("");
				}
				
				String spwcf = "";
				char[] pwcf = pwcfField.getPassword();
				for(char chacf : pwcf) {
					spwcf += chacf;
				}
				if (!(spw.equals(spwcf))) {
					pwField.setText("");
					pwcfField.setText("");
				} else {
					pwcfField.setText("");
				}
				
				String reg = "^\\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|[3][01])\\-[1-4][0-9]{6}$";
				String rrn = rrnField.getText();
				if (rrn.length() != 14) {
					rrnField.setText("");
				} else if (!(rrn.matches(reg))) {
					rrnField.setText("");
				}
				
				String regph = "^\\d{3}-\\d{3,4}-\\d{4}$";
				String phn = phField.getText();
				if (!(phn.length() == 12 || phn.length() == 13)) {
					phField.setText("");
				} else if (!(phn.matches(regph))) {
					phField.setText("");
				}
				
				String regem = "^[_0-9a-zA-Z-]+@[0-9a-zA-Z-]+(.[_0-9a-zA-Z-]+)*$"; 
				String email = emField.getText();
				if (!(email.matches(regem))) {
					emField.setText("");
				}
				
				String agreewt = "N";
				if (agreecb.isSelected() == true) 
					agreewt = "Y";
				
				
				if (id2.length() >= 8 && checkedId == 0 && spw.length() >= 8 && spw.equals(spwcf)
						&& rrn.length() == 14 && rrn.matches(reg) 
						&& (phn.length() == 12 || phn.length() == 13)
						&& phn.matches(regph) && agreewt == "Y") {
					
					Connection conn = DBconnection.getConnection();
					PreparedStatement pstmt;
					
					int returnCnt = 0;
					try {
						pstmt = conn.prepareStatement(SQL2);
						pstmt.setString(1, id2);
						pstmt.setString(2, spw);
						pstmt.setString(3, rrn);
						pstmt.setString(4, phn);
						pstmt.setString(5, email);
						pstmt.setString(6, agreewt);
						
						returnCnt = pstmt.executeUpdate();
						conn.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
					if (returnCnt == 1) {
						JOptionPane.showMessageDialog(null, "회원가입완료");
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "회원가입 실패, 다시 시도하세요.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "조건을 충족시키세요.\r\n충족되지않은 항목은 초기화됩니다.");
				}
			}
		});
		
		cancelbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	private void init() {
		
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 484, 661);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		signuplb = new JLabel("회원가입");
		signuplb.setFont(new Font("굴림", Font.PLAIN, 30));
		signuplb.setBounds(178, 33, 120, 56);
		panel.add(signuplb);
		
		idlb = new JLabel("*아이디");
		idlb.setFont(new Font("굴림", Font.PLAIN, 16));
		idlb.setBounds(105, 98, 67, 31);
		panel.add(idlb);
		
		idField = new JTextField();
		idField.setBounds(105, 124, 208, 29);
		panel.add(idField);
		idField.setColumns(10);
		
		pwlb = new JLabel("*비밀번호");
		pwlb.setFont(new Font("굴림", Font.PLAIN, 16));
		pwlb.setBounds(105, 164, 80, 31);
		panel.add(pwlb);
		
		pwField = new JPasswordField();
		pwField.setBounds(105, 193, 208, 31);
		panel.add(pwField);
		
		pwcflb = new JLabel("*비밀번호 확인");
		pwcflb.setFont(new Font("굴림", Font.PLAIN, 16));
		pwcflb.setBounds(105, 228, 108, 31);
		panel.add(pwcflb);
		
		pwcfField = new JPasswordField();
		pwcfField.setBounds(105, 253, 208, 31);
		panel.add(pwcfField);
		
		nt1lb = new JLabel("'*'는 필수입력항목");
		nt1lb.setForeground(Color.RED);
		nt1lb.setBounds(304, 86, 114, 15);
		panel.add(nt1lb);
		
		rrnlb = new JLabel("*주민등록번호");
		rrnlb.setFont(new Font("굴림", Font.PLAIN, 16));
		rrnlb.setBounds(105, 294, 108, 31);
		panel.add(rrnlb);
		
		rrnField = new JTextField();
		rrnField.setColumns(10);
		rrnField.setBounds(105, 320, 246, 29);
		panel.add(rrnField);
		
		nt2lb = new JLabel("('-'포함해서 입력하세요)");
		nt2lb.setBounds(210, 303, 141, 15);
		panel.add(nt2lb);
		
		phlb = new JLabel("*연락처");
		phlb.setFont(new Font("굴림", Font.PLAIN, 16));
		phlb.setBounds(105, 359, 55, 31);
		panel.add(phlb);
		
		phField = new JTextField();
		phField.setColumns(10);
		phField.setBounds(105, 385, 246, 29);
		panel.add(phField);
		
		emlb = new JLabel("이메일");
		emlb.setFont(new Font("굴림", Font.PLAIN, 16));
		emlb.setBounds(105, 424, 55, 31);
		panel.add(emlb);
		
		emField = new JTextField();
		emField.setColumns(10);
		emField.setBounds(105, 452, 246, 29);
		panel.add(emField);
		
		nt3lb = new JLabel("('-'포함 12자리 혹은 13자리 입력)");
		nt3lb.setBounds(161, 368, 190, 15);
		panel.add(nt3lb);
		
		idcfbt = new JButton("중복확인");
		idcfbt.setBounds(321, 130, 97, 23);
		panel.add(idcfbt);
		
		agreecb= new JCheckBox("개인정보 수집 및 이용에 동의해주세요");
		agreecb.setBounds(105, 511, 250, 31);
		panel.add(agreecb);
		
		signupbt = new JButton("가입하기");
		signupbt.setBounds(105, 563, 108, 42);
		panel.add(signupbt);
		
		cancelbt = new JButton("취소");
		cancelbt.setBounds(264, 563, 108, 42);
		panel.add(cancelbt);
		
		nt4lb = new JLabel("*필수");
		nt4lb.setForeground(Color.RED);
		nt4lb.setBounds(105, 492, 67, 23);
		panel.add(nt4lb);
		
		nt5lb = new JLabel("(*8자리이상)");
		nt5lb.setForeground(Color.RED);
		nt5lb.setBounds(178, 173, 80, 15);
		panel.add(nt5lb);
		
		nt6lb = new JLabel("(*8자리이상)");
		nt6lb.setForeground(Color.RED);
		nt6lb.setBounds(162, 107, 80, 15);
		panel.add(nt6lb);
		
		setTitle("Lotteria");
		setSize(500,700);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}

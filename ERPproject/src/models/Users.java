package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Users {
	private int id;
	private String user_id;
	private String password;
	private String jumin;
	private String phone;
	private String email;
	private char agree_wt;
	private char admin_at;
	private String signup_date;
}

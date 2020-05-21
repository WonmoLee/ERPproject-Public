package models;

import lombok.Builder;
import lombok.Data;

@Data
public class Employees {
	private int id;
	private String name;
	private String jumin;
	private String phone;
	private String hire_date;
	
	@Builder
	public Employees(int id, String name, String jumin, String phone, String hire_date) {
		super();
		this.id = id;
		this.name = name;
		this.jumin = jumin;
		this.phone = phone;
		this.hire_date = hire_date;
	}
}

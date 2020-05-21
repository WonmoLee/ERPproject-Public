package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Products {
	private int id;
	private int category_id;
	private String name;
	private int price;
	private String ingredient;
	private String quantity;
}
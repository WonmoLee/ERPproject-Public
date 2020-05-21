package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductStocks {
	
	private int id;
	private String name;
	private int input_stock;
	private int output_stock;
	private int current_stock;
	
}

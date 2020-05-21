package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItems {
	private int orders_id;
	private int item_id;
	private	int products_id;
	private	int quantity;
	private int unit_price;
}

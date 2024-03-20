package pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Cart {
    private int product_id;
    private int quantity;

    public Cart(int quantity) {
        this.quantity = quantity;
    }
}

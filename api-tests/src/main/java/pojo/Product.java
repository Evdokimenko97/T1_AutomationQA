package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Product {
    private long id;
    private String name;
    private String category;
    private double price;
    private int discount;

    public Product() {
    }
}


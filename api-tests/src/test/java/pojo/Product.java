package pojo;

import lombok.Data;

@Data
public class Product {
    private long id;
    private String name;
    private String category;
    private double price;
    private int discount;
}

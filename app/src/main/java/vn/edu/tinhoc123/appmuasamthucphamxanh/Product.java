package vn.edu.tinhoc123.appmuasamthucphamxanh;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String category;
    private String price;
    private int imageRes;
    private int quantity;

    public Product(String name, String category, String price, int imageRes) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageRes = imageRes;
        this.quantity = 1;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getPrice() { return price; }
    public int getImageRes() { return imageRes; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setPrice(String price) { this.price = price; }
    public void setImageRes(int imageRes) { this.imageRes = imageRes; }
    private boolean isSelected = false;

    public boolean isSelected() { return isSelected; }
    public void setSelected(boolean selected) { isSelected = selected; }
}
package vn.edu.tinhoc123.appmuasamthucphamxanh;

import com.google.firebase.database.PropertyName;
import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String category;
    private String price;
    private int imageRes;
    private int quantity;
    private boolean isSelected = false;

    public Product() {}

    public Product(String name, String category, String price, int imageRes) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageRes = imageRes;
        this.quantity = 1;
    }

    @PropertyName("name")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @PropertyName("category")
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    @PropertyName("price")
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    @PropertyName("imageRes")
    public int getImageRes() { return imageRes; }
    public void setImageRes(int imageRes) { this.imageRes = imageRes; }

    @PropertyName("quantity")
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @com.google.firebase.database.Exclude
    public boolean isSelected() { return isSelected; }
    public void setSelected(boolean selected) { this.isSelected = selected; }
}
package vn.edu.tinhoc123.appmuasamthucphamxanh;

public class ProductModel {
    private String name, category, price;

    public ProductModel() {}

    public ProductModel(String name, String category, String price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getPrice() { return price; }
}
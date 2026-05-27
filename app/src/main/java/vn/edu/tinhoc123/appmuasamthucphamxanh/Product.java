package vn.edu.tinhoc123.appmuasamthucphamxanh;

public class Product {
    private String name;
    private String price;
    private int imageRes;

    public Product(String name, String price, int imageRes) {
        this.name = name;
        this.price = price;
        this.imageRes = imageRes;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public int getImageRes() { return imageRes; }
}

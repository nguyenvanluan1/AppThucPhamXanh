package vn.edu.tinhoc123.appmuasamthucphamxanh;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<Product> cartItems = new ArrayList<>();

    private CartManager() {}
    public static CartManager getInstance() {
        if (instance == null) instance = new CartManager();
        return instance;
    }
    public void addProduct(Product p) { cartItems.add(p); }
    public List<Product> getCartItems() { return cartItems; }
}

package vn.edu.tinhoc123.appmuasamthucphamxanh;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<Product> cartItems = new ArrayList<>();

    public interface CartUpdateListener {
        void onUpdate(int count);
    }
    private CartUpdateListener listener;

    private CartManager() {}

    public static CartManager getInstance() {
        if (instance == null) instance = new CartManager();
        return instance;
    }

    public void setListener(CartUpdateListener listener) {
        this.listener = listener;
    }

    public void addProduct(Product p) {
        boolean found = false;
        for (Product item : cartItems) {
            if (item.getName().equals(p.getName())) {
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }

        if (!found) {
            p.setQuantity(1);
            cartItems.add(p);
        }

        if (listener != null) listener.onUpdate(cartItems.size());
    }

    public void removeProduct(Product p) {
        cartItems.remove(p);
        if (listener != null) listener.onUpdate(cartItems.size());
    }

    public List<Product> getCartItems() { return cartItems; }
}
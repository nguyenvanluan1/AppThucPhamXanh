package vn.edu.tinhoc123.appmuasamthucphamxanh;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<Product> cartItems = new ArrayList<>();
    private String currentUserId = "GUEST";
    private double discount = 0;

    public interface CartUpdateListener {
        void onUpdate(int count);
    }
    private CartUpdateListener listener;

    private CartManager() {}

    public static CartManager getInstance() {
        if (instance == null) instance = new CartManager();
        return instance;
    }

    public void setUserSession(String userId) {
        if (!this.currentUserId.equals(userId)) {
            this.currentUserId = userId;
            this.cartItems.clear();
            this.discount = 0;
            if (listener != null) listener.onUpdate(0);
        }
    }

    public void clearSession() {
        this.currentUserId = "GUEST";
        clearCart();
    }

    public void setListener(CartUpdateListener listener) {
        this.listener = listener;
    }

    public void applyDiscount(double amount) {
        this.discount = amount;
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product p : cartItems) {
            String priceStr = p.getPrice().replaceAll("[^\\d]", "");
            double price = Double.parseDouble(priceStr);
            total += (price * p.getQuantity());
        }
        return total - discount;
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
            Product newP = new Product(p.getName(), p.getCategory(), p.getPrice(), p.getImageRes());
            newP.setQuantity(1);
            cartItems.add(newP);
        }

        if (listener != null) listener.onUpdate(cartItems.size());
    }

    public void removeProduct(Product p) {
        cartItems.remove(p);
        if (listener != null) listener.onUpdate(cartItems.size());
    }

    public List<Product> getCartItems() { return cartItems; }

    public double getDiscount() { return discount; }

    public boolean hasProductInCategory(String category) {
        for (Product p : cartItems) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
    }

    public double getSubtotal() {
        double total = 0;
        for (Product p : cartItems) {
            String priceClean = p.getPrice().replaceAll("[^0-9]", "");
            total += (Double.parseDouble(priceClean) * p.getQuantity());
        }
        return total;
    }

    public void clearCart() {
        cartItems.clear();
        applyDiscount(0);
        if (listener != null) listener.onUpdate(0);
    }
}
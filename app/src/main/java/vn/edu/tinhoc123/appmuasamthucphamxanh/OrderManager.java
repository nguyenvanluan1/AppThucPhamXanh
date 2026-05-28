package vn.edu.tinhoc123.appmuasamthucphamxanh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderManager {
    private static OrderManager instance;
    private List<Order> orderList = new ArrayList<>();
    private int orderCounter = 1;

    private OrderManager() {}

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public String generateOrderId() {
        return "DH" + String.format("%02d", orderCounter++);
    }

    public void addOrder(Order order) {
        orderList.add(order);
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public List<Product> getMostPurchasedProductsByCategory(String category) {
        Map<String, Integer> totalQuantityMap = new HashMap<>();
        Map<String, Product> productInfoMap = new HashMap<>();

        for (Order order : orderList) {
            for (Product p : order.getItems()) {
                String key = p.getName().trim().toLowerCase();

                int currentQty = p.getQuantity();
                int newTotal = totalQuantityMap.getOrDefault(key, 0) + currentQty;

                totalQuantityMap.put(key, newTotal);

                if (!productInfoMap.containsKey(key)) {
                    productInfoMap.put(key, p);
                }
            }
        }

        List<Product> result = new ArrayList<>();
        for (String key : totalQuantityMap.keySet()) {
            Product p = productInfoMap.get(key);

            if (totalQuantityMap.get(key) >= 3 && p.getCategory().trim().equalsIgnoreCase(category.trim())) {
                p.setQuantity(totalQuantityMap.get(key));
                result.add(p);
            }
        }
        return result;
    }
}
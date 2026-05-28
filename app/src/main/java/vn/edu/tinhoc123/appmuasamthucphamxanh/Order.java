package vn.edu.tinhoc123.appmuasamthucphamxanh;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    private String orderId;
    private String status;
    private double totalAmount;
    private List<Product> items;

    public Order(String orderId, String status, double totalAmount, List<Product> items) {
        this.orderId = orderId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.items = (items != null) ? items : new ArrayList<>();    }

    public String getOrderId() { return orderId; }
    public String getStatus() { return status; }
    public double getTotalAmount() { return totalAmount; }

    public List<Product> getItems() { return items; }
}
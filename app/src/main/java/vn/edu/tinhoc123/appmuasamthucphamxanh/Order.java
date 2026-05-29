    package vn.edu.tinhoc123.appmuasamthucphamxanh;

    import com.google.firebase.database.PropertyName;

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
            this.items = (items != null) ? items : new ArrayList<>();
        }
        public Order() {
        }

        @PropertyName("orderId") public String getOrderId() { return orderId; }
        @PropertyName("status")
        public String getStatus() { return status; }
        @PropertyName("totalAmount") public double getTotalAmount() { return totalAmount; }
        @PropertyName("items") public List<Product> getItems() { return items; }

        @PropertyName("orderId") public void setOrderId(String orderId) { this.orderId = orderId; }
        @PropertyName("status") public void setStatus(String status) { this.status = status; }
        @PropertyName("totalAmount") public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
        @PropertyName("items") public void setItems(List<Product> items) { this.items = items; }
    }
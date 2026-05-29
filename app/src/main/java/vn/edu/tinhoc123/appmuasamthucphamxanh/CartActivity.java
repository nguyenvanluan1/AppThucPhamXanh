package vn.edu.tinhoc123.appmuasamthucphamxanh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rcvCart;
    private TextView txtTotal, txtOffers;
    private Button btnCheckout;
    private ImageView btnBack;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        btnBack = findViewById(R.id.btnBack);
        rcvCart = findViewById(R.id.rcvCart);
        txtTotal = findViewById(R.id.txtTotal);
        txtOffers = findViewById(R.id.txtOffers);
        btnCheckout = findViewById(R.id.btnCheckout);

        btnBack.setOnClickListener(v -> finish());
        txtOffers.setOnClickListener(v -> showVoucherDialog());

        btnCheckout.setOnClickListener(v -> {
            if (com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser() == null) {
                Toast.makeText(this, "Vui lòng đăng nhập để đặt hàng!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                handleCheckout();
            }
        });
        String voucherName = getIntent().getStringExtra("VOUCHER_NAME");
        if (voucherName != null) {
            checkAndApplyVoucher(voucherName);
        } else {
            txtOffers.setText("Chọn ưu đãi tại đây");
            txtOffers.setTextColor(android.graphics.Color.RED);
            txtOffers.setVisibility(View.VISIBLE);
            loadCartData();
        }
    }

    private void showVoucherDialog() {
        final String[] vouchers = {
                "Giảm 10.000đ cho đơn hàng rau củ",
                "Giảm 20.000đ cho đơn hàng thịt cá",
                "Giảm 10% cho đơn hàng từ 500k"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.select_dialog_item, vouchers) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                android.widget.TextView textView = (android.widget.TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(android.graphics.Color.RED);
                return view;
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("Chọn ưu đãi(Click vào ưu đãi)")
                .setAdapter(adapter, (dialog, which) -> checkAndApplyVoucher(vouchers[which]))
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void checkAndApplyVoucher(String voucherName) {
        List<Product> selectedItems = new ArrayList<>();
        for (Product p : CartManager.getInstance().getCartItems()) {
            if (p.isSelected()) selectedItems.add(p);
        }

        if (selectedItems.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Vui lòng chọn sản phẩm để áp dụng ưu đãi!")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        double subtotal = 0;
        for (Product p : selectedItems) {
            String priceClean = p.getPrice().replaceAll("[^0-9]", "");
            subtotal += (Double.parseDouble(priceClean) * p.getQuantity());
        }

        boolean isValid = false;

        if (voucherName.contains("500k")) {
            isValid = (subtotal >= 500000);
        } else if (voucherName.contains("rau củ")) {
            isValid = true;
            for (Product p : selectedItems) {
                if (!p.getCategory().equalsIgnoreCase("Rau, củ")) {
                    isValid = false;
                    break;
                }
            }
        } else if (voucherName.contains("thịt")) {
            isValid = true;
            for (Product p : selectedItems) {
                if (!p.getCategory().equalsIgnoreCase("Thịt, Cá")) {
                    isValid = false;
                    break;
                }
            }
        }

        if (isValid) {
            double discount = 0;
            if (voucherName.contains("10.000đ")) discount = 10000;
            else if (voucherName.contains("20.000đ")) discount = 20000;
            else if (voucherName.contains("10%")) discount = subtotal * 0.1;

            CartManager.getInstance().applyDiscount(discount);
            txtOffers.setText("Ưu đãi: " + voucherName + " (Đã áp dụng)");
            txtOffers.setTextColor(android.graphics.Color.BLUE);
        } else {
            CartManager.getInstance().applyDiscount(0);
            txtOffers.setText("Ưu đãi: " + voucherName + " (Không đủ điều kiện)");
            txtOffers.setTextColor(android.graphics.Color.RED);
            new AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Đơn hàng chưa đủ điều kiện cho voucher này (Voucher áp dụng cho danh mục riêng hoặc không đủ giá trị)!")
                    .setPositiveButton("OK", null)
                    .show();
        }

        calculateTotal();
    }

    private void handleCheckout() {
        try {
            android.util.Log.d("DEBUG_CHECKOUT", "Bắt đầu handleCheckout");
            List<Product> cartItems = CartManager.getInstance().getCartItems();
            if (cartItems == null || cartItems.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
                return;
            }
            List<Product> itemsToBuy = new ArrayList<>();
            List<Product> remainingItems = new ArrayList<>();

            for (Product p : cartItems) {
                if (p.isSelected()) itemsToBuy.add(p);
                else remainingItems.add(p);
            }

            if (itemsToBuy.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất một sản phẩm!", Toast.LENGTH_SHORT).show();
                return;
            }

            double subtotal = 0;
            for (Product p : itemsToBuy) {
                String priceStr = p.getPrice();
                if (priceStr != null) {
                    String priceClean = priceStr.replaceAll("[^0-9]", "");
                    if (!priceClean.isEmpty()) {
                        subtotal += Double.parseDouble(priceClean) * p.getQuantity();
                    }
                }
            }
            double finalTotal = Math.max(0, subtotal - CartManager.getInstance().getDiscount());

            java.util.Map<String, Object> orderMap = new java.util.HashMap<>();
            orderMap.put("orderId", OrderManager.getInstance().generateOrderId());
            orderMap.put("status", "Đang xử lý");
            orderMap.put("totalAmount", finalTotal);

            java.util.Map<String, Product> itemsMap = new java.util.HashMap<>();
            for (int i = 0; i < itemsToBuy.size(); i++) {
                itemsMap.put("item_" + i, itemsToBuy.get(i));
            }
            orderMap.put("items", itemsMap);

            String userId = FirebaseAuth.getInstance().getCurrentUser() != null
                    ? FirebaseAuth.getInstance().getCurrentUser().getUid() : null;

            if (userId == null) {
                Toast.makeText(this, "Lỗi xác thực người dùng!", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseDatabase.getInstance("https://appmuasamthucphamxanh-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .getReference("Orders")
                    .child(userId)
                    .push()
                    .setValue(orderMap)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            CartManager.getInstance().setCartItems(remainingItems);
                            CartManager.getInstance().applyDiscount(0);
                            Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, OrderHistoryActivity.class));
                            finish();
                        } else {
                            String error = task.getException() != null ? task.getException().getMessage() : "Lỗi không xác định";
                            Toast.makeText(this, "Lỗi Firebase: " + error, Toast.LENGTH_LONG).show();
                        }
                    });

        } catch (Exception e) {
            Toast.makeText(this, "Lỗi hệ thống: " + e.getMessage(), Toast.LENGTH_LONG).show();
            android.util.Log.e("DEBUG_CHECKOUT", "Lỗi crash: ", e);
        }
    }

    private double getSubtotal() {
        double total = 0;
        for (Product p : CartManager.getInstance().getCartItems()) {
            if (p.isSelected()) {
                String priceClean = p.getPrice().replaceAll("[^0-9]", "");
                if (!priceClean.isEmpty()) {
                    total += (Double.parseDouble(priceClean) * p.getQuantity());
                }
            }
        }
        return total;
    }

    public void loadCartData() {
        adapter = new ProductAdapter(
                CartManager.getInstance().getCartItems(),
                null,
                true,
                this::calculateTotal
        );
        rcvCart.setLayoutManager(new LinearLayoutManager(this));
        rcvCart.setAdapter(adapter);
        calculateTotal();
    }

    public void calculateTotal() {
        double subtotal = 0;
        List<Product> selectedItems = new ArrayList<>();

        for (Product p : CartManager.getInstance().getCartItems()) {
            if (p.isSelected()) {
                selectedItems.add(p);
                String priceClean = p.getPrice().replaceAll("[^0-9]", "");
                if (!priceClean.isEmpty()) {
                    subtotal += (Double.parseDouble(priceClean) * p.getQuantity());
                }
            }
        }
        double discount = CartManager.getInstance().getDiscount();
        if (selectedItems.isEmpty() || discount == 0) {
            CartManager.getInstance().applyDiscount(0);
            txtOffers.setText("Chọn ưu đãi tại đây");
            txtOffers.setTextColor(android.graphics.Color.RED);
            discount = 0;
        }
        int finalTotal = (int) Math.max(0, subtotal - discount);
        txtTotal.setText("Tổng tiền: " + String.format("%,d", finalTotal) + "đ");
    }

    private boolean isVoucherStillValid(List<Product> selectedItems) {
        String voucherText = txtOffers.getText().toString();
        if (!voucherText.contains("Ưu đãi:")) return true;
        if (voucherText.contains("rau củ")) {
            for (Product p : selectedItems) {
                if (!p.getCategory().equalsIgnoreCase("Rau, củ")) return false;
            }
        } else if (voucherText.contains("thịt")) {
            for (Product p : selectedItems) {
                if (!p.getCategory().equalsIgnoreCase("Thịt, Cá")) return false;
            }
        } else if (voucherText.contains("500k")) {
            double subtotal = 0;
            for (Product p : selectedItems) {
                String priceClean = p.getPrice().replaceAll("[^0-9]", "");
                if (!priceClean.isEmpty()) {
                    subtotal += (Double.parseDouble(priceClean) * p.getQuantity());
                }
            }
            return subtotal >= 500000;
        }
        return true;
    }
}
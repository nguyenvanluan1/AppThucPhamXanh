package vn.edu.tinhoc123.appmuasamthucphamxanh;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<String>(
                this,
                android.R.layout.select_dialog_item,
                vouchers
        ) {
            @Override
            public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
                android.view.View view = super.getView(position, convertView, parent);
                android.widget.TextView textView = (android.widget.TextView) view.findViewById(android.R.id.text1);

                textView.setTextColor(android.graphics.Color.RED);

                return view;
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("Chọn ưu đãi(Click vào ưu đãi)")
                .setAdapter(adapter, (dialog, which) -> {
                    checkAndApplyVoucher(vouchers[which]);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void checkAndApplyVoucher(String voucherName) {
        double subtotal = getSubtotal();
        boolean isValid = false;

        if (voucherName.contains("500k")) {
            isValid = (subtotal >= 500000);
        } else if (voucherName.contains("rau củ")) {
            isValid = CartManager.getInstance().hasProductInCategory("Rau, củ");
        } else if (voucherName.contains("thịt")) {
            isValid = CartManager.getInstance().hasProductInCategory("Thịt, Cá");
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
                    .setMessage("Đơn hàng của bạn chưa đủ điều kiện cho voucher này!")
                    .setPositiveButton("OK", null)
                    .show();
        }
        loadCartData();
    }

    private double getSubtotal() {
        double total = 0;
        for (Product p : CartManager.getInstance().getCartItems()) {
            String priceClean = p.getPrice().replaceAll("[^0-9]", "");
            if (!priceClean.isEmpty()) {
                total += (Double.parseDouble(priceClean) * p.getQuantity());
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
        double subtotal = getSubtotal();
        double discount = CartManager.getInstance().getDiscount();
        int finalTotal = (int) Math.max(0, subtotal - discount);
        txtTotal.setText("Tổng tiền: " + String.format("%,d", finalTotal) + "đ");
    }
}
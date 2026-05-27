package vn.edu.tinhoc123.appmuasamthucphamxanh;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

        loadCartData();
    }

    public void loadCartData() {
        List<Product> cartList = CartManager.getInstance().getCartItems();

        adapter = new ProductAdapter(
                cartList,
                null,
                true, // isCartScreen = true
                new ProductAdapter.OnCartChangedListener() {
                    @Override
                    public void onCartChanged() {
                        calculateTotal(CartManager.getInstance().getCartItems());
                    }
                }
        );

        rcvCart.setLayoutManager(new LinearLayoutManager(this));
        rcvCart.setAdapter(adapter);

        calculateTotal(cartList);
    }

    public void calculateTotal(List<Product> list) {
        int subtotal = 0;
        int discount = 10000;

        for (Product p : list) {
            String priceClean = p.getPrice().replaceAll("[^0-9]", "");
            if (!priceClean.isEmpty()) {
                int price = Integer.parseInt(priceClean);

                subtotal += (price * p.getQuantity());
            }
        }

        int finalTotal = Math.max(0, subtotal - discount);

        txtOffers.setText("Ưu đãi: Giảm 10.000đ");
        txtTotal.setText("Tổng tiền: " + finalTotal + "đ");
    }
}
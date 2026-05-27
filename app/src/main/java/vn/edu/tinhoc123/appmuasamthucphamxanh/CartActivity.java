package vn.edu.tinhoc123.appmuasamthucphamxanh;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rcvCart;
    private TextView txtTotal, txtOffers;
    private Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rcvCart = findViewById(R.id.rcvCart);
        txtTotal = findViewById(R.id.txtTotal);
        txtOffers = findViewById(R.id.txtOffers);
        btnCheckout = findViewById(R.id.btnCheckout);

        List<Product> cartList = CartManager.getInstance().getCartItems();

        rcvCart.setLayoutManager(new LinearLayoutManager(this));
        rcvCart.setAdapter(new ProductAdapter(cartList, null));

        calculateTotal(cartList);
    }

    private void calculateTotal(List<Product> list) {
    }
}
package vn.edu.tinhoc123.appmuasamthucphamxanh;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderHistoryActivity extends AppCompatActivity {
    private RecyclerView rcvOrders;
    private OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        rcvOrders = findViewById(R.id.rcvOrders);
        rcvOrders.setLayoutManager(new LinearLayoutManager(this));

        adapter = new OrderAdapter(OrderManager.getInstance().getOrderList());
        rcvOrders.setAdapter(adapter);
    }
}
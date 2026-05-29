package vn.edu.tinhoc123.appmuasamthucphamxanh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {
    private RecyclerView rcvOrders;
    private OrderAdapter adapter;
    private List<Order> orderList;

    private static final String DATABASE_URL = "https://appmuasamthucphamxanh-default-rtdb.asia-southeast1.firebasedatabase.app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(this, "Bạn cần đăng nhập để xem trang này", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_order_history);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        rcvOrders = findViewById(R.id.rcvOrders);
        rcvOrders.setLayoutManager(new LinearLayoutManager(this));

        orderList = new ArrayList<>();
        adapter = new OrderAdapter(orderList);
        rcvOrders.setAdapter(adapter);

        loadOrdersFromFirebase();
    }

    private void loadOrdersFromFirebase() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance(DATABASE_URL)
                .getReference("Orders")
                .child(userId);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String orderId = data.child("orderId").getValue(String.class);
                    String status = data.child("status").getValue(String.class);
                    Double totalAmount = data.child("totalAmount").getValue(Double.class);

                    List<Product> products = new ArrayList<>();
                    DataSnapshot itemsSnapshot = data.child("items");

                    for (DataSnapshot itemSnapshot : itemsSnapshot.getChildren()) {
                        Product p = itemSnapshot.getValue(Product.class);
                        if (p != null) {
                            products.add(p);
                        }
                    }

                    Order order = new Order(orderId, status, (totalAmount != null ? totalAmount : 0), products);
                    orderList.add(order);
                }

                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }

                if (orderList.isEmpty()) {
                    Log.d("ORDER_HISTORY", "Không có đơn hàng nào cho user.");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("ORDER_HISTORY", "Lỗi tải: " + error.getMessage());
                Toast.makeText(OrderHistoryActivity.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}